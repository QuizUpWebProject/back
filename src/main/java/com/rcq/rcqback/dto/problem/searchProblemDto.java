package com.rcq.rcqback.dto.problem;

import com.rcq.rcqback.util.StandardEnum.ProblemsStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchProblemDto {

    private int pageNumber;
    private int pageSize;
    private Long problemlistid;
    private String word;
    private ProblemsStandardEnum problemsStandardEnum;
}
