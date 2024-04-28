package com.rcq.rcqback.service.problem;


import com.rcq.rcqback.dto.comment.checkCommentDto;
import com.rcq.rcqback.dto.comment.getCommentDto;
import com.rcq.rcqback.dto.comment.makeCommentDto;
import com.rcq.rcqback.dto.condition.updateConditionDto;
import com.rcq.rcqback.dto.problem.*;
import com.rcq.rcqback.entity.comment.Comment;
import com.rcq.rcqback.entity.problem.Problem;
import com.rcq.rcqback.entity.problem.ProblemList;
import com.rcq.rcqback.repository.comment.CommentRepository;
import com.rcq.rcqback.repository.problem.ProblemListRepository;
import com.rcq.rcqback.repository.problem.ProblemRepository;
import com.rcq.rcqback.util.ProblemConditionEnum;
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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
        problem.setCreated_at(LocalDateTime.now());
        problem.setProblemConditionEnum(ProblemConditionEnum.NO);
            return problemRepository.save(problem);
    }

    @Transactional
    public ProblemList saveProblemList(makeProblemListDto makeProblemListDto) {
        // 새로운 문제 객체 생성
        ProblemList problemList=new ProblemList();
        problemList.setCategory(makeProblemListDto.getCategory());
        problemList.setTitle(makeProblemListDto.getTitle());
        problemList.setUserid(makeProblemListDto.getUserid());
        problemList.setUsernickname(makeProblemListDto.getUsernickname());
        problemList.setCreated_at(LocalDateTime.now());
        return problemListRepository.save(problemList);

    }

    @Transactional
    public List<getProblemListDto> getProblemList(checkProblemListDto checkProblemListDto){
        List<getProblemListDto> dtoList = new ArrayList<>();
        int pagenumber=checkProblemListDto.getPageNumber();
        int pageSize=checkProblemListDto.getPageSize();
        Sort sort=sortProblemList(checkProblemListDto.getStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber-1, pageSize, sort);
        Page<ProblemList> problemListPage = problemListRepository.findAllByCategory(checkProblemListDto.getCategory(),pageable);
        List<ProblemList> problemLists=problemListPage.getContent();
        System.out.println(problemLists.size());
        for(ProblemList problemList: problemLists){
            getProblemListDto dto=modelMapper.map(problemList,getProblemListDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public List<getProblemsDto> getProblems(checkProblemsDto checkProblemsDto){
        List<getProblemsDto> dtoList = new ArrayList<>();
        ProblemList problemList=problemListRepository.findByid(checkProblemsDto.getProblemlist_id());
        problemList.setViewcount(problemList.getViewcount()+1);
        problemListRepository.save(problemList);
        int pagenumber=checkProblemsDto.getPageNumber();
        int pageSize=checkProblemsDto.getPageSize();
        Sort sort=sortProblems(checkProblemsDto.getProblemsStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber-1, pageSize, sort);
        Page<Problem> problemPage = problemRepository.findAllByProblemList_Id(
                checkProblemsDto.getProblemlist_id(),
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
    public getProblemDto getProblem(Long problemid){
        Optional<Problem> problemOptional= problemRepository.findById(problemid);
        if (problemOptional.isPresent()) {
            Problem problem = problemOptional.get();
            problem.setViewcount(problem.getViewcount()+1);
            problemRepository.save(problem);
            getProblemDto getProblemdto= new getProblemDto();
            getProblemdto.setQuestion(problem.getQuestion());
            getProblemdto.setAnswer(problem.getAnswer());
            return getProblemdto;
        } else {
            throw new EntityNotFoundException("문제가 존재하지않습니다.");
        }

    }

    @Transactional
    public void recommend(Long problemListId) throws Exception {
        try {
            ProblemList problemList = problemListRepository.findById(problemListId)
                    .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 문제 리스트를 찾을 수 없습니다."));

            problemList.setRecommendcount(problemList.getRecommendcount() + 1);
            problemListRepository.save(problemList);
        } catch (EntityNotFoundException e) {
            // 예외 처리 - 해당 ID에 해당하는 문제 리스트가 없을 경우
            throw e;
        } catch (Exception e) {
            // 기타 예외 처리 - 롤백을 위해 RuntimeException으로 변환
            throw new RuntimeException("문제 리스트 추천 중 오류가 발생했습니다.", e);
        }
    }
    @Transactional
    public List<getCommentDto> getComments(checkCommentDto checkCommentDto){
        List<getCommentDto> dtoList = new ArrayList<>();
        int pagenumber=checkCommentDto.getPagenumber();
        int pageSize=checkCommentDto.getPageSize();
        Sort sort=sortComment(checkCommentDto.getCommentStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
        Page<Comment> commentPage = commentRepository.findByProblemlistId(
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
        comment.setUsernickname(makeCommentDto.getUsernickname());
        comment.setCreated_at(LocalDateTime.now());
        return  commentRepository.save(comment);


    }
    public void updateCondition(updateConditionDto updateConditionDto){
        Optional<Problem> problemOptional=problemRepository.findById(updateConditionDto.getProblemid());
        if (problemOptional.isPresent()) {
            Problem problem = problemOptional.get();
            problem.setProblemConditionEnum(updateConditionDto.getProblemConditionEnum());
            problemRepository.save(problem);
        } else {
            throw new EntityNotFoundException("Problem with ID " + updateConditionDto.getProblemid() + " not found.");
        }
        }

        public List<getProblemsDto> searchProblemTitle(searchProblemDto searchProblemDto){

            List<getProblemsDto> dtoList = new ArrayList<>();
            int pagenumber= searchProblemDto.getPageNumber();
            int pageSize=searchProblemDto.getPageSize();
            Sort sort=sortProblems(searchProblemDto.getProblemsStandardEnum());
            Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
            Page<Problem> problemPage = problemRepository.findAllByProblemList_IdAndQuestionContaining(
                    searchProblemDto.getProblemlistid(), searchProblemDto.getWord(),
                    pageable
            );

            List<Problem> problems=problemPage.getContent();
            for(Problem problem :problems){
                getProblemsDto dto=modelMapper.map(problem,getProblemsDto.class);
                dtoList.add(dto);
            }
            return dtoList;
        }

    public List<getProblemListDto> searchProblemListTitle(searchProblemListDto searchProblemListDto){

        List<getProblemListDto> dtoList = new ArrayList<>();
        int pagenumber= searchProblemListDto.getPageNumber();
        int pageSize=searchProblemListDto.getPageSize();
        Sort sort=sortProblemList(searchProblemListDto.getProblemListStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
        Page<ProblemList> problemListPage = problemListRepository.findAllByTitleContaining(
                searchProblemListDto.getWord(),
                pageable
        );

        List<ProblemList> problemLists=problemListPage.getContent();
        for(ProblemList problemList :problemLists){
            getProblemListDto dto=modelMapper.map(problemList,getProblemListDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<getProblemListDto> searchProblemListUserId(searchProblemListDto searchProblemListDto){

        List<getProblemListDto> dtoList = new ArrayList<>();
        int pagenumber= searchProblemListDto.getPageNumber();
        int pageSize=searchProblemListDto.getPageSize();
        Sort sort=sortProblemList(searchProblemListDto.getProblemListStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber, pageSize, sort);
        Page<ProblemList> problemListPage = problemListRepository.findAllByTitleContaining(
                searchProblemListDto.getWord(),
                pageable
        );

        List<ProblemList> problemLists=problemListPage.getContent();
        for(ProblemList problemList :problemLists){
            getProblemListDto dto=modelMapper.map(problemList,getProblemListDto.class);
            dtoList.add(dto);
        }
        return dtoList;
    }


    }




