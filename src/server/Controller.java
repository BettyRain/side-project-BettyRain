package server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.LocalTime;

/**
 * Controller class which adds a message into textzone in ServerWindow
 * 
 * @author bettyrain
 */

public class Controller {
	@FXML
	public TextArea textZone;

	public void add(String message) {
		LocalTime localTime = LocalTime.now();
		textZone.appendText(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond() + " " + " "
				+ message + "\n");
	}
}
