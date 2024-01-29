package com.study.VO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryVO {
    private int categoryId;
    private String categoryName;
}
