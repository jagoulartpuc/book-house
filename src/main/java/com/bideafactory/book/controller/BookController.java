package com.bideafactory.book.controller;

import com.bideafactory.book.domain.Book;
import com.bideafactory.book.exception.InvalidDiscountException;
import com.bideafactory.book.service.BookService;
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

    @PostMapping("/book")
    public ResponseEntity<?> postBook(@RequestBody Book book) {
        try {
            bookService.insertBook(book);
            return ResponseEntity.ok("Book Accepted");
        } catch (InvalidDiscountException | IOException | InterruptedException e) {
            return ResponseEntity.status(409).body("Invalid discount");
        }
    }
}
