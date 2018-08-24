package com.chason.book.book.service;

import com.chason.book.book.entity.BookInfo;
import com.chason.book.book.result.Result;

import java.util.List;

/**
 * Created by chason on 2018/8/23.11:08
 */
public interface BookService {
    Result addBook(BookInfo bookInfo) throws Exception;

    Result deleteBook(String bookUuid) throws Exception;

    Result updateBook(BookInfo bookInfo) throws Exception;

    Result getBookInfoByUuid(String bookUuid) throws Exception;

    List<BookInfo> getAll() throws Exception;
}
