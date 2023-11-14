package com.rcq.rcqback.dto.problem;


import com.rcq.rcqback.util.StandardEnum.ProblemsStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkProblemsDto {
    Long problemlist_id;
    int pageNumber;
    int pageSize;
    ProblemsStandardEnum problemsStandardEnum;
}
