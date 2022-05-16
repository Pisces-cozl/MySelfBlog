package com.blogadmin.model.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParam {
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
