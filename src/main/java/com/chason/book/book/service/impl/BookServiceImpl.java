package com.chason.book.book.service.impl;

import com.chason.book.book.constants.Constants;
import com.chason.book.book.entity.BookInfo;
import com.chason.book.book.entity.BookInfoExample;
import com.chason.book.book.repository.BookInfoMapper;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.BookService;
import com.chason.book.book.util.DateTimeUtil;
import com.chason.book.book.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.print.Book;
import java.util.List;

/**
 * Created by chason on 2018/8/23.11:08
 */
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public Result addBook(BookInfo bookInfo) throws Exception {
        if (null == bookInfo || StringUtils.isEmpty(bookInfo.getName())) {
            return Result.error("参数不能为空");
        }

//        String s = null;
//        s.length();

        bookInfo.setUuid(UuidUtil.randomUUID());
        bookInfo.setUpdateTime(DateTimeUtil.nowDate());
        bookInfo.setCreateTime(DateTimeUtil.nowDate());

        boolean result = add(bookInfo);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.error("保存失败");
        }
    }

    @Override
    public Result deleteBook(String bookUuid) throws Exception {
        if (StringUtils.isEmpty(bookUuid)) {
            return Result.error("参数不能为空");
        }

        BookInfo bookInfo = new BookInfo();
        bookInfo.setUuid(bookUuid);
        bookInfo.setStatus(Constants.STATUS_DEL);

        boolean result = updateByUuid(bookInfo);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }

    @Override
    public Result updateBook(BookInfo bookInfo) throws Exception {
        if (null == bookInfo || StringUtils.isEmpty(bookInfo.getUuid())) {
            return Result.error("参数不能为空");
        }

        boolean result = updateByUuid(bookInfo);
        if (result) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    @Override
    public Result getBookInfoByUuid(String bookUuid) throws Exception {
        if (StringUtils.isEmpty(bookUuid)) {
            return Result.error("参数不能为空");
        }

        BookInfo bookInfo = getByUuid(bookUuid);
        if (null != bookInfo) {
            return Result.success(bookInfo, "查询成功");
        } else {
            return Result.error("查询失败");
        }
    }

    @Override
    public List<BookInfo> getAll() throws Exception {
        BookInfoExample bookInfoExample = new BookInfoExample();
        bookInfoExample.setOrderByClause("if(isnull(sort_no),0,1) desc,sort_no,create_time desc");
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andStatusEqualTo(Constants.STATUS_OK);
        return bookInfoMapper.selectByExample(bookInfoExample);
    }

    private boolean add(BookInfo bookInfo) throws Exception {
        return bookInfoMapper.insertSelective(bookInfo) > 0;
    }

    private boolean updateByUuid(BookInfo bookInfo) throws Exception {
        BookInfoExample bookInfoExample = new BookInfoExample();
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andUuidEqualTo(bookInfo.getUuid());

        bookInfo.setUpdateTime(DateTimeUtil.nowDate());
        return bookInfoMapper.updateByExampleSelective(bookInfo, bookInfoExample) > 0;
    }

    private BookInfo getByUuid(String bookUuid) throws Exception {
        BookInfoExample bookInfoExample = new BookInfoExample();
        BookInfoExample.Criteria criteria = bookInfoExample.createCriteria();
        criteria.andUuidEqualTo(bookUuid);

        List<BookInfo> list = bookInfoMapper.selectByExample(bookInfoExample);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
