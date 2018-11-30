package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Server window with log from chat
 * Window is made with using JavaFX UI
 * Shows information about "ping-pong" connection and users
 * 
 * @param Stage
 * @author bettyrain
 */

public class ServerWindow extends Application {
	public static Controller serverGui;

	@Override 
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/s.fxml"));
			Parent root = loader.load();
			serverGui = loader.getController();
			System.out.println("SERVEGUI" + serverGui);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("SERVER");
			primaryStage.show();
			primaryStage.setOnCloseRequest(event -> {
				System.exit(0);
			});

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch();
	}
}
