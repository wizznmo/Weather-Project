import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 * A service class to handle interactions with the OpenWeatherMap API.
 */
public class WeatherService {

    // IMPORTANT: Replace "YOUR_API_KEY" with your actual OpenWeatherMap API key.
    private static final String API_KEY = "1f72eb500f8f7d8a366e500a9b7d5c2c";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Fetches weather data for a given city.
     * @param city The name of the city.
     * @return A JSONObject containing the weather data.
     * @throws Exception if the API request fails or the city is not found.
     */
    public JSONObject getWeatherData(String city) throws Exception {
        // Format the URL with the city name and API key.
        String urlString = String.format(API_URL, city.replace(" ", "%20"), API_KEY);
        
        // Create an HTTP request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .build();

        // Send the request and get the response.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the request was successful (HTTP status code 200).
        if (response.statusCode() == 200) {
            // Parse the JSON response body and return it.
            return new JSONObject(response.body());
        } else {
            // If the city is not found (404) or another error occurs, throw an exception.
            throw new RuntimeException("Failed to fetch weather data. Status code: " + response.statusCode());
        }
    }
}
