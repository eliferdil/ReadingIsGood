package com.bookstore.demo.repository;

import com.bookstore.demo.model.OrderBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBookRepository extends CrudRepository<OrderBook, Long> {

}
