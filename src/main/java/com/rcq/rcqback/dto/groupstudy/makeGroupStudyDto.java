package com.rcq.rcqback.dto.groupstudy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class makeGroupStudyDto {
    private String groupname;
    private String category;
    private int capacitylimit;
    private int accesscode;
    private String about;
    private Long masterid;
    private int isOpen;

}
