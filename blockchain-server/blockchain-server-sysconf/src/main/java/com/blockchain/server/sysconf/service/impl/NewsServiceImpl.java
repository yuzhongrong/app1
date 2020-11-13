package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.sysconf.dto.NewsDTO;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;
import com.blockchain.server.sysconf.mapper.NewsMapper;
import com.blockchain.server.sysconf.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsMapper newsMapper;

    @Autowired
    public void setNewsMapper(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public List<NewsDTO> listNews(Integer type, String languages, Date beginTime, Date endTime) {
        List<NewsDTO> news = newsMapper.listNewsDTO(type,languages,beginTime,endTime);
        return news;
    }

    @Override
    public List<News> listAll(NewsQueryConditionDTO condition) {
        return newsMapper.listAll(condition);
    }

    @Override
    public News getNewsById(String id) {
        return newsMapper.selectByPrimaryKey(id);
    }

}
