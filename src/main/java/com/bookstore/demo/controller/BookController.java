package com.bookstore.demo.controller;

import com.bookstore.demo.exception.CustomException;
import com.bookstore.demo.exception.CustomExceptionConstants;
import com.bookstore.demo.model.Book;
import com.bookstore.demo.model.dto.*;
import com.bookstore.demo.repository.BookRepository;
import com.bookstore.demo.util.ResponseStatusEnum;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Book Controller")
@RestController
@RequestMapping("rest/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value = "Persist New Book (Only ADMIN user)", response = ResponseDto.class, authorizations = {
            @Authorization(value = "Only ADMIN user can persist new book.")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 102, message = "Book already exists.")})
    @PostMapping
    @Secured ({"ROLE_ADMIN"})
    public ResponseDto create(@RequestBody @Valid BookRegisterDto bookRegisterDto) {

        Book book = bookRepository.findByName(bookRegisterDto.getName());
        if(book != null) {
            throw new CustomException(CustomExceptionConstants.BOOK_ALREADY_EXISTS_ERROR_CODE, CustomExceptionConstants.BOOK_ALREADY_EXISTS_ERROR_MSG);
        }

        book = new Book();
        book.setName(bookRegisterDto.getName());
        book.setStock(bookRegisterDto.getStock());
        book.setPrice(bookRegisterDto.getPrice());
        bookRepository.save(book);

        return new ResponseDto(ResponseStatusEnum.SUCCESS.getStatus(), "Book registration is successful. Book ID: " + book.getId());
    }

    @ApiOperation(value = "Update Book's Stock (Only ADMIN user)", response = ResponseDto.class, authorizations = {
            @Authorization(value = "Only ADMIN user can update the book's stock.")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 103, message = "Book is not found.")})
    @PostMapping("/update-stock")
    @Secured ({"ROLE_ADMIN"})
    public ResponseDto updateStock(@RequestBody @Valid BookUpdateDto bookUpdateDto) {

        Book book = bookRepository.findById(bookUpdateDto.getId());
        if(book == null) {
            throw new CustomException(CustomExceptionConstants.BOOK_NOT_FOUND_ERROR_CODE, CustomExceptionConstants.BOOK_NOT_FOUND_ERROR_MSG);
        }

        book.setStock(bookUpdateDto.getStock());
        bookRepository.save(book);

        return new ResponseDto(ResponseStatusEnum.SUCCESS.getStatus(), "Book stock update is successful.");
    }
}
