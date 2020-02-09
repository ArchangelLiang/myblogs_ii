package com.gwg.util;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

    private int total;
    private int pages;
    private int currentPage;
    private List<T> result;
    private List<Integer> pageNums;

    public PageResult() {
    }

    public PageResult(int total, int pages, int currentPage,List<T> result) {
        this.total = total;
        this.pages = pages;
        this.currentPage = currentPage;
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean isFirstPage(){
        return this.currentPage == 1;
    }

    public boolean isLastPage(){
        return this.currentPage == this.pages;
    }

    public List<Integer> getPageNums() {
        return pageNums;
    }

    public void setPageNums(List<Integer> pageNums) {
        this.pageNums = pageNums;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", pages=" + pages +
                ", currentPage=" + currentPage +
                '}';
    }
}
