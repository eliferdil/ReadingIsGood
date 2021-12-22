package com.bookstore.demo.repository;

import com.bookstore.demo.model.CurrentlyProcessingItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentlyProcessingItemRepository extends CrudRepository<CurrentlyProcessingItem, Long> {

    CurrentlyProcessingItem findById(long id);
}
