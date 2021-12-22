package com.bookstore.demo.service;

import com.bookstore.demo.model.Book;
import com.bookstore.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book findById(long id) {
        return bookRepository.findById(id);
    }

    public void decreaseStock(Book book, int orderQuantity) {
        book.setStock(book.getStock() - orderQuantity);
        bookRepository.save(book);
    }
}
