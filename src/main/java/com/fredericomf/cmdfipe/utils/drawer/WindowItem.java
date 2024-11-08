package com.fredericomf.cmdfipe.utils.drawer;

import com.fredericomf.cmdfipe.enums.TerminalColors;
import com.fredericomf.cmdfipe.enums.TextAlign;

public class WindowItem {
    private String text;
    private TextAlign textAlign;
    private TerminalColors color;

    public WindowItem(String text) {
        this.text = text;
    }

    public WindowItem(String text, TextAlign textAlign) {
        this.text = text;
        this.textAlign = textAlign;
    }

    public WindowItem(String text, TerminalColors color) {
        this.text = text;
        this.color = color;
    }

    public WindowItem(String text, TextAlign textAlign, TerminalColors color) {
        this.text = text;
        this.textAlign = textAlign;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextAlign getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(TextAlign textAlign) {
        this.textAlign = textAlign;
    }

    public TerminalColors getColor() {
        return color;
    }

    public void setColor(TerminalColors color) {
        this.color = color;
    }
}
