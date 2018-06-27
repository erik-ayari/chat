package methods;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ServerSession {

    private int serverPort;

    public ServerSession(int serverPort) {
        this.serverPort = serverPort;
    }

    public void registerUser(String username, String ip) throws JSONException, IOException {

        String pfad = "src/methods/users.json";
        File file = new File(pfad);
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");

        JSONObject json = new JSONObject(content);


        for (Iterator it = json.keys(); it.hasNext(); ) {

            String item = (String) it.next();

            if (item.equals(username)) {
                JSONObject userJSON = new JSONObject(json.get(item));
                userJSON.put("ip", ip);

                json.put(item, userJSON);
                FileWriter fw = new FileWriter(new File(pfad));
                fw.write(json.toString());
                fw.flush();
                fw.close();
                return;

            }
        }

        JSONObject userJSON = new JSONObject();
        userJSON.put("password", "GETPASSWORD METHOD");
        userJSON.put("ip", ip);

        json.put(username, userJSON);

        FileWriter fw = new FileWriter(new File(pfad));
        fw.write(json.toString());
        fw.flush();
        fw.close();

    }

    public boolean login(String username, String password) throws IOException, JSONException {


        String pfad = "src/methods/passwords.json";
        File file = new File(pfad);
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");

        JSONObject json = new JSONObject(content);

        for (Iterator it = json.keys(); it.hasNext(); ) {

            String jsonUsername = (String) it.next();

            if (jsonUsername.equals(username)) {

                if (json.get(jsonUsername).equals(password)) {

                    return true;

                }

                return false;

            }

        }

        return false;

    }

    public String[] getCurrentChatrooms() throws IOException, JSONException {

        String pfad = "src/methods/chatrooms.json";
        File file = new File(pfad);
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");

        JSONObject json = new JSONObject(content);

        ArrayList<String> chatrooms = new ArrayList<>();

        for (Iterator it = json.keys(); it.hasNext(); ) {

            String name = (String) it.next();
            chatrooms.add(name);

        }

        String[] stringArray = new String[chatrooms.size()];
        chatrooms.toArray(stringArray);

        return stringArray;

    }


    public String[] waitForMessage() {
        try {

            ServerSocket serverSocket = new ServerSocket(this.serverPort);

            //Eine Verbindung wird eingegangen
            Socket socket = serverSocket.accept();


            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String[] input = (String[]) in.readObject();

            String art = input[0];


            serverSocket.close();

            switch (art) {
                case "msg":
                    String message = input[1];
                    String channel = input[2];

                    // an alle austeilen
                    int port = socket.getPort();

                    ArrayList<String> userIPS = new ArrayList<>();

                    String pfad = "src/methods/users.json";
                    File file = new File(pfad);
                    String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");

                    JSONObject json = new JSONObject(content);

                    for (Iterator it = json.keys(); it.hasNext(); ) {

                        String ip = (String) it.next();
                        userIPS.add(ip);

                    }

                    //System.out.println(userIPS);

                    for (String ip : userIPS) {
                        DistributeMessages temp = new DistributeMessages(new String[]{"msgDistribute", message, (String) json.get(ip), channel}, ip, port);
                        temp.start();

                    }


                    //ALle Benutzer austeilen {msgDistribute, message, user, chatroom}

                    return new String[]{message, channel};

                case "login":
                    String username = input[1];
                    String password = input[2];
                    String ip = socket.getInetAddress().toString().replace("/", "");

                    System.out.println(ip);

                    boolean success = login(username, password);

                    Socket answer = new Socket(ip, 34567);
                    PrintWriter out = new PrintWriter(answer.getOutputStream(), true);
                    out.println(success);

                    return waitForMessage();

                case "getChatrooms":

                    String[] chatrooms = getCurrentChatrooms();
                    ip = socket.getInetAddress().toString().replace("/", "");

                    answer = new Socket(ip, 34568);
                    ObjectOutput objectOut = new ObjectOutputStream(answer.getOutputStream());
                    objectOut.writeObject(chatrooms);


                    return waitForMessage();


            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new String[]{"ERROR"};
    }


}
