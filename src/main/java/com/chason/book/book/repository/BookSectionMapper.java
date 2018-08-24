package com.chason.book.book.repository;

import com.chason.book.book.entity.BookSection;
import com.chason.book.book.entity.BookSectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSectionMapper {
    int countByExample(BookSectionExample example);

    int deleteByExample(BookSectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookSection record);

    int insertSelective(BookSection record);

    List<BookSection> selectByExampleWithBLOBs(BookSectionExample example);

    List<BookSection> selectByExample(BookSectionExample example);

    BookSection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookSection record, @Param("example") BookSectionExample example);

    int updateByExampleWithBLOBs(@Param("record") BookSection record, @Param("example") BookSectionExample example);

    int updateByExample(@Param("record") BookSection record, @Param("example") BookSectionExample example);

    int updateByPrimaryKeySelective(BookSection record);

    int updateByPrimaryKeyWithBLOBs(BookSection record);

    int updateByPrimaryKey(BookSection record);
}