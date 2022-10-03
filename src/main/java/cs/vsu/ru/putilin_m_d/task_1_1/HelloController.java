package cs.vsu.ru.putilin_m_d.task_1_1;

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
        canvas.getGraphicsContext2D().setFill(Color.BLACK);
        canvas.getGraphicsContext2D().fillRect(3, 3, 40, 40);
        canvas.getGraphicsContext2D().fillOval(40,40, 40, 40);
    }

}