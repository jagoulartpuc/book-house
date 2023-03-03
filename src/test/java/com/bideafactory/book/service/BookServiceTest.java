package com.bideafactory.book.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bideafactory.book.client.DiscountServiceClient;
import com.bideafactory.book.domain.Book;
import com.bideafactory.book.domain.request.DiscountRequest;
import com.bideafactory.book.domain.response.DiscountResponse;
import com.bideafactory.book.exception.InvalidDiscountException;
import com.bideafactory.book.repository.BookRepository;

@SpringBootTest
public class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private DiscountServiceClient discountServiceClient;

    @Autowired
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInsertBookWithoutDiscount() throws IOException, InterruptedException {
        Book book = new Book();
        book.setId("1");
        book.setName("John");
        book.setLastName("Doe");
        book.setAge(30);
        book.setStartDate(LocalDate.of(2022, 1, 1));
        book.setEndDate(LocalDate.of(2022, 2, 1));
        book.setPhoneNumber("1234567890");
        book.setHouseId("123");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.insertBook(book);

        Assertions.assertEquals(book, savedBook);
    }

    @Test
    public void testInsertBookWithDiscount() throws IOException, InterruptedException {
        Book book = new Book();
        book.setId("1");
        book.setName("John");
        book.setLastName("Doe");
        book.setAge(30);
        book.setStartDate(LocalDate.of(2022, 1, 1));
        book.setEndDate(LocalDate.of(2022, 2, 1));
        book.setPhoneNumber("1234567890");
        book.setHouseId("123");
        book.setDiscountCode("DISCOUNT");

        DiscountRequest request = DiscountRequest.builder()
                .userId(book.getId())
                .houseId(book.getHouseId())
                .discountCode(book.getDiscountCode())
                .build();
        DiscountResponse response = new DiscountResponse();
        response.setStatus(true);

        when(discountServiceClient.postDiscount(request)).thenReturn(response);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.insertBook(book);

        Assertions.assertEquals(book, savedBook);
    }

    @Test
    public void testInsertBookWithInvalidDiscount() throws IOException, InterruptedException {
        Book book = new Book();
        book.setId("1");
        book.setName("John");
        book.setLastName("Doe");
        book.setAge(30);
        book.setStartDate(LocalDate.of(2022, 1, 1));
        book.setEndDate(LocalDate.of(2022, 2, 1));
        book.setPhoneNumber("1234567890");
        book.setHouseId("123");
        book.setDiscountCode("DISCOUNT");

        DiscountRequest request = DiscountRequest.builder()
                .userId(book.getId())
                .houseId(book.getHouseId())
                .discountCode(book.getDiscountCode())
                .build();
        DiscountResponse response = new DiscountResponse();
        response.setStatus(false);

        when(discountServiceClient.postDiscount(request)).thenReturn(response);
        Assertions.assertThrows(InvalidDiscountException.class, () -> bookService.insertBook(book));
    }

}