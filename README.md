## My Weather

My Weather is an app designed to provide real-time weather forecasts for various locations,
including your current location.
The app is built using a robust multi-module architecture to ensure clear separation of concerns and
maintain scalability.
Each feature is implemented as an independent module, adhering to clean architecture principles with
distinct data and presentation layers for better organization and maintainability.

## Features

* Current Weather Data: View real-time weather forecasts for your current location using GPS-based
  latitude and longitude for precise updates.
* Search: Search for cities and access detailed weather forecasts for specific locations.
* Favorites Management: Save cities to a list of favorites for easy access, even offline.
* 7-Day Weather Forecast: Access an extended seven-day forecast for saved or searched locations,
  with cached data for offline support.

## Prerequisites

* Android Studio: Jellyfish version or later
* Android Gradle Plugin: Version 8.2.2
* Kotlin Version 1.9.20
* Java Version 17

## Getting Started

* Clone the repository git clone https://github.com/kelienzo/DigiMart.git
* Open the project in android studio
* Resolve any configuration issues and allow the project to build.
* Run the app on your preferred emulator or device.

## Architecture Flow

The project is structured using a multi-module architecture to ensure better separation of concerns
and scalability.
Each feature or screen is represented as an independent module, adhering to clean architecture
principles with distinct layers:

Data Layer:

* This layer houses all data-related components, including:
* Data Sources: Includes the network API (using Retrofit) and database operations.
* Database: Contains DAO (Data Access Object) and Entity classes for local storage.
* Repository: Serves as the bridge between the data sources and the presentation layer,
  encapsulating data retrieval and processing logic.

Presentation Layer:

* This layer focuses on the user interface and state management, comprising:

UI Screens: Built with Jetpack Compose for modern and dynamic user interfaces.
ViewModels: Manage UI-related data and business logic, ensuring separation of concerns.

## Technologies

* Kotlin: The primary programming language, recommended by Google for Android development.
* Jetpack Compose: Modern toolkit for building native UI efficiently.
* MVVM Architecture: Cleanly separates business and presentation logic from the UI.
* Room Persistence Library: Provides an abstraction layer over SQLite for simplified database
  access.
* Kotlin Coroutines: For managing asynchronous operations.
* Kotlin Flows: Enables reactive programming for UI updates.
* Coil: Image loading library with built-in caching for performance optimization.
* Retrofit: A type-safe HTTP client for making API requests seamlessly.
* Dagger Hilt: Simplifies dependency injection and reduces boilerplate code.
* Gradle Version Catalogs: Manages dependencies and plugins in a scalable way.
* JUnit: Unit testing framework.
* Kotlinx Coroutines Test: For testing coroutine-based code.

