package com.example.server.service;

import com.example.server.mapper.BookMapper;
import com.example.server.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookService")
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    public List<Book> selectAllBooks(){
        return bookMapper.selectAllBooks();
    }

    public Book selectBookById(Integer id){
        return bookMapper.selectBookById(id);
    }



}
