package jfx.fractal.explorer.actions;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitAction implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Platform.exit();
	}

}
