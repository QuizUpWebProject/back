package com.rcq.rcqback.service.problem;


import com.rcq.rcqback.dto.auth.signUpUserDto;
import com.rcq.rcqback.dto.problem.checkProblemListDto;
import com.rcq.rcqback.dto.problem.getProblemListDto;
import com.rcq.rcqback.dto.problem.makeProblemDto;
import com.rcq.rcqback.entity.User;
import com.rcq.rcqback.entity.problem.Problem;
import com.rcq.rcqback.entity.problem.ProblemList;
import com.rcq.rcqback.repository.problem.ProblemListRepository;
import com.rcq.rcqback.repository.problem.ProblemRepository;
import com.rcq.rcqback.util.ProblemListStandardEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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

    @Transactional
    public List<getProblemListDto> getProblemList(checkProblemListDto checkProblemListDto){
        List<getProblemListDto> dtoList = new ArrayList<>();
        List<ProblemList> problemLists = problemListRepository.findAllByCategory(checkProblemListDto.getCategory());
        problemLists=sortProblemList(problemLists,checkProblemListDto.getStandardEnum());
        for(ProblemList problemList: problemLists){
            getProblemListDto dto=modelMapper.map(problemList,getProblemListDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<ProblemList> sortProblemList(List<ProblemList> problemLists, ProblemListStandardEnum standardEnum){
        Comparator<ProblemList> comparator = null;
        if(standardEnum==ProblemListStandardEnum.LATEST){
            comparator=Comparator.comparing(ProblemList::getId).reversed();
        }
        if(standardEnum==ProblemListStandardEnum.OLDEST){
            comparator=Comparator.comparing(ProblemList::getId);
        }
        if(standardEnum==ProblemListStandardEnum.RECOMMEND){
            comparator=Comparator.comparing(ProblemList::getRecommendcount).reversed();
        }
        if(standardEnum==ProblemListStandardEnum.VIEW){
            comparator=Comparator.comparing(ProblemList::getViewcount).reversed();
        }
        problemLists.sort(comparator);
        return problemLists;
    }



}
