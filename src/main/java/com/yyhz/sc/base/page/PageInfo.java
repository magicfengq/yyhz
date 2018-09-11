package com.yyhz.sc.base.page;

import java.util.List;

public class PageInfo<T> {

    private int page = 1;

    private int pages = 0;

    private int pageSize = 10;

    private int total = 0;


    private List<T> list;

    private List<T> rows;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page == null) {
            this.page = 1;
        } else if (page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }

    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = 10;
        } else if (pageSize <= 0) {
            this.pageSize = 10;
        } else if (pageSize > 50) {
            this.pageSize = 50;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void initPages() {
        pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    }
}
