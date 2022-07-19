package com.project.bookmarks.service;

import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.NewBookRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(NewBookRequest newBookRequest);

    Book updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}
