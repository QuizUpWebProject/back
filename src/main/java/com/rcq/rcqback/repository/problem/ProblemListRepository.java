package com.rcq.rcqback.repository.problem;

import com.rcq.rcqback.entity.problem.Problem;
import com.rcq.rcqback.entity.problem.ProblemList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProblemListRepository extends JpaRepository<ProblemList,Long> {
    Page<ProblemList> findAllByCategory(String category, Pageable pageable);
    ProblemList findByid(Long id);
    Page<Problem> findByProblemListID(Long ProblemListId,Pageable pageable);
    List<ProblemList> findAllByCategory(String category);

}
