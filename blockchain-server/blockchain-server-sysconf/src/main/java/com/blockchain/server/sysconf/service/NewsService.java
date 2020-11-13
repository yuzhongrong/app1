package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.dto.NewsDTO;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;

import java.util.Date;
import java.util.List;

public interface NewsService {

    List<NewsDTO> listNews(Integer type, String languages, Date beginTime, Date endTime);

    List<News> listAll(NewsQueryConditionDTO condition);

    News getNewsById(String id);


}
