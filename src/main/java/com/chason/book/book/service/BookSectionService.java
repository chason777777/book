package com.chason.book.book.service;

import com.chason.book.book.entity.BookSection;
import com.chason.book.book.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by chason on 2018/8/23.11:08
 */
public interface BookSectionService {
    Result addBookSection(BookSection section, MultipartFile file) throws Exception;

    Result deleteBookSection(String sectionUuid) throws Exception;

    Result updateBookSection(BookSection section, MultipartFile file) throws Exception;

    Result getBookSectionByUuid(String sectionUuid) throws Exception;

    List<BookSection> getAll() throws Exception;
}
