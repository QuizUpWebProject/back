package com.rcq.rcqback.repository.problem;

import com.rcq.rcqback.entity.problem.ProblemList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProblemListRepository extends JpaRepository<ProblemList,Long> {

    ProblemList findByid(Long id);
    List<ProblemList> findAllByCategory(String category);

}
