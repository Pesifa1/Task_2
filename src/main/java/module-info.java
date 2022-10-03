module cs.vsu.ru.putilin_m_d.task_1_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens cs.vsu.ru.putilin_m_d.task_1_1 to javafx.fxml;
    exports cs.vsu.ru.putilin_m_d.task_1_1;
}