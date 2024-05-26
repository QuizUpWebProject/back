package com.rcq.rcqback.dto.groupstudy;

import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchGroupStudyDto {
    private int pageNumber;
    private int pageSize;
    private String word;
    private ProblemListStandardEnum problemListStandardEnum;
}
