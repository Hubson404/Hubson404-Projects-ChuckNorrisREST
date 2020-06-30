import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ChuckRest {

    private static final Set<String> seenJokesId = new HashSet<>();
    private static final String HOST = "https://api.chucknorris.io/jokes/random";


    public static String getJoke() throws IOException {

        String joke;
        boolean wasJokeAlreadySeen = false;

        do {
            URL url = new URL(HOST);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            JsonElement je = JsonParser.parseString(content.toString());
            joke = je.getAsJsonObject().get("value").getAsString();
            wasJokeAlreadySeen = !seenJokesId.add(je.getAsJsonObject().get("id").toString());
        } while (wasJokeAlreadySeen);

        return joke;
    }
}
