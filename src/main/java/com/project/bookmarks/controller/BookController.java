package com.project.bookmarks.controller;

import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.NewBookRequest;
import com.project.bookmarks.model.request.BorrowRequest;
import com.project.bookmarks.model.request.SearchRequest;
import com.project.bookmarks.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path="/bookmarks/books")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    //Get all books
    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    //Get 1 book
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    //Add Book
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@Valid @RequestBody NewBookRequest newBookRequest){
        Book newBook = bookService.addBook(newBookRequest);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }
    //Update Book
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id,@Valid @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }
    //Delete Book
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book deleted!", HttpStatus.OK);
    }
    //Search Book
    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestBody SearchRequest searchRequest) {
        List<Book> searchedBooks = bookService.searchBooks(searchRequest);
        return new ResponseEntity<>(searchedBooks, HttpStatus.OK);
    }
    //Reserve Book
    @PutMapping("/borrow")
    public ResponseEntity<Book> borrowBook(@RequestBody BorrowRequest borrowRequest){
        Book borrowedBook = bookService.borrowBook(borrowRequest);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }
    //Book Returned
    @PutMapping("/return/{id}")
    public ResponseEntity<Book> returnBook(@PathVariable("id") Long id){
        Book returnedBook = bookService.returnBook(id);
        return new ResponseEntity<>(returnedBook, HttpStatus.OK);
    }
}
