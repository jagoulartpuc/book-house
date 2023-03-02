package com.bideafactory.book.service;

import com.bideafactory.book.client.DiscountServiceClient;
import com.bideafactory.book.domain.Book;
import com.bideafactory.book.domain.request.DiscountRequest;
import com.bideafactory.book.domain.response.DiscountResponse;
import com.bideafactory.book.exception.InvalidDiscountException;
import com.bideafactory.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DiscountServiceClient discountServiceClient;

    public Book insertBook(Book book) throws IOException, InterruptedException {
        if (book.getDiscountCode() == null) {
            return bookRepository.save(book);
        } else {
            return getDiscount(book);
        }
    }

    private Book getDiscount(Book book) throws IOException, InterruptedException {
        DiscountRequest request = DiscountRequest.builder()
                .userId(book.getId())
                .houseId(book.getHouseId())
                .discountCode(book.getDiscountCode())
                .build();
        DiscountResponse response = discountServiceClient.postDiscount(request);
        if (response.isStatus()) {
            book.setDiscountCode(request.getDiscountCode());
            return bookRepository.save(book);
        } else {
            throw new InvalidDiscountException();
        }
    }
}
