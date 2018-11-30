package client;

/**
 * Launcher class which creates a new Chat and ChatWindow for every new user
 * 
 * @author bettyrain
 */

public class Launcher {
	static ChatWindow chatWindow;
	static Chat chat;

	public static void main(String[] args) {
		chatWindow = new ChatWindow();
		chat = new Chat();
	}

}
