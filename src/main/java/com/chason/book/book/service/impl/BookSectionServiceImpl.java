package com.chason.book.book.service.impl;

import com.chason.book.book.constants.Constants;
import com.chason.book.book.entity.BookSection;
import com.chason.book.book.entity.BookSectionExample;
import com.chason.book.book.repository.BookSectionMapper;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.BookSectionService;
import com.chason.book.book.util.DateTimeUtil;
import com.chason.book.book.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by chason on 2018/8/23.11:08
 */
@Service("bookSectionService")
public class BookSectionServiceImpl implements BookSectionService {
    @Autowired
    private BookSectionMapper bookSectionMapper;

    @Override
    public Result addBookSection(BookSection bookSection, MultipartFile file) throws Exception {
        if (null == bookSection || StringUtils.isEmpty(bookSection.getName())) {
            return Result.error("参数不能为空");
        }

        if (null != file) {
            String content  = new String(file.getBytes(),"gbk");
            if (!StringUtils.isEmpty(content)) {
                bookSection.setContent(content);
            }
        }

        bookSection.setUuid(UuidUtil.randomUUID());
        bookSection.setUpdateTime(DateTimeUtil.nowDate());
        bookSection.setCreateTime(DateTimeUtil.nowDate());

        boolean result = add(bookSection);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.error("保存失败");
        }
    }

    @Override
    public Result deleteBookSection(String sectionUuid) throws Exception {
        if (StringUtils.isEmpty(sectionUuid)) {
            return Result.error("参数不能为空");
        }

        BookSection bookSection = new BookSection();
        bookSection.setUuid(sectionUuid);
        bookSection.setStatus(Constants.STATUS_DEL);

        boolean result = updateByUuid(bookSection);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public Result updateBookSection(BookSection bookSection, MultipartFile file) throws Exception {
        if (null == bookSection || StringUtils.isEmpty(bookSection.getUuid())) {
            return Result.error("参数不能为空");
        }

        if (null != file) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            StringBuffer content = new StringBuffer();
            String line = "";
            while (StringUtils.isEmpty(line = bufferedReader.readLine())) {
                content.append(line);
            }

            bufferedReader.close();
            if (!StringUtils.isEmpty(content.toString())) {
                bookSection.setContent(content.toString());
            }
        }

        boolean result = updateByUuid(bookSection);
        if (result) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    @Override
    public Result getBookSectionByUuid(String sectionUuid) throws Exception {
        if (StringUtils.isEmpty(sectionUuid)) {
            return Result.error("参数不能为空");
        }

        BookSection bookSection = getByUuid(sectionUuid);
        if (null != bookSection) {
            return Result.success(bookSection, "查询成功");
        } else {
            return Result.error("查询失败");
        }
    }

    @Override
    public List<BookSection> getAll() throws Exception {
        BookSectionExample bookSectionExample = new BookSectionExample();
        bookSectionExample.setOrderByClause("if(isnull(sort_no),0,1) desc,sort_no,create_time");
        BookSectionExample.Criteria criteria = bookSectionExample.createCriteria();
        criteria.andStatusEqualTo(Constants.STATUS_OK);
        return bookSectionMapper.selectByExample(bookSectionExample);
    }

    private boolean add(BookSection bookSection) throws Exception {
        return bookSectionMapper.insertSelective(bookSection) > 0;
    }

    private boolean updateByUuid(BookSection bookSection) throws Exception {
        BookSectionExample bookSectionExample = new BookSectionExample();
        BookSectionExample.Criteria criteria = bookSectionExample.createCriteria();
        criteria.andUuidEqualTo(bookSection.getUuid());

        bookSection.setUpdateTime(DateTimeUtil.nowDate());
        return bookSectionMapper.updateByExampleSelective(bookSection, bookSectionExample) > 0;
    }

    private BookSection getByUuid(String sectionUuid) throws Exception {
        BookSectionExample bookSectionExample = new BookSectionExample();
        BookSectionExample.Criteria criteria = bookSectionExample.createCriteria();
        criteria.andUuidEqualTo(sectionUuid);

        List<BookSection> list = bookSectionMapper.selectByExample(bookSectionExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
