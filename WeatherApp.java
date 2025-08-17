import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A Weather Information App with a JavaFX GUI.
 * This application fetches and displays real-time weather data from the OpenWeatherMap API.
 */
public class WeatherApp extends Application {

    // --- GUI Components ---
    private BorderPane root;
    private TextField cityInput;
    private Label weatherInfoLabel;
    private Label forecastLabel;
    private ImageView weatherIcon;
    private ListView<String> historyListView;
    private ToggleGroup unitToggleGroup;
    private boolean isCelsius = true;

    private WeatherService weatherService = new WeatherService();
    private List<String> searchHistory = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Information App");

        // --- Layout Setup ---
        root = new BorderPane();
        root.setPadding(new Insets(20));

        // --- Top: Input Area ---
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);
        cityInput = new TextField();
        cityInput.setPromptText("Enter City Name");
        Button searchButton = new Button("Get Weather");
        searchButton.setOnAction(e -> fetchWeatherData());
        inputBox.getChildren().addAll(new Label("Location:"), cityInput, searchButton);
        root.setTop(inputBox);

        // --- Center: Weather Display ---
        VBox weatherDisplayBox = new VBox(20);
        weatherDisplayBox.setAlignment(Pos.CENTER);
        weatherIcon = new ImageView();
        weatherIcon.setFitHeight(100);
        weatherIcon.setFitWidth(100);
        weatherInfoLabel = new Label("Enter a city to see the weather.");
        weatherInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        forecastLabel = new Label();
        forecastLabel.setFont(Font.font("Arial", 16));
        weatherDisplayBox.getChildren().addAll(weatherIcon, weatherInfoLabel, forecastLabel);
        root.setCenter(weatherDisplayBox);

        // --- Right: Controls and History ---
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(0, 0, 0, 20));

        // Unit Conversion
        VBox unitBox = new VBox(5);
        Label unitLabel = new Label("Units:");
        unitToggleGroup = new ToggleGroup();
        RadioButton celsiusButton = new RadioButton("Celsius/kph");
        celsiusButton.setToggleGroup(unitToggleGroup);
        celsiusButton.setSelected(true);
        celsiusButton.setOnAction(e -> { isCelsius = true; });
        RadioButton fahrenheitButton = new RadioButton("Fahrenheit/mph");
        fahrenheitButton.setToggleGroup(unitToggleGroup);
        fahrenheitButton.setOnAction(e -> { isCelsius = false; });
        unitBox.getChildren().addAll(unitLabel, celsiusButton, fahrenheitButton);

        // History
        VBox historyBox = new VBox(5);
        historyBox.getChildren().add(new Label("Search History:"));
        historyListView = new ListView<>();
        historyListView.setPrefHeight(200);

        rightPanel.getChildren().addAll(unitBox, historyBox, historyListView);
        root.setRight(rightPanel);

        // --- Dynamic Background ---
        updateBackground();

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Fetches weather data from the service and updates the UI.
     */
    private void fetchWeatherData() {
        String city = cityInput.getText();
        if (city == null || city.trim().isEmpty()) {
            showAlert("Error", "City name cannot be empty.");
            return;
        }

        try {
            JSONObject weatherData = weatherService.getWeatherData(city);
            updateUI(weatherData);
            addToHistory(city);
        } catch (Exception e) {
            showAlert("Error", "Could not fetch weather data. Please check the city name or your network connection.");
            e.printStackTrace();
        }
    }

    /**
     * Updates the GUI with the fetched weather data.
     * @param data The JSON object containing weather information.
     */
    private void updateUI(JSONObject data) {
        // Current Weather
        JSONObject main = data.getJSONObject("main");
        JSONObject wind = data.getJSONObject("wind");
        JSONObject weather = data.getJSONArray("weather").getJSONObject(0);

        double temp = main.getDouble("temp");
        double humidity = main.getDouble("humidity");
        double windSpeed = wind.getDouble("speed");
        String description = weather.getString("description");
        String iconCode = weather.getString("icon");
        
        // Unit Conversion
        String tempStr, windStr;
        if (isCelsius) {
            tempStr = String.format("%.1f°C", temp);
            windStr = String.format("%.1f kph", windSpeed * 3.6); // m/s to kph
        } else {
            tempStr = String.format("%.1f°F", (temp * 9/5) + 32);
            windStr = String.format("%.1f mph", windSpeed * 2.237); // m/s to mph
        }

        weatherInfoLabel.setText(String.format("Temperature: %s\nHumidity: %.0f%%\nWind Speed: %s\nConditions: %s",
                tempStr, humidity, windStr, description));

        // Set Weather Icon
        String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
        weatherIcon.setImage(new Image(iconUrl, true)); // true for background loading

        // For simplicity, this app doesn't fetch a multi-day forecast.
        // We will just display a message.
        forecastLabel.setText("Forecast data requires a separate API call in the free tier.");
    }

    /**
     * Updates the application's background based on the time of day.
     */
    private void updateBackground() {
        int hour = LocalDateTime.now().getHour();
        String bgColor;
        if (hour >= 6 && hour < 18) { // Day
            bgColor = "#87CEEB"; // Sky Blue
        } else { // Night
            bgColor = "#2c3e50"; // Midnight Blue
        }
        root.setStyle("-fx-background-color: " + bgColor + ";");
    }

    /**
     * Adds a search query to the history list.
     * @param city The city that was searched for.
     */
    private void addToHistory(String city) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String historyEntry = String.format("%s - %s", city, timestamp);
        searchHistory.add(0, historyEntry); // Add to the top of the list
        historyListView.getItems().setAll(searchHistory);
    }

    /**
     * Displays a simple alert dialog.
     * @param title The title of the alert.
     * @param message The message content.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
