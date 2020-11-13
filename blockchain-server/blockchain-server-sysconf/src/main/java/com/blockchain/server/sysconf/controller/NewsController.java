package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.sysconf.controller.api.NewsApi;
import com.blockchain.server.sysconf.service.NewsService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(NewsApi.NEWS_API)
@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public void setNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    @ApiOperation(value = NewsApi.LISTNEWS.METHOD_TITLE_NAME, notes = NewsApi.LISTNEWS.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO ListNews(@ApiParam(NewsApi.LISTNEWS.METHOD_API_TYPE) @RequestParam(value = "type") Integer type, HttpServletRequest request) {
        return ResultDTO.requstSuccess(newsService.listNews(type, HttpRequestUtil.getUserLocale(request), null, null));
    }

    @ApiOperation(value = NewsApi.NEWDETAIL.METHOD_TITLE_NAME, notes = NewsApi.NEWDETAIL.METHOD_TITLE_NOTE)
    @GetMapping("/getNewsById")
    public ResultDTO NewsDetail(@ApiParam(NewsApi.NEWDETAIL.METHOD_API_ID) @RequestParam(value = "newsId") String newsId) {
        return ResultDTO.requstSuccess(newsService.getNewsById(newsId));
    }
}
