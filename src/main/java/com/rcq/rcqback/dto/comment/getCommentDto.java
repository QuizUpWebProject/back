package com.rcq.rcqback.dto.comment;

import com.rcq.rcqback.util.ProblemConditionEnum;

import java.time.LocalDateTime;

public class getCommentDto {

    private Long id;
    private String userid;
    private String content;
    private Long recommendcount;
    private LocalDateTime created_at;
}
