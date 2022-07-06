package com.example.server.controller;

import com.example.server.model.Book;
import com.example.server.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/selectAllBooks")
    public List<Book> selectAllBooks(){
        return bookService.selectAllBooks();
    }

    @GetMapping(value = "/selectBookById")
    public Book selectBookById(@RequestParam("bookId") Integer bookId){
        return bookService.selectBookById(bookId);
    }

    @PostMapping(value = "/redisAddBook")
    public void redisAddBook(@RequestParam("bookId") Integer bookId,@RequestParam("bookName") String bookName,@RequestParam("bookDesc") String bookDesc){
        bookService.redisAddBook(bookId,bookName,bookDesc);
    }



}
