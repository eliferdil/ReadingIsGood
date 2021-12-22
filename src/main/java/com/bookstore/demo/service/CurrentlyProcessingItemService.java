package com.bookstore.demo.service;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.CurrentlyProcessingItem;
import com.bookstore.demo.repository.CurrentlyProcessingItemRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrentlyProcessingItemService {

    @Autowired
    private CurrentlyProcessingItemRepository currentlyProcessingItemRepository;

    private static final Logger logger = LogManager.getLogger(CurrentlyProcessingItemService.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CurrentlyProcessingItem checkProcessingItem(long id) {
        return currentlyProcessingItemRepository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CurrentlyProcessingItem insertProcessingItem(long id) {
        try {
            CurrentlyProcessingItem currentlyProcessingItem = new CurrentlyProcessingItem();
            currentlyProcessingItem.setId(id);
            return currentlyProcessingItemRepository.save(currentlyProcessingItem);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Item is attempted to update simultaneously, will be rollback. Item ID: " + id);
            throw new CustomException(CustomExceptionConstants.GENERAL_EXCEPTION_ERROR_CODE, CustomExceptionConstants.GENERAL_EXCEPTION_ERROR_MSG);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeProcessingItem(CurrentlyProcessingItem currentlyProcessingItem) {
        currentlyProcessingItemRepository.delete(currentlyProcessingItem);
    }

    public void checkItemAvailability(long id) {
        CurrentlyProcessingItem currentlyProcessingItem = checkProcessingItem(id);
        if(currentlyProcessingItem != null)
            throw new CustomException(CustomExceptionConstants.GENERAL_EXCEPTION_ERROR_CODE, CustomExceptionConstants.GENERAL_EXCEPTION_ERROR_MSG);
    }

}
