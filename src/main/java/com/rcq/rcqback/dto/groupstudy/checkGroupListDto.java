package com.rcq.rcqback.dto.groupstudy;


import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkGroupListDto {



        int pageNumber;
        int pageSize;
        String category;
        ProblemListStandardEnum standardEnum;

}
