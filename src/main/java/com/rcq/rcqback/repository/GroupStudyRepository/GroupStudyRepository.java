package com.rcq.rcqback.repository.GroupStudyRepository;

import com.rcq.rcqback.entity.groupstudy.GroupStudy;
import com.rcq.rcqback.entity.problem.ProblemList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupStudyRepository extends JpaRepository<GroupStudy,Long> {
    List<GroupStudy> findAllByCategory(String category);
    GroupStudy findByid(Long Id);
    boolean existsByGroupname(String nickname);

}
