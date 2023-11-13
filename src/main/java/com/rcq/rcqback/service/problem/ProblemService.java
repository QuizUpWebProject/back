package com.rcq.rcqback.service.problem;


import com.rcq.rcqback.dto.comment.checkCommentDto;
import com.rcq.rcqback.dto.comment.getCommentDto;
import com.rcq.rcqback.dto.comment.makeCommentDto;
import com.rcq.rcqback.dto.problem.*;
import com.rcq.rcqback.entity.comment.Comment;
import com.rcq.rcqback.entity.problem.Problem;
import com.rcq.rcqback.entity.problem.ProblemList;
import com.rcq.rcqback.repository.comment.CommentRepository;
import com.rcq.rcqback.repository.problem.ProblemListRepository;
import com.rcq.rcqback.repository.problem.ProblemRepository;
import com.rcq.rcqback.util.StandardEnum.CommentStandardEnum;
import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import com.rcq.rcqback.util.StandardEnum.ProblemsStandardEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private final ProblemListRepository problemListRepository;
    private final ProblemRepository problemRepository;
    private final CommentRepository commentRepository;
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
    public ProblemList saveProblemList(makeProblemListDto makeProblemListDto) {
        // 새로운 문제 객체 생성
        ProblemList problemList=new ProblemList();
        problemList.setCategory(makeProblemListDto.getCategory());
        problemList.setTitle(makeProblemListDto.getTitle());
        problemList.setUserid(makeProblemListDto.getUserid());

        return problemListRepository.save(problemList);

    }

    @Transactional
    public List<getProblemListDto> getProblemList(checkProblemListDto checkProblemListDto){
        List<getProblemListDto> dtoList = new ArrayList<>();
        int pagenumber=checkProblemListDto.getPagenumber();
        int pageSize=checkProblemListDto.getPageSize();
        Sort sort=sortProblemList(checkProblemListDto.getStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
        Page<ProblemList> problemListPage = problemListRepository.findAllByCategory(checkProblemListDto.getCategory(),pageable);
        List<ProblemList> problemLists=problemListPage.getContent();
        for(ProblemList problemList: problemLists){
            getProblemListDto dto=modelMapper.map(problemList,getProblemListDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public List<getProblemsDto> getProblems(checkProblemsDto checkProblemsDto){
        List<getProblemsDto> dtoList = new ArrayList<>();
        int pagenumber=checkProblemsDto.getPagenumber();
        int pageSize=checkProblemsDto.getPageSize();
        Sort sort=sortProblems(checkProblemsDto.getProblemsStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
        Page<Problem> problemPage = problemRepository.findByProblemListId(
                checkProblemsDto.getProblemlistid(),
                pageable
        );
        List<Problem> problems=problemPage.getContent();
        for(Problem problem :problems){
            getProblemsDto dto=modelMapper.map(problem,getProblemsDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public List<getCommentDto> getComments(checkCommentDto checkCommentDto){
        List<getCommentDto> dtoList = new ArrayList<>();
        int pagenumber=checkCommentDto.getPagenumber();
        int pageSize=checkCommentDto.getPageSize();
        Sort sort=sortComment(checkCommentDto.getCommentStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
        Page<Comment> commentPage = commentRepository.findByProblemListId(
                checkCommentDto.getProblemlistid(),
                pageable
        );
        List<Comment> comments=commentPage.getContent();
        for(Comment comment :comments){
            getCommentDto dto=modelMapper.map(comment,getCommentDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Sort sortProblemList(ProblemListStandardEnum standardEnum){
        Sort sort = Sort.unsorted();
        switch (standardEnum) {
            case LATEST:
                sort = Sort.by("id").descending();
                break;
            case OLDEST:
                sort = Sort.by("id").ascending();
                break;
            case RECOMMEND:
                sort = Sort.by("recommendcount").descending();
                break;
            case VIEW:
                sort = Sort.by("viewcount").descending();
                break;
        }
        return sort;
    }
    public Sort sortProblems(ProblemsStandardEnum standardEnum){
        Sort sort = Sort.unsorted();
        switch (standardEnum) {
            case LATEST:
                sort = Sort.by("id").descending();
                break;
            case OLDEST:
                sort = Sort.by("id").ascending();
                break;
            case VIEW:
                sort = Sort.by("viewcount").descending();
                break;
        }
        return sort;
    }
    public Sort sortComment(CommentStandardEnum standardEnum){
        Sort sort = Sort.unsorted();
        switch (standardEnum) {
            case LATEST:
                sort = Sort.by("id").descending();
                break;
            case OLDEST:
                sort = Sort.by("id").ascending();
                break;
            case RECOMMEND:
                sort = Sort.by("recommendcount").descending();
                break;
        }
        return sort;
    }

    public Comment saveComment(makeCommentDto makeCommentDto){

        Comment comment=new Comment();
        comment.setContent(makeCommentDto.getContent());
        comment.setProblemlist(problemListRepository.findByid(makeCommentDto.getProblemlistid()));
        return  commentRepository.save(comment);


    }



}
