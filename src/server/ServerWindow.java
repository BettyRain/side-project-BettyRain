package server;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class ServerWindow extends Application {
	public static Controller serverGui;

//  public static ServerGui getInstanse() {
//      if (serverGui == null) {
//          serverGui = new ServerGui();
//      }
//      return serverGui;
//  }

//  public ServerGui() {
//      System.out.println("test");
//  }

	// public ServerGui() {
//      serverGui = this;
//  }

	@Override
	public void start(Stage primaryStage) {
//      serverGui = this;
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

//  public void test2() {
//      System.out.println("test" + textZone);
//      add("tfasdf asdf sadf asd f");
//  }

	public static void main(String[] args) {
		launch();
	}
}
