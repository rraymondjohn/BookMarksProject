package com.project.bookmarks.service;

import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.request.BookRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(BookRequest bookRequest);

    Book updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}
