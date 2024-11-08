package com.fredericomf.cmdfipe.utils.drawer;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import com.fredericomf.cmdfipe.enums.TextAlign;

/// Author: Frederico Mottinha de Figueiredo
/// 
/// O Objetivo dessa classe é facilitar o desenho de janelas no terminal. A ideia é que as janelas tenham uma largura pré-definida, mas que possam aumentar de acordo com o conteúdo da mesma.
/// 
/// Ainda está em fase de implementação e melhorias.
public class WindowDrawer {

    final FrameDrawer frameDrawer;

    public WindowDrawer(FrameDrawer frameDrawer) {
        this.frameDrawer = frameDrawer;
    }

    public void draw(WindowItem[] title, List<WindowItem> content) {
        draw(title, content, "No content.");
    }

    public void draw(WindowItem[] title, List<WindowItem> content, String noContentText) {

        FrameDrawer newFrameDrawer = frameDrawer;

        IntSummaryStatistics contentStatistics = content.stream()
                .map(c -> c.getText())
                .filter(v -> v.length() > frameDrawer.configs.insideFrameWidth())
                .collect(Collectors.summarizingInt(String::length));

        if (contentStatistics.getMax() > frameDrawer.configs.insideFrameWidth()) {
            newFrameDrawer = new FrameDrawer(new FrameDrawerConfigs(
                    contentStatistics.getMax(),
                    frameDrawer.configs.frameColor(),
                    frameDrawer.configs.commonTextColor(),
                    frameDrawer.configs.errorColor(),
                    frameDrawer.configs.successColor(),
                    frameDrawer.configs.optionItemsColor()));
        }

        newFrameDrawer.drawTopBorder();

        for (WindowItem t : title) {
            newFrameDrawer.drawText(t.getText(), t.getTextAlign() == null ? TextAlign.CENTER : t.getTextAlign(),
                    t.getColor() == null ? frameDrawer.configs.commonTextColor() : t.getColor());
            newFrameDrawer.drawMediumLine();
        }

        if (content.isEmpty()) {
            newFrameDrawer.drawText(noContentText, TextAlign.LEFT);
        } else {

            for (WindowItem c : content) {
                newFrameDrawer.drawText(c.getText(), c.getTextAlign() == null ? TextAlign.LEFT : c.getTextAlign(),
                        c.getColor() == null ? frameDrawer.configs.optionItemsColor() : c.getColor());
            }
        }

        newFrameDrawer.drawBottomBorder();
    }
}
