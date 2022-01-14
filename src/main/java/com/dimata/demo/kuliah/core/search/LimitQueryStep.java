package com.dimata.demo.kuliah.core.search;

public interface LimitQueryStep {
    String result();
    long getPage();
    long getLimit();
    long getOffset();
    void setPage(long page);
    void setLimit(long limit);
}
