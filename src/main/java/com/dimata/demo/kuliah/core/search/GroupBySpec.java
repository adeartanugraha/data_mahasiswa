package com.dimata.demo.kuliah.core.search;

public interface GroupBySpec {
    GroupBySpec merge(GroupBySpec groupBy);
    GroupBySpec append(String group);
    String getQuery();
}
