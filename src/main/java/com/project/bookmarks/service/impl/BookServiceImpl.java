package com.project.bookmarks.service.impl;

import com.project.bookmarks.exception.ResourceNotFoundException;
import com.project.bookmarks.model.Book;
import com.project.bookmarks.model.BookStatus;
import com.project.bookmarks.model.request.BookRequest;
import com.project.bookmarks.model.request.NewBookRequest;
import com.project.bookmarks.model.request.BorrowRequest;
import com.project.bookmarks.model.request.SearchRequest;
import com.project.bookmarks.repository.BookRepository;
import com.project.bookmarks.repository.UserRepository;
import com.project.bookmarks.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
//        if(bookRequest.getUserId() != null) {
//            updatedBook.setBorrowedDate(bookRequest.getBorrowedDate());
//            updatedBook.setDueDate(bookRequest.getDueDate());
//            updatedBook.setUser(userRepository.findById(bookRequest.getUserId())
//                    .orElseThrow(()-> new ResourceNotFoundException("User not found!")));
//        }
//        if(!updatedBook.getUser().toString().isEmpty()) {
//            if (LocalDateTime.now().isAfter(updatedBook.getDueDate())) {
//                updatedBook.setStatus(BookStatus.Overdue.toString());
//            } else {
//                updatedBook.setStatus(bookRequest.getStatus());
//            }
//        }
        return bookRepository.save(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(SearchRequest searchRequest){
        String searchedTitle = "%" + searchRequest.getTitle() + "%";
        String searchedAuthor = "%" + searchRequest.getAuthor() + "%";
        String searchedGenre = "%" + searchRequest.getGenre() + "%";
        String searchedStatus = "%%";
        if(searchRequest.getIsAvailable()){
            searchedStatus = "%Available%";
        }
        System.out.println(searchedTitle + " " + searchedAuthor + " " + searchedGenre + " " + searchedStatus);
        return bookRepository.searchBooks(searchedStatus, searchedTitle, searchedAuthor, searchedGenre);
    }

    public List<Book> getBooksByUserId(Long id){
        List<Book> borrowedBooks = bookRepository.findAllByUserId(id);
        return borrowedBooks;
    }

    public Book borrowBook(BorrowRequest borrowRequest){
        Book borrowedBook = getBookById(Long.parseLong(borrowRequest.getBookId()));
        if(borrowedBook.getStatus().equals(BookStatus.Available.toString())){
            LocalDateTime currentDateTime = LocalDateTime.now();
            borrowedBook.setStatus(BookStatus.OnLoan.toString());
            borrowedBook.setBorrowedDate(currentDateTime);
            LocalDate dueDate = LocalDate.now();
            LocalDateTime dueDateTime = LocalTime.MAX.atDate(dueDate);
            borrowedBook.setDueDate(dueDateTime);
            borrowedBook.setUser(userRepository.findById(Long.parseLong(borrowRequest.getUserId()))
                    .orElseThrow(()-> new ResourceNotFoundException("User not found!")));
            borrowedBook = bookRepository.save(borrowedBook);
        } else {

        }
        return borrowedBook;
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
