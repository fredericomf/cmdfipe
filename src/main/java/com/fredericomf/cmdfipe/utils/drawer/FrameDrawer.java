package com.fredericomf.cmdfipe.utils.drawer;

import com.fredericomf.cmdfipe.enums.TerminalColors;
import com.fredericomf.cmdfipe.enums.TextAlign;

/// Author: Frederico Mottinha de Figueiredo
/// 
/// O objetivo dessa classe é facilitar o desenho de quadros e textos no terminal.
public class FrameDrawer {

    final public FrameDrawerConfigs configs;

    public FrameDrawer(FrameDrawerConfigs configs) {
        this.configs = configs;
    }

    public void drawTopBorder() {
        System.out.println("\033[" + configs.frameColor() + "m╔" + "═".repeat(configs.insideFrameWidth()) + "╗\033[0m");
    }

    public void drawMediumLine() {
        System.out.println("\033[" + configs.frameColor() + "m╠" + "═".repeat(configs.insideFrameWidth()) + "╣\033[0m");
    }

    public void drawBottomBorder() {
        System.out.println("\033[" + configs.frameColor() + "m╚" + "═".repeat(configs.insideFrameWidth()) + "╝\033[0m");
    }

    public void drawText(String text) {
        drawText(text, TextAlign.LEFT, configs.commonTextColor());
    }

    public void drawText(String text, TextAlign textAlign) {
        drawText(text, textAlign, configs.commonTextColor());
    }

    public void drawText(String text, TextAlign textAlign, TerminalColors textColor) {

        if (textColor == null) {
            throw new IllegalArgumentException("textColor cannot be null");
        }

        System.out
                .println("\033[" + configs.frameColor() + "m║\033[" + textColor + "m"
                        + getAlignedText(text, textAlign) + "\033["
                        + configs.frameColor() + "m║\033[0m");
    }

    private String getAlignedText(String text, TextAlign textAlign) {

        switch (textAlign) {
            case CENTER:
                int sidesWidth = (configs.insideFrameWidth() - text.length()) / 2;
                int newTotalWidth = (sidesWidth * 2) + text.length();

                int rightSideWidth;

                if (newTotalWidth < configs.insideFrameWidth()) {
                    rightSideWidth = configs.insideFrameWidth() - newTotalWidth + sidesWidth;
                } else if (newTotalWidth > configs.insideFrameWidth()) {
                    rightSideWidth = newTotalWidth - configs.insideFrameWidth() - sidesWidth;
                } else {
                    rightSideWidth = sidesWidth;
                }

                return " ".repeat(sidesWidth) + text + " ".repeat(rightSideWidth);
            case LEFT:
                return text + " ".repeat(configs.insideFrameWidth() - text.length());
            case RIGHT:
                return " ".repeat(configs.insideFrameWidth() - text.length()) + text;
            default:
                throw new IllegalArgumentException("textAlign cannot be null");
        }
    }

}
