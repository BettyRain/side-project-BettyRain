package client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Chat {
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;


    public void init(String nick) {
        try {
            if (bufferedWriter != null | bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {

                }
                bufferedReader = null;
                bufferedWriter = null;
            }
            Socket socket = new Socket("localhost", 7070);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));


            if (join(nick)) {
                String help;
                while ((help = bufferedReader.readLine()) != null) {
                    if (help.startsWith("JOIN")) {
//                        Launcher.g2.printJOIN(help);
                    } else if (help.startsWith("MESSAGE")) {
                        System.out.println("USER MESSAGE: " + help);
//                        Launcher.g2.printMsg(help);
                    } else if (help.startsWith("LEAVE")) {
//                        Launcher.g2.printLeave(help);
                    } else if (help.startsWith("ERROR")) {
//                        Launcher.g2.printERROR(help);
                    } else {
                        System.out.println("UNKNOWN COMMAND" + help);
                    }
                }

            } else {
/*                Launcher.g2.printERROR("Connection Refuesed. NICK ALREADY IN USE" );
                Launcher.g2.printLeave("s Вы");//TODO
*/            }
        } catch (IOException e) {
/*            Launcher.g2.printERROR("Connection Refuesed. NICK ALREADY IN USE" );
            Launcher.g2.printLeave("s Вы");//TODO
*/        }
    }

    private boolean join(String nick) {
        try {
            bufferedWriter.write("JOIN " + nick);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String answer = bufferedReader.readLine();
            if (answer.equals("OK")) {
                System.out.println("CONNECTION ESTABLISHED");
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write("MESSAGE " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            bufferedWriter.write("LEAVE");
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
