package com.fredericomf.cmdfipe.enums;

public enum MainMenuOptions {
    CARRO(1, "Carro", "carros", TerminalColors.GREEN), MOTO(2, "Moto", "motos", TerminalColors.GREEN),
    CAMINHAO(3, "Caminhão", "caminhoes", TerminalColors.GREEN), SAIR(9, "Sair", null, TerminalColors.RED);

    private final int id;
    private final String description;
    private final String apiCategory;
    private final TerminalColors color;

    MainMenuOptions(int value, String description, String apiCategory, TerminalColors color) {
        this.id = value;
        this.description = description;
        this.apiCategory = apiCategory;
        this.color = color;
    }

    public static MainMenuOptions getById(int id) {
        for (MainMenuOptions option : MainMenuOptions.values()) {
            if (option.id == id) {
                return option;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TerminalColors getColor() {
        return color;
    }

    public String getApiCategory() {
        return apiCategory;
    }
}
