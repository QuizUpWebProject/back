package com.rcq.rcqback.dto.problem;


import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import com.rcq.rcqback.util.StandardEnum.ProblemsStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchProblemListDto {

    private int pageNumber;
    private int pageSize;
    private String word;
    private ProblemListStandardEnum problemListStandardEnum;
}
