package com.blockchain.server.teth.common.util;

import com.blockchain.server.teth.common.constants.EthWalletConstants;
import com.github.pagehelper.PageHelper;

/**
 * 常用方法简易工具
 */
public class BaseHelperUtil {

    /**
     * 查询分页处理
     *
     * @param pageNum  当前页数
     * @param pageSize 页数展示条数
     */
    public static void startPage(Integer pageNum, Integer pageSize) {
        if (null == pageNum) pageNum = 0;
        if (null == pageSize) pageSize = EthWalletConstants.BASE_PAGESIZE;
        PageHelper.startPage(pageNum, pageSize);
    }
}
