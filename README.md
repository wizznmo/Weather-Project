# Weather-Project

JavaFX Weather Information App 

A real-time desktop weather client developed in Java. This application provides a user-friendly graphical interface built with JavaFX to fetch and display current weather data from the live OpenWeatherMap API. 

Features 

Real-Time Weather Data: Fetches and displays current temperature, humidity, wind speed, and a textual description of weather conditions. 

Dynamic Weather Icons: Visually represents the current weather conditions (e.g., sun, clouds, rain) by dynamically loading icons from the web. 

Unit Conversion: Allows the user to instantly switch between Metric (Celsius, kph) and Imperial (Fahrenheit, mph) units. 

Search History: Maintains a session-based log of recent city searches, complete with timestamps, for easy reference. 

Dynamic Theming: The application's background color automatically changes to reflect the time of day (a brighter blue for daytime, a darker blue for nighttime). 

Robust Error Handling: Provides clear, user-friendly alert dialogs for common issues such as invalid city names or network failures. 

Technology Stack 

Language: Java (JDK 11+) 

GUI Framework: JavaFX 

API: OpenWeatherMap API 

Networking: Java's built-in HttpClient 

JSON Parsing: org.json (JSON-java library) 

Architecture Overview 

The application follows a simple, decoupled design to separate the user interface from the data-fetching logic. 

 +-------------------+      +------------------+      +--------------------+ 
  |   WeatherApp      |----->|  WeatherService  |----->| OpenWeatherMap API | 
  | (JavaFX GUI)      |<-----|  (API Handler)   |<-----| (External Service) | 
  +-------------------+      +------------------+      +--------------------+ 
 
 

Getting Started 

Prerequisites 

Java Development Kit (JDK): Version 11 or higher must be installed. 

JavaFX SDK: The platform-specific SDK is required. It can be downloaded from Gluon. 

JSON-java Library: The json-20231013.jar file is required. 

OpenWeatherMap API Key: A free, personal API key is necessary to make requests. 

Setup 

Add API Key: Open the WeatherService.java file and place your personal API key in the API_KEY constant. 

Configure VS Code: Set up the .vscode/settings.json and .vscode/launch.json files to correctly point to your local JavaFX SDK path and the JSON library. 

How It Works 

The user enters a city name in the GUI and clicks "Get Weather". 

The WeatherApp class captures this input and calls the getWeatherData() method in the WeatherService. 

The WeatherService builds the appropriate URL, makes an HTTP GET request to the OpenWeatherMap API, and receives a JSON response. 

The JSON response is parsed into a JSONObject. 

The JSONObject is returned to the WeatherApp class, which then updates all the JavaFX labels, images, and the history view with the new data. 

Project Structure 

WeatherApp.java # Main application class. Manages all GUI components, event handling, and user interaction. 

WeatherService.java # Handles all API communication, including building requests and parsing responses. 
