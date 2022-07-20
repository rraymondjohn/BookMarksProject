package com.project.bookmarks.service;

import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.NewBookRequest;
import com.project.bookmarks.model.request.ReserveRequest;
import com.project.bookmarks.model.request.SearchRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(NewBookRequest newBookRequest);

    Book updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    List<Book> searchBooks(SearchRequest searchRequest);

    Book reserveBook(ReserveRequest reserveRequest);

    Book returnBook(Long id);
}
