package com.study.VO;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
public class BoardVO {
    private Long boardId;
    private int categoryId;
    private String title;
    private String name;
    private String content;
    private String passWord;
    private Long viewCount;
    private LocalDateTime createdData;
    private Timestamp updateData;

}
