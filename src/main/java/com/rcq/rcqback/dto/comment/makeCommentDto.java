package com.rcq.rcqback.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class makeCommentDto {

    private String content;
    private String usernickname;
    private Long problemlistid;
}
