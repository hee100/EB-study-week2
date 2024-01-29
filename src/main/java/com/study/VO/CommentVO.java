package com.study.VO;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentVO {
    private Long commentId;
    private Long boardId;
    private String content;
    private LocalDateTime createdDate;
}
