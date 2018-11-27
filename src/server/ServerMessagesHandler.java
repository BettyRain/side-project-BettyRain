package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ServerMessagesHandler extends Thread {
    private static ConcurrentHashMap<String, ServerMessagesHandler> users = new ConcurrentHashMap<>();
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Thread thread;
    private String userName;
    private long lastPing, save;

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
                if (input.startsWith("JOIN")) {
                    if (input.split(" ").length > 1 && !users.containsKey(input.split(" ")[1])) {
                        users.put(input.split(" ")[1], this);
                        userName = input.split(" ")[1];
                        sendOK();
                        sendUserJoined(userName);
                        thread = new Thread(() -> {
                            int count = 0;
                            while (!interrupted()) {
                                try {
                                    bufferedWriter.write("PING");
                                    bufferedWriter.newLine();
                                    bufferedWriter.flush();
                                    lastPing = System.currentTimeMillis();
                                    save = lastPing;
                                    TimeUnit.MILLISECONDS.sleep(5000);
                                    if (lastPing == save) {
                                        count++;
                                    } else if (count == 2) {
                                        System.out.println("lastPing - FAIL" + lastPing);
                                        sendError("PING-PONG FAIL");
                                        sendUserLeave(userName);
                                        disconnect(ServerMessagesHandler.this);
                                        thread.interrupt();
                                        thread = null;
                                    } else {
                                        count = 0;
                                    }
                                } catch (InterruptedException | IOException e) {
                                    sendError("PING-PONG FAIL");
                                    sendUserLeave(userName);
                                    disconnect(ServerMessagesHandler.this);
                                    thread.interrupt();
                                    thread = null;
                                }
                            }
                        });
                        thread.setDaemon(true);
                        thread.start();
                    } else {
                        try {
                            bufferedWriter.write("LOGIN ALREADY IN USE");
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }
                        disconnect(this);
                    }
                } else if (input.startsWith("MESSAGE")) {
                    if (input.split(" ").length > 1) {
                        sendMessageToAll(input.split(" ", 2)[1]);
                    } else {
                        sendError("MESSAGE FORMAT ERROR");
                        disconnect(this);
                    }

                } else if (input.startsWith("LEAVE")) {
                    sendUserLeave(userName);
                    disconnect(this);
                } else if (input.startsWith("PONG")) {
                    lastPing = 999;
                } else {
                    sendError("UNKNOWN COMMAND");
                }
            }
        } catch (IOException e) {
            System.out.println("USER DISCONNECTED");
            sendUserLeave(userName);
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
        if (users.containsKey(userName)) {
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
    }

    private void sendError(String errorText) {
        System.out.println("err" + this.userName);
        users.forEach((k, v) -> {
            System.out.println(v.userName + ":" + v.lastPing + ":" + v.save);
        });
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
            thread.interrupt();
            serverMessagesHandler.bufferedWriter.close();
            serverMessagesHandler.bufferedReader.close();
            removeUser(serverMessagesHandler.userName);
        } catch (Exception e) {
            System.out.println(169);
            removeUser(serverMessagesHandler.userName);
        }
    }

    private void removeUser(String name) {
        if (name != null)
            users.remove(name);
    }
}
