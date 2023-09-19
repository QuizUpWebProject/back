package com.rcq.rcqback.repository.problem;

import com.rcq.rcqback.entity.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
}
