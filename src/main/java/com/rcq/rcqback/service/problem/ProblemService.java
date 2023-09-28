package com.rcq.rcqback.service.problem;


import com.rcq.rcqback.dto.auth.signUpUserDto;
import com.rcq.rcqback.dto.problem.getProblemListDto;
import com.rcq.rcqback.dto.problem.makeProblemDto;
import com.rcq.rcqback.entity.User;
import com.rcq.rcqback.entity.problem.Problem;
import com.rcq.rcqback.entity.problem.ProblemList;
import com.rcq.rcqback.repository.problem.ProblemListRepository;
import com.rcq.rcqback.repository.problem.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemListRepository problemListRepository;
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Problem saveProblem(makeProblemDto makeProblemDto) {
        // 새로운 문제 객체 생성
        Problem problem=new Problem();
        problem.setQuestion(makeProblemDto.getQuestion());
        problem.setAnswer(makeProblemDto.getAnswer());
        problem.setProblemList(problemListRepository.findByid(makeProblemDto.getProblemlistid()));

        return problemRepository.save(problem);
    }

    public List<getProblemListDto> getProblemList(String category){
        List<getProblemListDto> dtoList = new ArrayList<>();
        List<ProblemList> problemLists = problemListRepository.findAllByCategory(category);
        for(ProblemList problemList: problemLists){
            getProblemListDto dto=modelMapper.map(problemList,getProblemListDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }



}
