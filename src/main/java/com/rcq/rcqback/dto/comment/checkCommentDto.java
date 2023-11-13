package com.rcq.rcqback.dto.comment;

import com.rcq.rcqback.util.StandardEnum.CommentStandardEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class checkCommentDto {

    Long problemlistid;
    int pagenumber;
    int pageSize;
    CommentStandardEnum commentStandardEnum;
}
