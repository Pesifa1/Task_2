package cs.vsu.ru.putilin_m_d.task_2_8;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class HelloController {
    @FXML
    private Button button;

    @FXML
    private Canvas canvas;

    @FXML
    protected void drawButton() {

        Drawer drawer = new Drawer(canvas, 80 , Color.YELLOW, Color.VIOLET);
        drawer.fillCircle();
    }

}