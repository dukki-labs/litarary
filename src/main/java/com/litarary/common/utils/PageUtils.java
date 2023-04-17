package com.litarary.common.utils;

public class PageUtils {

    public static int getTotalPage(int totalCount, int size) {
        return (int) Math.ceil((double) totalCount / size);
    }

    public static boolean isLastPage(int page, int totalPage) {
        return page >= totalPage;
    }
}
