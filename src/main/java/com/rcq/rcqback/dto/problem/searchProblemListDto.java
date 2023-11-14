package com.rcq.rcqback.dto.problem;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchProblemListDto {

    private int pagenumber;
    private int pageSize;
    private String word;
}
