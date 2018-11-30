package server;

import util.ConfigFileReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server class Creates a server window in thread Uses serverMessagesHandler to
 * add a new chat user
 * 
 * @author bettyrain
 */

public class Server {

	static ConfigFileReader config = new ConfigFileReader();

	public static void main(String[] args) {
		init();
	}

	private static void init() {
		final int PORT = Integer.parseInt(config.getProperty("PORT"));

		Thread t = new Thread(() -> {
			ServerWindow.main(null);
		});
		t.start();
		System.out.println("System is running...");
		try (ServerSocket ss = new ServerSocket(PORT)) {
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
