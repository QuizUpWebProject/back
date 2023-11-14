package com.rcq.rcqback.dto.problem;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class getProblemListDto{
    private Long id;
    private String userid;
    private String title;
    private String category;
    private Long recommendcount;
    private Long viewcount;
    private LocalDateTime created_at;
}

