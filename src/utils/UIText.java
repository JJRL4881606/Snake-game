package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;

public class UIText {

    public static void drawOutlinedText(
            Graphics g,
            String texto,
            int centerX,
            int centerY,
            Font font,
            Color fill,
            Color border,
            int strokeWidth
    ) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(font);

        GlyphVector gv = font.createGlyphVector(
                g2d.getFontRenderContext(),
                texto
        );

        int x = centerX - gv.getPixelBounds(null, 0, 0).width / 2;
        int y = centerY;

        Shape shape = gv.getOutline(x, y);

        // relleno
        g2d.setColor(fill);
        g2d.fill(shape);

        // borde
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.setColor(border);
        g2d.draw(shape);
    }
}