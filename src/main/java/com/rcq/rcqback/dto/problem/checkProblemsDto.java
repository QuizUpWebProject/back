package com.rcq.rcqback.dto.problem;


import com.rcq.rcqback.util.StandardEnum.ProblemsStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkProblemsDto {
    Long problemlistid;
    int pagenumber;
    int pageSize;
    ProblemsStandardEnum problemsStandardEnum;
}
