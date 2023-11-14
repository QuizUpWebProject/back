package com.rcq.rcqback.dto.problem;


import com.rcq.rcqback.util.ProblemConditionEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class getProblemsDto {

    private Long id;
    private String question;
    private String answer;
    private int viewcount;
    private LocalDateTime created_at;
    private ProblemConditionEnum problemConditionEnum;
}
