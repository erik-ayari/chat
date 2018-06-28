package methods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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

    public static String[] getCurrentChatrooms() throws IOException, JSONException {

        String pfad = "src/methods/data/chatrooms.json";
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

        System.out.println(Arrays.toString(stringArray));

        return stringArray;

    }

    public boolean login(String username, String password) throws IOException, JSONException {


        String pfad = "src/methods/data/passwords.json";
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

    public String[] getAllUsers() throws IOException, JSONException {
        String pfad = "src/methods/data/users.json";
        File file = new File(pfad);
        String content = new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8");

        JSONObject json = new JSONObject(content);

        ArrayList<String> users = new ArrayList<>();

        for (Iterator it = json.keys(); it.hasNext(); ) {

            String ip = (String) it.next();
            users.add((String) json.get(ip));

        }

        String[] out = new String[users.size()];
        users.toArray(out);
        return out;


    }
    

}
