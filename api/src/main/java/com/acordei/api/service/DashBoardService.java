package com.acordei.api.service;

import com.acordei.api.dao.DashBoardDao;
import com.acordei.api.domain.DashBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashBoardService {

    @Autowired
    DashBoardDao dashBoardDao;

    public List<DashBoard> findDashBoardDatas() {
        return dashBoardDao.findDashBoardDatas();
    }
}
