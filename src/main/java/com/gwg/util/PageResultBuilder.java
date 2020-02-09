package com.gwg.util;

import java.util.ArrayList;
import java.util.List;

public class PageResultBuilder {

    public static <T> PageResult<T> builder(List<T> result,int pageNum,int pageSize,int total){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum);
        pageResult.setResult(result);
        pageResult.setTotal(total);
        int pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        pageResult.setPages(pages);
        ArrayList<Integer> pageNums = new ArrayList<>();
        for (int i = 1; i <= pages; i++) {
            pageNums.add(i);
        }
        pageResult.setPageNums(pageNums);
        return pageResult;
    }

}
