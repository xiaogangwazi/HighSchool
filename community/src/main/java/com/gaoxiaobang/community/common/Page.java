package com.gaoxiaobang.community.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T>  implements Serializable {
    private int curr;//当前页数
    private int total;//总数
    private int pageTotal;//总页数
    private int pageSize;//每页大小
    private List<T> result = new ArrayList<>();

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "curr=" + curr +
                '}';
    }
}
