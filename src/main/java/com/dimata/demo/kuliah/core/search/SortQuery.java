package com.dimata.demo.kuliah.core.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SortQuery {
    private String sortBy;
    private boolean asc;
}
