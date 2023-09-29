package com.rcq.rcqback.dto.problem;


import com.rcq.rcqback.util.ProblemListStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkProblemListDto {
    String category;
    ProblemListStandardEnum standardEnum;
}
