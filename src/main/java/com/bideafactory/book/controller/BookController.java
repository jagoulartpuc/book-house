package com.bideafactory.book.controller;

import com.bideafactory.book.domain.Book;
import com.bideafactory.book.exception.InvalidDiscountException;
import com.bideafactory.book.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/bideafactory")
public class BookController {

    @Autowired
    private BookService bookService;

    private static Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @PostMapping("/book")
    public ResponseEntity<String> postBook(@RequestBody Book book) {
        try {
            bookService.insertBook(book);
            LOGGER.info("Book created successfully: " + book);
            return ResponseEntity.ok("Book Accepted");
        } catch (InvalidDiscountException | IOException | InterruptedException e) {
            LOGGER.error("Error in book " + book.getId() + ": " + e.getMessage());
            return ResponseEntity.status(409).body("Invalid discount");
        }
    }
}
