package com.rcq.rcqback.dto.groupstudy;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class getGroupListDto {

        private Long id;
        private String masterName;
        private String groupname;
        private String category;
        private int capacityLimit;
        private int current;
        private int isOpen;
}
