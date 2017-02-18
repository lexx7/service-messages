package com.lexx7.servicemessages.web.model;


import java.util.ArrayList;

public class ListDataModel<T> {

    private Long page;

    private Long total;

    private Long records;

    private ArrayList<T> rows;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public ArrayList<T> getRows() {
        return rows;
    }

    public void setRows(ArrayList<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ListDataModel{" +
                "page=" + page +
                ", total=" + total +
                ", records=" + records +
                ", rows" +
                '}';
    }
}
