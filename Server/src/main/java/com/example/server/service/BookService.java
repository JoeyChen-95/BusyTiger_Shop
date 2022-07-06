package com.example.server.service;

import com.example.server.mapper.BookMapper;
import com.example.server.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bookService")
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Book> selectAllBooks(){
        return bookMapper.selectAllBooks();
    }

    public Book selectBookById(Integer id){
        return bookMapper.selectBookById(id);
    }

    public void redisAddBook(Integer id, String name, String description){
        Map<String,String> map=new HashMap<>();
        map.put("id",id.toString());
        map.put("name",name);
        map.put("description",description);
        redisTemplate.opsForHash().putAll("book_"+id.toString(),map);

    }



}
