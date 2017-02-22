package com.mljr.operators.service.primary.operators.impl;

import com.mljr.operators.dao.primary.operators.IUserInfoDAO;
import com.mljr.operators.entity.model.operators.UserInfo;
import com.mljr.operators.service.primary.operators.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gaoxi
 * @time 2017/2/22
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private IUserInfoDAO IUserInfoDAO;

    @Override
    public UserInfo getByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }
        return IUserInfoDAO.getByMobile(mobile);
    }
}
