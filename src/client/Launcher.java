package client;

public class Launcher {
    static ChatWindow chatWindow;
    static Chat chat;
    public static void main(String[] args) {
    	chatWindow  = new ChatWindow();
        chat = new Chat();
//        chat.init();
    }

}
