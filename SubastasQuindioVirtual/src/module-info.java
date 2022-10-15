module SubastasQuindioVirtual{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
	requires java.logging;
	requires javafx.base;

	opens co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers to javafx.fxml;
	opens co.edu.uniquindio.programacion.subastasQuindioVirtual.application to javafx.graphics, javafx.fxml;
	exports co.edu.uniquindio.programacion.subastasQuindioVirtual.application;
}