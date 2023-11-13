package com.rcq.rcqback.repository.comment;

import com.rcq.rcqback.entity.comment.Comment;
import com.rcq.rcqback.entity.problem.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>  {
    Page<Comment> findByProblemListId(Long problemListId, Pageable pageable);
}
