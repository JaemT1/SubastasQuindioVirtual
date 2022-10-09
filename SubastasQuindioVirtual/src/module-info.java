module SubastasQuindioVirtual{
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens co.edu.uniquindio.programacion.subastasQuindioVirtual.controllers to javafx.fxml;
	opens co.edu.uniquindio.programacion.subastasQuindioVirtual.application to javafx.graphics, javafx.fxml;
	exports co.edu.uniquindio.programacion.subastasQuindioVirtual.application;
}