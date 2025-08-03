# Book Tracker

A modern Android reading companion that helps you organize your personal library, track reading progress, and discover new books based on your preferences.

## Features

### Personal Library Management
- **Track Reading Status**: Organize books into "To Read", "Currently Reading", and "Finished" categories
- **Progress Tracking**: Monitor reading progress with visual progress bars
- **Rating System**: Rate finished books with a 5-star system
- **Personal Notes**: Add thoughts and reviews for each book
- **Search Functionality**: Find books in your library by title, author, or genre

### Book Discovery
- **Personalized Recommendations**: Get book suggestions based on your highly rated finished books
- **Google Books Integration**: Real book data powered by Google Books API
- **Genre-Based Discovery**: Recommendations based on your favorite genres
- **Add to Library**: Add discovered books to your personal collection

### User Experience
- **Dark/Light Mode**: Toggle between themes with persistent preferences
- **Material Design 3**: Modern, clean interface following Material Design guidelines
- **Responsive Design**: Optimized for various screen sizes
- **Splash Screen**: Smooth app startup experience
- **Intuitive Navigation**: Bottom navigation for easy access to main features

## Technical Architecture

### Built With
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern UI toolkit
- **Room Database** - Local data persistence
- **Retrofit** - REST API client for Google Books integration
- **Material Design 3** - UI design system
- **Coroutines** - Asynchronous programming
- **Navigation Component** - App navigation

### Architecture Pattern
- **Repository Pattern** - Clean separation of data sources
- **MVVM Architecture** - Reactive UI with state management
- **Dependency Injection** - Manual DI for simplicity
- **Clean Architecture** - Organized by feature and responsibility

### Project Structure
```
com.example.booktracker/
├── data/
│   ├── api/              # Google Books API integration
│   ├── Book.kt           # Core data model
│   ├── BookDao.kt        # Database access
│   ├── BookRepository.kt # Data repository
│   └── ...
├── ui/
│   ├── screens/          # App screens
│   ├── components/       # Reusable UI components
│   └── theme/            # Theming and styling
└── MainActivity.kt       # Main app entry point
```

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.9+

### Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/book-tracker.git
   cd book-tracker
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository

3. **Build and Run**
   - Sync the project with Gradle files
   - Run the app on an emulator or physical device

### API Configuration
The app uses the Google Books API for book recommendations. No API key is required for basic usage, but rate limits may apply.

## How to Use

### Adding Your First Book
1. Tap the **+** button located at bottom right on the main screen
2. Fill in book details (title and author are required)
3. Select reading status, add genre, and notes
4. Tap **Save** to add to your library

### Tracking Reading Progress
1. Tap on any book in your "Currently Reading" section
2. Use the progress slider to update your reading percentage
3. Add notes about your thoughts while reading
4. Mark as "Finished" when complete and rate the book

### Discovering New Books
1. Navigate to the **Discover Books** tab
2. The app analyzes your highly-rated books (4+ stars)
3. Browse personalized recommendations based on your favorite genres
4. Tap **Add to Library** to save interesting books for later

### Managing Your Library
- **Search**: Use the search icon to find specific books
- **Filter by Status**: Books are automatically grouped by reading status
- **Edit Details**: Tap any book to update status, progress, rating, or notes
- **Delete Books**: Use the delete button in book details (with confirmation)

## Key Features in Detail

### Smart Recommendations
The recommendation system analyzes your reading history:
- Identifies favorite genres from your 4+ star rated books
- Fetches relevant suggestions from Google Books API
- Presents diverse recommendations across your preferred genres

### Progress Tracking
- Visual progress bars for currently reading books
- Automatic progress updates (0% for "To Read", 100% for "Finished")
- Percentage-based tracking for precise monitoring

### Data Persistence
- All data stored locally using Room database
- No internet required for core functionality
- Automatic backups of your reading data

## Configuration

### Theme Preferences
The app remembers your dark/light mode preference across app restarts. Toggle using the theme button in the top bar.

### Database Management
Your book data is stored locally and persists across app updates. The database automatically migrates when needed.

## Roadmap

### Planned Features
- [ ] Import books from a CSV
- [ ] Reading statistics and analytics
- [ ] Book cover image support
- [ ] Export reading data
- [ ] Advanced filtering and sorting

### Known Issues
- Large recommendation lists may take time to load
- Internet connection required for book discovery