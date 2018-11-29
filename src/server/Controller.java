package server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.time.LocalTime;

public class Controller {
	@FXML
	public TextArea textZone;

//    @FXML
//    private Button button;

	public void add(String message) {
		System.out.println("add");
		LocalTime localTime = LocalTime.now();
		textZone.appendText(localTime.getHour() + ":" + localTime.getMinute() + ":" + localTime.getSecond() + " " + " "
				+ message + "\n");
	}
}
