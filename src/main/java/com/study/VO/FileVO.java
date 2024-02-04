package com.study.VO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileVO {
    private Long fileId;
    private Long boardId;
    private String realName;
    private String saveName;
    private int size;
}
