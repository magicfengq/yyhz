package com.yyhz.sc.base.page;

import java.util.Collection;

public class PageContainer<E, K, V> implements Page<E> {

    /**
     * 记录的总数
     */
    private long totalnum = 0;

    /**
     * 记录的总页数
     */
    private int totalpage = 0;

    /**
     * 记录集合
     */
    private Collection<E> info;

    public PageContainer(long totalnum, int totalpage, Collection<E> info) {
        this.totalnum = totalnum;
        this.totalpage = totalpage;
        this.info = info;
    }

    @Override
    public long getTotalnum() {
        return totalnum;
    }

    @Override
    public int getTotalpage() {
        return totalpage;
    }

    @Override
    public Collection<E> getInfo() {
        return info;
    }
}
