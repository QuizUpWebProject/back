package com.rcq.rcqback.service.groupstudy;


import com.rcq.rcqback.dto.groupstudy.makeGroupProblemDto;
import com.rcq.rcqback.dto.groupstudy.makeGroupStudyDto;
import com.rcq.rcqback.entity.groupstudy.GroupProblem;
import com.rcq.rcqback.entity.groupstudy.GroupStudy;
import com.rcq.rcqback.repository.GroupStudyRepository.GroupProblemRepository;
import com.rcq.rcqback.repository.GroupStudyRepository.GroupStudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class GroupStudyService {

    private final GroupProblemRepository groupProblemRepository;
    private final GroupStudyRepository groupStudyRepository;

    @Transactional
    public GroupStudy makeGroupStudy(makeGroupStudyDto makegroupstudydto) {
        // 새로운 문제 객체 생성
        GroupStudy groupStudy=new GroupStudy();
        groupStudy.setGroupname(makegroupstudydto.getGroupname());
        groupStudy.setCategory(makegroupstudydto.getCategory());
        groupStudy.setCapacityLimit(makegroupstudydto.getCapacitylimit());
        groupStudy.setAbout(makegroupstudydto.getAbout());
        groupStudy.setAccessCode(makegroupstudydto.getAccesscode());

        return groupStudyRepository.save(groupStudy);
    }

    @Transactional
    public GroupProblem makeGroupProblem(makeGroupProblemDto makegroupproblemdto) {
        // 새로운 문제 객체 생성
        GroupProblem groupProblem=new GroupProblem();
        groupProblem.setQuestion(makegroupproblemdto.getQuestion());
        groupProblem.setAnswer(makegroupproblemdto.getAnswer());
        groupProblem.setGroupStudy(groupStudyRepository.findByid(makegroupproblemdto.getGroupstudyid()));


        return groupProblemRepository.save(groupProblem);
    }


}
