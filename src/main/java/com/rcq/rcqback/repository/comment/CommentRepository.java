package com.rcq.rcqback.repository.comment;

import com.rcq.rcqback.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>  {
}
