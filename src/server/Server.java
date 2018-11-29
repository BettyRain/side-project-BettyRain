package server;

import javafx.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		init();
	}

	private static void init() {
		Thread t = new Thread(() -> {
			ServerWindow.main(null);
		});
		t.start();
		System.out.println("System running...");
		try (ServerSocket ss = new ServerSocket(7070)) {
			while (true) {
				Socket socket = ss.accept();
				System.out.println("NEW CLIENT");
				ServerMessagesHandler serverMessagesHandler = new ServerMessagesHandler(socket);
				serverMessagesHandler.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
