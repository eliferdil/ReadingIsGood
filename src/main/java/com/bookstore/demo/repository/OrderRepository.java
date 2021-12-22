package com.bookstore.demo.repository;

import com.bookstore.demo.model.Order;
import com.bookstore.demo.model.dto.ReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Order findById(long id);

    Page<Order> findAllByUserIdOrderByCreatedAtDesc(long userId, Pageable pageable);

    List<Order> findAll();

    Page<Order> findByCreatedAtBetweenOrderByCreatedAtDesc(Timestamp dateFrom, Timestamp dateTo, Pageable pageable);

    @Query(value = "select MONTH(ord.createdAt) as month, count(ord) as orderCount, sum(ord.totalPrice) as totalPrice, sum(orderBook.quantity) as bookCount " +
            "from Order ord, OrderBook orderBook " +
            "where orderBook.order.id = ord.id and ord.user.id = :userId and ord.createdAt between :dateFrom and :dateTo " +
            "group by(MONTH(ord.createdAt))")
    ReportDto findOrderStatisticsByUserId(long userId, Timestamp dateFrom, Timestamp dateTo);

}
