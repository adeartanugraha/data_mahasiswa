package com.dimata.demo.kuliah.core.search;

public interface JoinOperationStep {
    JoinQueryStep on(WhereQueryStep operationStep);
}
