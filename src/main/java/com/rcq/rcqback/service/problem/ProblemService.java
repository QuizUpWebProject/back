package com.rcq.rcqback.service.problem;


import com.rcq.rcqback.dto.auth.signUpUserDto;
import com.rcq.rcqback.dto.problem.makeProblemDto;
import com.rcq.rcqback.entity.User;
import com.rcq.rcqback.entity.problem.Problem;
import com.rcq.rcqback.repository.problem.ProblemListRepository;
import com.rcq.rcqback.repository.problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemListRepository problemListRepository;
    private final ProblemRepository problemRepository;
    @Transactional
    public Problem saveProblem(makeProblemDto makeProblemDto) {
        // 새로운 사용자 객체 생성
        Problem problem=new Problem();
        problem.setQuestion(makeProblemDto.getQuestion());
        problem.setAnswer(makeProblemDto.getAnswer());
        problem.setProblemList(problemListRepository.findByid(makeProblemDto.getProblemlistid()));

        return problemRepository.save(problem);
    }

}
