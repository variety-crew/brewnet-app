package com.varc.brewnetapp.domain.returning.query.mapper;

import com.varc.brewnetapp.domain.exchange.query.aggregate.vo.ExchangeListVO;
import com.varc.brewnetapp.domain.returning.query.aggregate.vo.ReturningListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReturningMapper {
    int selectReturningListCnt();

    List<ReturningListVO> selectReturningList(long offset, long pageSize);

    List<ReturningListVO> selectAllReturningList();

}
