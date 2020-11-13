package com.blockchain.common.base.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> extends BaseDTO {
    /* 总条目数 */
    private Long total;
    /* 当前页码 */
    private Integer pageNum;
    /* 每页记录数 */
    private Integer pageSize;
    /* 第一页页码 */
    private Integer firstPage;
    /* 上一页页码 */
    private Integer prePage;
    /* 下一页页码 */
    private Integer nextPage;
    /* 最后一页页码，最大页数 */
    private Integer lastPage;
    /* 结果集 */
    private List<T> rows;

    /**
     * 构造函数
     *
     * @param total    : 总数
     * @param pageNum  : 当前页数
     * @param pageSize : 页数大小
     * @param rows     : 集合
     */
    public PageDTO(Long total, Integer pageNum, Integer pageSize, List<T> rows) {
        this.total = total;
        this.pageSize = pageSize;
        this.rows = rows;
        if (total == null || total == 0) {
            this.total = 0l;
            this.pageNum = 1;
            this.firstPage = 1;
            this.prePage = 1;
            this.nextPage = 1;
            this.lastPage = 1;
        } else {
            this.pageNum = pageNum;
            this.firstPage = 1;
            long pageTotal = total / pageSize;
            this.lastPage = (int) ((total % pageSize == 0) ? pageTotal : pageTotal + 1);
            this.prePage = this.pageNum - 1;
            this.nextPage = this.pageNum == lastPage ? 0 : this.pageNum + 1;
        }
    }
}
