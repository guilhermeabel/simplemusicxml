module simple.musicxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires transitive java.desktop;
    requires java.logging;
    requires transitive javafx.graphics;
    requires javafx.graphicsEmpty;

    opens simple.musicxml to javafx.fxml;
    exports simple.musicxml;
}
