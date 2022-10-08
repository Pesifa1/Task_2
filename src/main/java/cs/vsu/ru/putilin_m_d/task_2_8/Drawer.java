package cs.vsu.ru.putilin_m_d.task_2_8;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class Drawer {

    // На данном этапе мне не нравится реализация полей в классе
    // По моему мнению глупо передавать все входные данные в Drawer
    // Я бы оставил коорды цента и canvas, остальное перенёс бы в аргументы методов

    private Canvas canvas;
    private int r;
    private Color C1, C2;

    private final int x0;
    private final int y0;

    public Drawer(Canvas canvas, int r, Color C1, Color C2) {
        this.canvas = canvas;
        this.r = r;
        this.C1 = C1;
        this.C2 = C2;
        this.x0 = (int) canvas.getWidth() / 2;
        this.y0 = (int) canvas.getHeight() / 2;
    }

    // Отрисовка прямой с интерполированием
    private void drawLine(int x1, int y1, int x2, int y2) {
        int step;

        int dx = (x2 - x1), dy = (y2 - y1);

        step = Math.max(Math.abs(dx), Math.abs(dy));

        dx = dx / step; dy = dy / step;
        int x = x1, y = y1, i = 1;

        while (i <= step) {
            canvas.getGraphicsContext2D().getPixelWriter().setColor(x, y, getCirclePointColor(x, y));
            x = x + dx;
            y = y + dy;
            i = i + 1;
        }
    }

    // Midpoint circle algorithm - Part 1 - https://www.youtube.com/watch?v=QeOvZTYlmAI
    // Part 2 - https://www.youtube.com/watch?v=0L8HnkiVuOA
    // Site - https://www.gatevidyalay.com/mid-point-circle-drawing-algorithm/

    // Отлисовка контура круга алгоритмом Midpoint circle

    public void drawCircle() {
        int x = 0;
        int y = r;
        double p = 1 - r;

        while (x + x0 <= y + y0) {
            x = x + 1;
            if(p >= 0) {
                y = y - 1;
                p = p + 2 * x - 2 * y + 1;
            } else {
                p = p + 2 * x + 1;
            }
            canvas.getGraphicsContext2D().getPixelWriter().setColor(x + x0, y + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(y + x0, x + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(y + x0, -x + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(x + x0, -y + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(-x + x0, -y + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(-y + x0, -x + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(-y + x0, x + y0, Color.BLACK);
            canvas.getGraphicsContext2D().getPixelWriter().setColor(-x + x0, y + y0, Color.BLACK);
        }
    }

    // Метод, для заполнения круга - костяк метода, похож на предыдущий, но теперь мы проходим 4 октанта
    // заполняя линиями по горизонтали круг используя координаты октанта

    public void fillCircle() {
        int x0 = (int) canvas.getWidth() / 2, y0 = (int) canvas.getHeight() / 2;

        int x = 0;
        int y = r;
        double p = 1 - r;

        while (x + x0 <= y + y0) {
            x = x + 1;
            if(p >= 0) {
                y = y - 1;
                p = p + 2 * x - 2 * y + 1;
            } else {
                p = p + 2 * x + 1;
            }

            drawLine(-x + x0, -y + y0, x + x0, -y + y0);
            drawLine(-y + x0, -x + y0, y + x0, -x + y0);
            drawLine(-y + x0, x + y0, y + x0, x + y0);
            drawLine(-x + x0, y + y0, x + x0, y + y0);
        }
        drawLine(x0 + r, y0, x0 - r, y0);
        // Почему то при заполнении круга образуюся вертикальные(если заполнять по октантам) и горизонтальные пробелы
        // по центру. Для их заполнения отрисовываем линии по центру(в данном случае одну)
    }

    // Метод для интерполяции точки круга - ищем разницу цвета по rgb, составляем отношение расстояния от точки до
    // центра круга с радиусом круга, и используя его определяем на какой "стадии" находтся цвет в данной точке

    public Color getCirclePointColor(int x, int y) {
        double dR = C2.getRed() - C1.getRed();
        double dG = C2.getGreen() - C1.getGreen();
        double dB = C2.getBlue() - C1.getBlue();
        double increment = (Math.sqrt(Math.pow(x - x0, 2) + Math.pow(y - y0, 2))) / r;
        // Проблема: отношение при r = гипотенузе не будет точно равно нулю, а будет равно 1.000000004535498...,
        // что создает ошибку, т.к. значения r, g, или b должны находиться в промежутке 0.0 - 1.0

        increment = increment - increment % 0.01;
        // Для этого я округлил дробь до сотых, чтобы таких проблем не возникало

        return Color.color(C1.getRed() + dR * increment, C1.getGreen() + dG * increment, C1.getBlue() + dB * increment);
    }

}
