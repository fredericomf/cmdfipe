package com.fredericomf.cmdfipe.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.fredericomf.cmdfipe.enums.MainMenuOptions;
import com.fredericomf.cmdfipe.enums.TerminalColors;
import com.fredericomf.cmdfipe.enums.TextAlign;
import com.fredericomf.cmdfipe.models.Brand;
import com.fredericomf.cmdfipe.models.Vehicle;
import com.fredericomf.cmdfipe.models.VehicleDetail;
import com.fredericomf.cmdfipe.models.VehicleModels;
import com.fredericomf.cmdfipe.models.VehicleYear;
import com.fredericomf.cmdfipe.services.ConsumoApi;
import com.fredericomf.cmdfipe.services.ConverteDados;
import com.fredericomf.cmdfipe.utils.drawer.FrameDrawer;
import com.fredericomf.cmdfipe.utils.drawer.FrameDrawerConfigs;
import com.fredericomf.cmdfipe.utils.drawer.WindowDrawer;
import com.fredericomf.cmdfipe.utils.drawer.WindowItem;

public class Principal {

    FrameDrawerConfigs frameDrawerConfigs = new FrameDrawerConfigs(100,
            TerminalColors.BLUE,
            TerminalColors.WHITE,
            TerminalColors.RED,
            TerminalColors.GREEN,
            TerminalColors.YELLOW);

    FrameDrawer frameDrawer = new FrameDrawer(frameDrawerConfigs);

    final int insideFrameWidth = 80;

    Scanner scanner = new Scanner(System.in);

    ConsumoApi api = new ConsumoApi();
    ConverteDados converter = new ConverteDados();

    MainMenuOptions selectedOption;
    Integer selectedBrand;
    Integer selectedVehicle;

    public void exbirMenu() {
        selectedOption = showMainMenu();

        if (selectedOption == MainMenuOptions.SAIR) {
            confirmExit();
            return;
        }

        brandMenu(null);
    }

    private void confirmExit() {

        WindowDrawer window = new WindowDrawer(frameDrawer);

        window.draw(
                new WindowItem[] {
                        new WindowItem("TEM CERTEZA QUE DESEJA SAIR?")
                },
                Arrays.asList(
                        new WindowItem[] {
                                new WindowItem("1 - Sim", frameDrawerConfigs.successColor()),
                                new WindowItem("2 - Não", frameDrawerConfigs.errorColor())
                        }));

        System.out.print("\033[" + frameDrawerConfigs.commonTextColor() + "mDigite uma das opções: \033[0m");

        int selectedOption = scanner.nextInt();

        if (selectedOption == 1) {
            System.exit(0);
        }

        exbirMenu();
    }

    private void showSelectedModelDetails() {
        System.out.println();
        System.out.println();
        System.out.println();

        String json = api
                .obterDados("https://parallelum.com.br/fipe/api/v1/" + selectedOption.getApiCategory() + "/marcas/"
                        + selectedBrand + "/modelos/" + selectedVehicle + "/anos");

        List<VehicleYear> years = converter.obterListaDados(json, VehicleYear.class);

        List<VehicleDetail> vehicles = new ArrayList<>();

        for (VehicleYear year : years) {
            json = api
                    .obterDados("https://parallelum.com.br/fipe/api/v1/" + selectedOption.getApiCategory() + "/marcas/"
                            + selectedBrand + "/modelos/" + selectedVehicle + "/anos/" + year.id());

            vehicles.add(converter.obterDados(json, VehicleDetail.class));
        }

        WindowDrawer window = new WindowDrawer(frameDrawer);
        window.draw(
                new WindowItem[] {
                        new WindowItem("TODOS OS VEÍCULOS COM OS VALORES POR ANO"),
                        new WindowItem("Marca: " + vehicles.getFirst().brand())
                },

                vehicles.stream()
                        .map(v -> new WindowItem(
                                v.modelYear() + ": " + v.model() + "(" + v.fuel() + ") - " + v.value()))
                        .collect(Collectors.toList())

        );
    }

    private void carModelMenu(List<Vehicle> models) {
        System.out.println();
        System.out.println();
        System.out.println();

        WindowDrawer window = new WindowDrawer(frameDrawer);
        window.draw(
                new WindowItem[] {
                        new WindowItem("MODELOS ENCONTRADOS")
                },
                models.stream()
                        .map(b -> new WindowItem(b.id() + " - " + b.name()))
                        .collect(Collectors.toList()));

        System.out.print("\033[32mDigite o código do modelo para consultar valores: \033[0m");

        String text = scanner.next();
        selectedVehicle = StringUtils.isNumeric(text) ? Integer.parseInt(text) : 999999999;

        if (!models.stream().anyMatch(b -> b.id().equals(selectedVehicle))) {
            System.out.println("\033[" + frameDrawerConfigs.errorColor()
                    + "mOpção inválida! Pressione qualquer tecla para continuar.\033[0m");
            scanner.nextLine();
            scanner.nextLine();
            carModelMenu(models);
        } else {
            showSelectedModelDetails();
        }

    }

    private void carMenu(List<Vehicle> models) {

        if (models == null || models.isEmpty()) {
            String json = api
                    .obterDados("https://parallelum.com.br/fipe/api/v1/" + selectedOption.getApiCategory() + "/marcas/"
                            + selectedBrand + "/modelos");

            VehicleModels vehicleModels = converter.obterDados(json, VehicleModels.class);
            models = vehicleModels.models();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        WindowDrawer window = new WindowDrawer(frameDrawer);
        window.draw(
                new WindowItem[] {
                        new WindowItem("VEÍCULOS PARA CONSULTA")
                },
                models.stream()
                        .map(b -> new WindowItem(b.id() + " - " + b.name()))
                        .collect(Collectors.toList()));

        System.out.print("\033[32mDigite um trecho do nome do veículo ou o código para consulta: \033[0m");

        String modelName = scanner.next();

        String errorMessage = "";

        boolean inputedVehicleCode = StringUtils.isNumeric(modelName);

        List<Vehicle> filteredModels = models.stream()
                .filter(m -> (inputedVehicleCode && m.id().equals(Integer.parseInt(modelName)))
                        || m.name().toLowerCase().contains(modelName.toLowerCase()))
                .collect(Collectors.toList());

        if (modelName.isEmpty()) {
            errorMessage = "Nome inválido! Pressione qualquer tecla para continuar.";
        } else if (filteredModels.isEmpty()) {
            errorMessage = "Veículo não encontrado! Pressione qualquer tecla para continuar.";
        }

        if (StringUtils.isNotBlank(errorMessage)) {
            System.out.println("\033[" + frameDrawerConfigs.errorColor()
                    + "m" + errorMessage + "\033[0m");
            scanner.nextLine();
            carMenu(models);
        } else if (inputedVehicleCode) {
            selectedVehicle = Integer.parseInt(modelName);
            showSelectedModelDetails();
        } else {
            carModelMenu(filteredModels);
        }

    }

    private void brandMenu(List<Brand> brands) {

        if (brands == null || brands.isEmpty()) {
            String json = api
                    .obterDados("https://parallelum.com.br/fipe/api/v1/" + selectedOption.getApiCategory() + "/marcas");

            brands = converter.obterListaDados(json, Brand.class);
        }

        System.out.println();
        System.out.println();
        System.out.println();

        WindowDrawer window = new WindowDrawer(frameDrawer);
        window.draw(
                new WindowItem[] {
                        new WindowItem("MARCAS PARA CONSULTA")
                },
                brands.stream()
                        .map(b -> new WindowItem(StringUtils.leftPad(b.id(), 3, "0") + " - " + b.name()))
                        .collect(Collectors.toList()));

        System.out.print("\033[32mInforme o código da marca para consulta: \033[0m");

        selectedBrand = scanner.nextInt();

        if (!brands.stream().anyMatch(b -> b.id().equals(selectedBrand.toString()))) {
            System.out.println("\033[" + frameDrawerConfigs.errorColor()
                    + "mOpção inválida! Pressione qualquer tecla para continuar.\033[0m");
            scanner.nextLine();
            scanner.nextLine();
            brandMenu(brands);
        } else {
            carMenu(null);
        }
    }

    private MainMenuOptions showMainMenu() {

        WindowDrawer window = new WindowDrawer(frameDrawer);

        window.draw(
                new WindowItem[] {
                        new WindowItem("CONSULTA FIPE - TERMINAL", TerminalColors.MAGENTA),
                        new WindowItem("OPÇÕES", TextAlign.LEFT)
                },
                Arrays.asList(MainMenuOptions.values()).stream()
                        .map(o -> new WindowItem(o.getId() + " - " + o.getDescription(), o.getColor()))

                        .collect(Collectors.toList()));

        System.out.print("\033[" + frameDrawerConfigs.commonTextColor()
                + "mDigite uma das opções para consultar valores: \033[0m");

        int selectedOption = scanner.nextInt();

        if ((selectedOption >= 1 && selectedOption <= 3) || selectedOption == 9) {
            return MainMenuOptions.getById(selectedOption);
        }

        System.out.println("\033[31mOpção invalida!\033[0m");
        return showMainMenu();
    }

}
