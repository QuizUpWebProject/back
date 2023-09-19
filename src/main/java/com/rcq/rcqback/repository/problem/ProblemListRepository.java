package com.rcq.rcqback.repository.problem;

import com.rcq.rcqback.entity.problem.ProblemList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemListRepository extends JpaRepository<ProblemList,Long> {

    ProblemList findByid(Long id);

}
