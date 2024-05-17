package com.rcq.rcqback.dto.groupstudy;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class getGroupListDto {

        private Long id;
        private String userid;
        private String title;
        private String category;
        private Long recommendcount;
        private int isOpen;
}
