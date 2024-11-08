package com.fredericomf.cmdfipe.utils.drawer;

import com.fredericomf.cmdfipe.enums.TerminalColors;

public record FrameDrawerConfigs(int insideFrameWidth, TerminalColors frameColor, TerminalColors commonTextColor,
                TerminalColors errorColor,
                TerminalColors successColor, TerminalColors optionItemsColor) {

}
