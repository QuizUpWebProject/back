package com.rcq.rcqback.repository.problem;

import com.rcq.rcqback.entity.problem.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
    Page<Problem> findAllByProblemList_Id(Long problemList_Id, Pageable pageable);
    Page<Problem> findAllByProblemList_IdAndQuestionContaining(Long problemListId, String Question, Pageable pageable);


}
