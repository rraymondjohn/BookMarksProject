package com.project.bookmarks.repository;

import com.project.bookmarks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT * FROM books b WHERE b.status LIKE :available AND b.title LIKE :title AND b.author LIKE :author AND b.genre LIKE :genre")
    List<Book> searchBooks(String available, String title, String author, String genre);
}
