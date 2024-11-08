package com.fredericomf.cmdfipe.principal;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.fredericomf.cmdfipe.enums.MainMenuOptions;
import com.fredericomf.cmdfipe.enums.TerminalColors;
import com.fredericomf.cmdfipe.enums.TextAlign;
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

    public void exbirMenu() {
        MainMenuOptions option = showMainMenu();

        if (option == MainMenuOptions.SAIR) {
            confirmExit();
            return;
        }
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

    private void carModelMenu() {
        System.out.print("\033[32mDigite o código do modelo para consultar valores: \033[0m");
    }

    private void carMenu() {
        System.out.print("\033[32mDigite um trecho do nome do veículo para consulta: \033[0m");
    }

    private void brandMenu() {
        System.out.print("\033[32mInforme o código da marca para consulta: \033[0m");
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
