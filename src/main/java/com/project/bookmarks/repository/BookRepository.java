package com.project.bookmarks.repository;

import com.project.bookmarks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByUserId(Long userId);
    @Query("from Book b where b.status like :available and b.title like :title and b.author like :author and b.genre like :genre")
    List<Book> searchBooks(String available, String title, String author, String genre);
}
