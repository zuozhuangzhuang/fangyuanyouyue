package com.fangyuanyouyue.wallet.dao;

import com.fangyuanyouyue.wallet.model.UserBalanceDetail;

public interface UserBalanceDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBalanceDetail record);

    int insertSelective(UserBalanceDetail record);

    UserBalanceDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBalanceDetail record);

    int updateByPrimaryKey(UserBalanceDetail record);
}