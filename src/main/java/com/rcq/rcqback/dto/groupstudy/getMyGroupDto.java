package com.rcq.rcqback.dto.groupstudy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class getMyGroupDto {
    private Long id;
    private String userid;
    private String title;
    private String category;
    private int peoples;
    private int isOpen;
}
