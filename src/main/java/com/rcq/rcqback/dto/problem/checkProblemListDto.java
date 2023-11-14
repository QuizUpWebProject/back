package com.rcq.rcqback.dto.problem;


import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkProblemListDto {
    int pageNumber;
    int pageSize;
    String category;
    ProblemListStandardEnum standardEnum;
}
