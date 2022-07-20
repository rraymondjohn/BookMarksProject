package com.project.bookmarks.service.impl;

import com.project.bookmarks.exception.ResourceNotFoundException;
import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.BookStatus;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.NewBookRequest;
import com.project.bookmarks.model.request.ReserveRequest;
import com.project.bookmarks.repository.BookRepository;
import com.project.bookmarks.repository.UserRepository;
import com.project.bookmarks.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getAllBooks(){

        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
         Book book = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book not found!"));
//        if(LocalDateTime.now().isAfter(book.getDueDate())) {
//            book.setStatus(BookStatus.Overdue.toString());
//        } else {
//            updatedBook.setStatus(bookRequest.getStatus());
//        }

        return book;
    }

    public Book addBook(NewBookRequest newBookRequest) {
        Book newBook = new Book();
        newBook.setTitle(newBookRequest.getTitle());
        newBook.setAuthor(newBookRequest.getAuthor());
        newBook.setDescription(newBookRequest.getDescription());
        newBook.setGenre(newBookRequest.getGenre());
        newBook.setImageSource(newBookRequest.getImageSource());
        newBook.setStatus(BookStatus.Available.toString());
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
        updatedBook.setUser(userRepository.findById(bookRequest.getUserId()).get());

        if(LocalDateTime.now().isAfter(updatedBook.getDueDate())) {
            updatedBook.setStatus(BookStatus.Overdue.toString());
        } else {
            updatedBook.setStatus(bookRequest.getStatus());
        }
        return bookRepository.save(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


    public Book reserveBook(ReserveRequest reserveRequest){
        Book reservedBook = getBookById(Long.parseLong(reserveRequest.getBookId()));
        LocalDateTime currentDateTime = LocalDateTime.now();
        reservedBook.setStatus(BookStatus.OnLoan.toString());
        reservedBook.setBorrowedDate(currentDateTime);
        reservedBook.setDueDate(currentDateTime.plusDays(14));
        reservedBook.setUser(userRepository.findById(Long.parseLong(reserveRequest.getUserId()))
                .orElseThrow(()-> new ResourceNotFoundException("User not found!")));
        log.info("Reserving book info: "+reservedBook);
        return bookRepository.save(reservedBook);
    }

    public Book returnBook(Long id){
        Book returnedBook = getBookById(id);
        returnedBook.setStatus(BookStatus.Available.toString());
        returnedBook.setBorrowedDate(null);
        returnedBook.setDueDate(null);
        returnedBook.setUser(null);
        return bookRepository.save(returnedBook);
    }
}
