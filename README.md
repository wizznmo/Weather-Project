# Weather-Project

# JavaFX Weather Information App

A real-time desktop weather client developed in Java. This application provides a user-friendly graphical interface built with JavaFX to fetch and display current weather data from the live **OpenWeatherMap API**.

---

## Features
- **Real-Time Weather Data**: Displays temperature, humidity, wind speed, and a textual description of weather conditions.  
- **Dynamic Weather Icons**: Represents current conditions (e.g., sun, clouds, rain) with dynamically loaded icons.  
- **Unit Conversion**: Switch between Metric (°C, kph) and Imperial (°F, mph) units.  
- **Search History**: Keeps a session-based log of recent city searches with timestamps.  
- **Dynamic Theming**: Background color reflects time of day (bright blue for day, dark blue for night).  
- **Robust Error Handling**: User-friendly alerts for invalid city names, missing keys, or network errors.  

---

## Technology Stack
- **Language**: Java (JDK 11+)  
- **GUI Framework**: JavaFX  
- **API**: OpenWeatherMap  
- **Networking**: Java HttpClient  
- **JSON Parsing**: org.json (JSON-java library)  

---

## Architecture Overview
The design separates the GUI from the API logic for simplicity and modularity.

+-------------------+ +------------------+ +--------------------+
| WeatherApp |----->| WeatherService |----->| OpenWeatherMap API |
| (JavaFX GUI) |<-----| (API Handler) |<-----| (External Service) |
+-------------------+ +------------------+ +--------------------+


---

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11+  
- JavaFX SDK (download from [Gluon](https://gluonhq.com/products/javafx/))  
- JSON-java library (`json-20231013.jar`)  
- OpenWeatherMap API key (free at [openweathermap.org](https://openweathermap.org/))  

### Setup
1. Add your API key in `WeatherService.java` (`API_KEY` constant).  
2. Configure VS Code:
   - `.vscode/settings.json` → JavaFX SDK path  
   - `.vscode/launch.json` → include JSON library  

---

## How It Works
1. User enters a city and clicks **Get Weather**.  
2. `WeatherApp` sends request → `WeatherService`.  
3. `WeatherService` builds URL → makes HTTP GET request to API.  
4. JSON response parsed into a `JSONObject`.  
5. Data passed back to `WeatherApp` → GUI updates with new info and history log.  

---

## Project Structure
- `WeatherApp.java` → Main JavaFX application (GUI, event handling, user interaction).  
- `WeatherService.java` → API communication (builds requests, parses responses).  
