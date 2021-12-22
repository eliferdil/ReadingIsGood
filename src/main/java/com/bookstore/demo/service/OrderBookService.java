package com.bookstore.demo.service;

import com.bookstore.demo.model.*;
import com.bookstore.demo.repository.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBookService {

     @Autowired
    private OrderBookRepository orderBookRepository;

    public OrderBook createOrderBook(Order order, Book book, int quantity) {
        OrderBook orderBook = new OrderBook();
        orderBook.setOrder(order);
        orderBook.setBook(book);
        orderBook.setQuantity(quantity);
        return orderBookRepository.save(orderBook);
    }
}
