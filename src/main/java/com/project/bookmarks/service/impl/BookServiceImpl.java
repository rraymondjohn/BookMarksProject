package com.project.bookmarks.service.impl;

import com.project.bookmarks.exception.ResourceNotFoundException;
import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.repository.BookRepository;
import com.project.bookmarks.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found!"));
    }

    public Book addBook(BookRequest bookRequest) {
        Book newBook = new Book();
        return bookRepository.save(newBook);
    }

    public Book updateBook(Long id, BookRequest bookRequest) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found!"));
        updatedBook.setTitle(bookRequest.getTitle());
        updatedBook.setDescription(bookRequest.getDescription());
        updatedBook.setAuthor(bookRequest.getAuthor());
        updatedBook.setImageSource(bookRequest.getImageSource());
        updatedBook.setGenre(bookRequest.getGenre());
        updatedBook.setBorrowedDate(bookRequest.getBorrowedDate());
        updatedBook.setDueDate(bookRequest.getDueDate());
        updatedBook.setUserId(bookRequest.getUserId());
        return bookRepository.save(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
