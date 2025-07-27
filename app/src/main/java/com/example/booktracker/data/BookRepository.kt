package com.example.booktracker.data

import androidx.compose.runtime.mutableStateListOf

class BookRepository {
    val books = mutableStateListOf(
        Book(
            title = "Atomic Habits",
            author = "James Clear",
            status = BookStatus.READING,
            rating = 5,
            notes = "Taking notes on habit formation",
            progress = 30,
            genre = "Self-Help"
        ),
        Book(
            title = "1984",
            author = "George Orwell",
            status = BookStatus.FINISHED,
            rating = 4,
            notes = "Chilling ending, very relevant today",
            progress = 100,
            genre = "Dystopian Fiction",
            dateFinished = System.currentTimeMillis() - 86400000
        ),
        Book(
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            status = BookStatus.TO_READ,
            rating = 0,
            notes = "",
            progress = 0,
            genre = "Fantasy"
        ),
        Book(
            title = "Deep Work",
            author = "Cal Newport",
            status = BookStatus.READING,
            rating = 3,
            notes = "Good focus tips for productivity",
            progress = 45,
            genre = "Self-Help"
        ),
        Book(
            title = "Dune",
            author = "Frank Herbert",
            status = BookStatus.TO_READ,
            rating = 0,
            notes = "",
            progress = 0,
            genre = "Science Fiction"
        ),
        Book(
            title = "The Midnight Library",
            author = "Matt Haig",
            status = BookStatus.FINISHED,
            rating = 4,
            notes = "Really makes you reflect on life choices",
            progress = 100,
            genre = "Fiction",
            dateFinished = System.currentTimeMillis() - 172800000
        )
    )
    fun addBook(book: Book) {
        books.add(book)
    }
    
    }
