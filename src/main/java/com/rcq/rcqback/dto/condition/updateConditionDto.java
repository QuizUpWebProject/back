package com.rcq.rcqback.dto.condition;

import com.rcq.rcqback.util.ProblemConditionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updateConditionDto {
    private long problemid;
    private ProblemConditionEnum problemConditionEnum;
}
