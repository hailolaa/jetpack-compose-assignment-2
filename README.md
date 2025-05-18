# jetpack-compose-assignment-2

This Android application demonstrates a complete Jetpack Compose-based implementation of a TODO list viewer with offline caching and navigation.

## 🔹 Features
- Fetches TODO items from JSONPlaceholder API (`https://jsonplaceholder.typicode.com/todos`)
- Displays TODO items in a scrollable Compose list using `LazyColumn`
- Caches data locally using Room for offline access and performance
- Displays full TODO item details on a separate screen
- Graceful navigation between list and detail screens using Compose Navigation
- MVVM architecture with Repository pattern for clean separation of concerns
- Handles loading states and error messages
- Kotlin Coroutines + Retrofit for network operations

## 🔧 Tech Stack
- Jetpack Compose (UI)
- Retrofit (API)
- Room (Persistence)
- Kotlin Coroutines
- MVVM Architecture
- Navigation Compose
