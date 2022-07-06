package com.example.server.mapper;

import com.example.server.model.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> selectAllBooks();
    Book selectBookById(Integer id);

}
