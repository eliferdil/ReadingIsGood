package com.bookstore.demo.service;

import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.*;
import com.bookstore.demo.model.dto.OrderBookDto;
import com.bookstore.demo.model.dto.OrderBookListDto;
import com.bookstore.demo.model.dto.OrderListDto;
import com.bookstore.demo.repository.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CurrentlyProcessingItemService currentlyProcessingItemService;

    @Autowired
    private OrderBookService orderBookService;

    public Order findById(long id) {
        return orderRepository.findById(id);
    }

    public Page<Order> findByCreatedAtBetweenOrderByCreatedAtDesc(Timestamp fromDate, Timestamp toDate, PageRequest pageRequest) {
        return orderRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(fromDate, toDate, pageRequest);
    }

    public Page<Order> findAllByUserIdOrderByCreatedAtDesc(long id, PageRequest pageRequest) {
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(id, pageRequest);
    }

    @Transactional
    public Map<Long, String> processOrder(List<OrderBookDto> bookList, User currentUser) {
        Map<Long, String> resultMap = new HashMap<>();
        double totalPrice = 0;

        Order order = save(currentUser);

        for(OrderBookDto orderBookDto: bookList) {
            currentlyProcessingItemService.checkItemAvailability(orderBookDto.getBookId());

            int orderQuantity = orderBookDto.getQuantity();

            Book book = bookService.findById(orderBookDto.getBookId());
            if(book == null) {
                resultMap.put(orderBookDto.getBookId(), CustomExceptionConstants.BOOK_NOT_FOUND_ERROR_MSG);
            } else if (!checkStock(book.getStock(), orderQuantity)) {
                resultMap.put(orderBookDto.getBookId(), CustomExceptionConstants.BOOK_OUT_OF_STOCK_ERROR_MSG);
            } else {
                CurrentlyProcessingItem currentlyProcessingItem = currentlyProcessingItemService.insertProcessingItem(book.getId());

                processOrderBook(order, book, orderQuantity);
                totalPrice += BigDecimal.valueOf(book.getPrice() * orderQuantity).setScale(2, RoundingMode.HALF_UP).doubleValue();
                resultMap.put(orderBookDto.getBookId(), "Book is added to order. Order ID: " + order.getId());

                currentlyProcessingItemService.removeProcessingItem(currentlyProcessingItem);
            }
        }

        updateTotalPrice(order, totalPrice);

        logger.info("Order is created. Order ID: " + order.getId());

        return resultMap;
    }

    public List<OrderListDto> mapOrderListToDtoList(List<Order> orders) {
        List<OrderListDto> orderDtos = new ArrayList<>();

        for(Order order: orders) {
            List<OrderBook> orderBooks =  order.getOrderBooks();

            List<OrderBookListDto> orderBookDtos = new ArrayList<>();

            for(OrderBook orderBook: orderBooks) {
                OrderBookListDto orderBookDto = new OrderBookListDto();
                orderBookDto.setBookId(orderBook.getBook().getId());
                orderBookDto.setBookName(orderBook.getBook().getName());
                orderBookDto.setQuantity(orderBook.getQuantity());
                orderBookDtos.add(orderBookDto);
            }

            OrderListDto orderListDto = new OrderListDto();
            orderListDto.setOrderBookList(orderBookDtos);
            orderListDto.setOrderDate(order.getCreatedAt());
            orderListDto.setTotalPrice(order.getTotalPrice());
            orderDtos.add(orderListDto);
        }

        return orderDtos;
    }

    public OrderBookListDto mapOrderBookToDto(OrderBook orderBook) {
        OrderBookListDto orderBookDto = new OrderBookListDto();
        orderBookDto.setBookId(orderBook.getBook().getId());
        orderBookDto.setQuantity(orderBook.getQuantity());

        return orderBookDto;
    }

    private Order save(User currentUser) {
        Order order = new Order();
        order.setUser(currentUser);
        return orderRepository.save(order);
    }

    private void updateTotalPrice(Order order, double totalPrice) {
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
    }

    private boolean checkStock(int stock, int quantity) {
        return stock >= quantity;
    }

    private void processOrderBook(Order order, Book book, int quantity) {
        orderBookService.createOrderBook(order, book, quantity);
        bookService.decreaseStock(book, quantity);
    }
}
