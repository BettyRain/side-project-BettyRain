package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerMessagesHandler extends Thread {
    static ConcurrentHashMap<String, ServerMessagesHandler> users = new ConcurrentHashMap<>();
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public ServerMessagesHandler(Socket socket) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * server:
     * <p>
     * out:
     * 1) JOIN nick //nick joined channel
     * 2) MESSAGE nick text // nick send "text"
     * 3) LEAVE nick // nick leave channel
     * 4) ERROR desc //
     * <p>
     * in:
     * 1) JOIN nick // nick trying connect to chat
     * 2) MESSAGE text // user send message
     * 3) LEAVE // user left channel
     * <p>
     * <p>
     * <p>
     * <p>
     * client:
     * out:
     * 1) JOIN mynick
     * 2) MESSAGE text
     * 3) LEAVE
     * <p>
     * in:
     * 1) JOIN nick
     * 2) MESSAGE nick text
     * 3) LEAVE nick
     * 4) ERROR desc
     */

    @Override
    public void run() {
        try {
            String input;
            while ((input = bufferedReader.readLine()) != null && !isInterrupted()) {
                System.out.println(input);
                if (input.startsWith("JOIN")) {
                    if (input.split(" ").length > 1 && !users.containsKey(input.split(" ")[1])) {
                        users.put(input.split(" ")[1], this);
                        userName = input.split(" ")[1];
                        sendOK();
                        sendUserJoined(userName);
                    } else {
                        sendError("LOGIN ALREADY IN USE OR INVALID");
                        disconnect(this);
                    }
                } else if (input.startsWith("MESSAGE")) {
                    if (input.split(" ").length > 1) {
                        sendMessageToAll(input.split(" ")[1]);
                    } else {
                        sendError("MESSAGE FORMAT ERROR");
                        disconnect(this);
                    }

                } else if (input.startsWith("LEAVE")) {
                    sendUserLeave(userName);
                    disconnect(this);
                } else {
                    sendError("UNKNOWN COMMAND");
                }
            }
        } catch (IOException e) {
            System.out.println("USER DISCONNECTED");
            disconnect(this);
        }
    }

    private void sendOK() {
        try {
            bufferedWriter.write("OK");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            removeUser(userName);
        }
    }

    private void sendMessageToAll(String message) {
        users.forEach((k, v) -> {
            try {
                v.bufferedWriter.write("MESSAGE " + userName + " " + message);
                v.bufferedWriter.newLine();
                v.bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println(110);
                disconnect(this);
                removeUser(v.userName);
            }
        });
    }

    private void sendUserJoined(String userName) {
        users.forEach((k, v) -> {
            try {
                v.bufferedWriter.write("JOIN " + userName);
                v.bufferedWriter.newLine();
                v.bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println(123);
                disconnect(v);
                removeUser(v.userName);
            }
        });
    }

    private void sendUserLeave(String userName) {
        users.remove(this.userName);
        users.forEach((k, v) -> {
            try {
                v.bufferedWriter.write("LEAVE " + userName);
                v.bufferedWriter.newLine();
                v.bufferedWriter.flush();
            } catch (IOException e) {
                System.out.println(139);
                disconnect(v);
                removeUser(v.userName);
            }

        });
    }

    private void sendError(String errorText) {
        try {
            users.get(this.userName).bufferedWriter.write("ERROR " + errorText);
            users.get(this.userName).bufferedWriter.newLine();
            users.get(this.userName).bufferedWriter.flush();
        } catch (NullPointerException e) {
            System.out.println(153);
        } catch (Exception e) {
            System.out.println(155);
            disconnect(users.get(this.userName));
            removeUser(users.get(this.userName).userName);
        }

    }

    private void disconnect(ServerMessagesHandler serverMessagesHandler) {
        try {
            serverMessagesHandler.interrupt();
            serverMessagesHandler.bufferedWriter.close();
            serverMessagesHandler.bufferedReader.close();
            removeUser(serverMessagesHandler.userName);
        } catch (Exception e) {
            System.out.println(169);
            removeUser(serverMessagesHandler.userName);
        }
    }

    private void removeUser(String name) {
        System.out.println("REMOVE:" + name);
        users.remove(name);
    }
}
