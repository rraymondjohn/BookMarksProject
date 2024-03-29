package com.project.bookmarks.service;

import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.NewBookRequest;
import com.project.bookmarks.model.request.BorrowRequest;
import com.project.bookmarks.model.request.SearchRequest;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(NewBookRequest newBookRequest);

    Book updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    List<Book> searchBooks(SearchRequest searchRequest);

    List<Book> getBooksByUserId(Long id);

    Book borrowBook(BorrowRequest borrowRequest);

    Book returnBook(Long id);
}
