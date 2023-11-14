package com.rcq.rcqback.repository.problem;

import com.rcq.rcqback.entity.problem.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
    Page<Problem> findByProblemListId(Long problemListId, Pageable pageable);
    Page<Problem> findByProblemListIdAndTitleContaining(Long problemListId, String title, Pageable pageable);
    Page<Problem> findByProblemListIdAndUserIdContaining(Long problemListId,String userid,Pageable pageable);
}
