package com.chason.book.book.controller;

import com.chason.book.book.entity.BookInfo;
import com.chason.book.book.pageHelper.PageInfo;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.BookService;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.internal.crypto.RsaMd5CksumType;

import java.security.cert.TrustAnchor;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by chason on 2018/8/23.11:01
 */
@RestController
@CrossOrigin
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("addBook")
    public Result addBook(BookInfo bookInfo) throws Exception {
        return bookService.addBook(bookInfo);
    }

    @RequestMapping("deleteByUuid")
    public Result deleteByUuid(@RequestParam(value = "uuid", required = true)String bookUuid) throws Exception {
        return bookService.deleteBook(bookUuid);
    }

    @RequestMapping("getByUuid")
    public Result getByUuid(@RequestParam(value = "uuid", required = true)String bookUuid) throws Exception {
        return bookService.getBookInfoByUuid(bookUuid);
    }

    @RequestMapping("updateByUuid")
    public Result updateByUuid(BookInfo bookInfo) throws Exception {
        return bookService.updateBook(bookInfo);
    }

    @RequestMapping("getAll")
    public Result getAll() throws Exception {
        List<BookInfo> list = bookService.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return Result.error("无数据");
        } else {
            return Result.success(list, "查询成功");
        }
    }

    @RequestMapping("get4Page")
    public Result getAll4Page(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                              @RequestParam(value = "pageSize", required = true, defaultValue = "1") Integer pageSize) throws Exception {
        PageHelper.startPage(page, pageSize);
        List<BookInfo> list = bookService.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return Result.error("无数据");
        } else {
            return Result.success(new PageInfo<BookInfo>(list), "查询成功");
        }
    }
}
