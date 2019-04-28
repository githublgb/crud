package com.lgb.bootweb.util;

import java.io.Serializable;
import java.util.List;

public class PageResult<E> implements Serializable {
    private static final long serialVersionUID = -1273175334927538692L;
    private Long total;
    private List<E> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<E> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<E> getRows() {
        return this.rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }
}
