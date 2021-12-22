package com.bookstore.demo.repository;

import com.bookstore.demo.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book findByName(String name);
    Book findById(long id);
    List<Book> findAll();
}
