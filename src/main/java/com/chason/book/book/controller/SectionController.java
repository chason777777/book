package com.chason.book.book.controller;

import com.chason.book.book.entity.BookSection;
import com.chason.book.book.pageHelper.PageInfo;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.BookSectionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by chason on 2018/8/23.11:01
 */
@RestController
@CrossOrigin
@RequestMapping("section")
public class SectionController {
    @Autowired
    private BookSectionService bookSectionService;

    @RequestMapping("addSection")
    public Result addSection(BookSection section,@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return bookSectionService.addBookSection(section,file);
    }

    @RequestMapping("deleteByUuid")
    public Result deleteByUuid(@RequestParam(value = "uuid", required = true)String bookUuid) throws Exception {
        return bookSectionService.deleteBookSection(bookUuid);
    }

    @RequestMapping("getByUuid")
    public Result getByUuid(@RequestParam(value = "uuid", required = true)String bookUuid) throws Exception {
        return bookSectionService.getBookSectionByUuid(bookUuid);
    }

    @RequestMapping("updateByUuid")
    public Result updateByUuid(BookSection section,@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        return bookSectionService.updateBookSection(section,file);
    }

    @RequestMapping("getAll")
    public Result getAll() throws Exception {
        List<BookSection> list = bookSectionService.getAll();
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
        List<BookSection> list = bookSectionService.getAll();
        if (CollectionUtils.isEmpty(list)) {
            return Result.error("无数据");
        } else {
            return Result.success(new PageInfo<BookSection>(list), "查询成功");
        }
    }
}
