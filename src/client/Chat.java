package client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import util.ConfigFileReader;

/**
 * Chat class which handles requests
 * 
 * @author bettyrain
 */

public class Chat {
	static ConfigFileReader config = new ConfigFileReader();

	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	private Socket socket;

	public void init(String nick) {
		final int PORT = Integer.parseInt(config.getProperty("PORT"));

		try {
			if (bufferedWriter != null | bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					System.out.println("Buffer reader close exception: " + e);
				}
				bufferedReader = null;
				bufferedWriter = null;
			}
			socket = new Socket("localhost", PORT);
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

			if (join(nick)) {
				String help = "";
				String[] words = null;
				while ((help = bufferedReader.readLine()) != null) {
					words = help.split("\\s");
					switch (words[0]) {
					case "JOIN":
						Launcher.chatWindow.printJoin(help);
						break;
					case "MESSAGE":
						System.out.println("USER MESSAGE: " + help);
						Launcher.chatWindow.printMsg(help);
						break;
					case "LEAVE":
						Launcher.chatWindow.printLeave(help);
						break;
					case "ERROR":
						Launcher.chatWindow.printError(help);
						Launcher.chatWindow.setStart();
						break;
					case "PING":
						sendPong();
						break;
					default:
						System.out.println("UNKNOWN COMMAND" + help);
					}
				}
			}
		} catch (IOException e) {
			Launcher.chatWindow.printError("Connection Refused. SERVER DOWN.");
			Launcher.chatWindow.printDisconnect();
			Launcher.chatWindow.setStart();
		}
	}

	// JOIN request
	private boolean join(String nick) {
		try {
			bufferedWriter.write("JOIN " + nick);
			bufferedWriter.newLine();
			bufferedWriter.flush();

			String answer = bufferedReader.readLine();
			System.out.println(answer);
			if (answer.equals("OK")) {
				return true;
			} else if (answer.equals("LOGIN ALREADY IN USE")) {
				Launcher.chatWindow.printError("LOGIN ALREADY IN USE");
				Launcher.chatWindow.printDisconnect();
				Launcher.chatWindow.setStart();
			}

			return false;
		} catch (Exception e) {
			Launcher.chatWindow.printError("SERVER DOWN. ");
			Launcher.chatWindow.setStart();
			return false;
		}
	}

	// MESSAGE request
	public void sendMessage(String message) {
		try {
			bufferedWriter.write("MESSAGE " + message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (Exception e) {
			System.out.println("Error message sending: " + e);
//            e.printStackTrace();
		}
	}

	// LEAVE request
	public void disconnect() {
		try {
			bufferedWriter.write("LEAVE");
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (Exception e) {
			System.out.println("Error leave: " + e);
//            e.printStackTrace();
		}
	}

	// PONG request
	public void sendPong() {
		try {
			bufferedWriter.write("PONG");
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			System.out.println("Error pong: " + e);
//			e.printStackTrace();
		}
	}
}
