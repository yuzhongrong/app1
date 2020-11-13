package com.blockchain.server.base.controller;

import com.blockchain.common.base.dto.PageDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class BaseController {

    /**
     * 构造分页对象
     * <p>
     * 构造控制层返回分页对象
     * <p>
     * 后台控制层统一返回该方法
     *
     * @param list
     * @return
     */
    protected <T> ResultDTO<PageDTO> generatePage(List<T> list) {
        //通过构造器生成一个分页信息对象
        PageInfo pageInfo = new PageInfo(list);
        //构造返回前端分页信息DTO
        PageDTO pageDTO = new PageDTO(pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getList());
        return ResultDTO.requstSuccess(pageDTO);
    }
}
