package com.rcq.rcqback.controller;


import com.rcq.rcqback.dto.comment.checkCommentDto;
import com.rcq.rcqback.dto.comment.getCommentDto;
import com.rcq.rcqback.dto.comment.makeCommentDto;
import com.rcq.rcqback.dto.condition.updateConditionDto;
import com.rcq.rcqback.dto.problem.*;
import com.rcq.rcqback.service.problem.ProblemService;
import com.rcq.rcqback.util.ApiResponse;
import com.rcq.rcqback.util.StandardEnum.CommentStandardEnum;
import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import com.rcq.rcqback.util.StandardEnum.ProblemsStandardEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@Controller
public class ProblemController {

    @Autowired
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService= problemService;
    }

    @GetMapping("/problem/api/getlist")
    public ResponseEntity<ApiResponse> getProblemList(@RequestParam int pageNumber,
                                                      @RequestParam int pageSize,
                                                      @RequestParam String category,
                                                      @RequestParam ProblemListStandardEnum standardEnum){
        ApiResponse apiResponse=new ApiResponse();
        checkProblemListDto checkProblemListDto = new checkProblemListDto();
        checkProblemListDto.setPageNumber(pageNumber);
        checkProblemListDto.setPageSize(pageSize);
        checkProblemListDto.setCategory(category);
        checkProblemListDto.setStandardEnum(standardEnum);
        List<getProblemListDto> problemListDtos=problemService.getProblemList(checkProblemListDto);
        if(problemListDtos.size()>0){
            apiResponse.setResult(problemListDtos);
            apiResponse.setSuccessResponse();
        }else{
            apiResponse.setNOTFOUNDResponse("해당 카테고리에 문제목록이 존재하지않습니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/problem/api/getproblems")
    public ResponseEntity<ApiResponse> getProblems(
            @RequestParam Long problemlist_id,
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam ProblemsStandardEnum problemsStandardEnum
    ) {
        checkProblemsDto checkProblemsDto = new checkProblemsDto();
        checkProblemsDto.setProblemlist_id(problemlist_id);
        checkProblemsDto.setPageNumber(pageNumber);
        checkProblemsDto.setPageSize(pageSize);
        checkProblemsDto.setProblemsStandardEnum(problemsStandardEnum);
        ApiResponse apiResponse=new ApiResponse();
        List<getProblemsDto> problemsDtoList=problemService.getProblems(checkProblemsDto);
        if(problemsDtoList.size()>0){
            apiResponse.setResult(problemsDtoList);
            apiResponse.setSuccessResponse();
        }else{
            apiResponse.setNOTFOUNDResponse("해당 문제집은 비어있습니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/problem/api/getcomments")
    public ResponseEntity<ApiResponse> getComments(@RequestParam Long problemlistid,
                                                   @RequestParam int pagenumber,
                                                   @RequestParam int pageSize,
                                                   @RequestParam CommentStandardEnum commentStandardEnum
    ) {
        checkCommentDto checkCommentDto = new checkCommentDto();
        checkCommentDto.setProblemlistid(problemlistid);
        checkCommentDto.setPagenumber(pagenumber);
        checkCommentDto.setPageSize(pageSize);
        checkCommentDto.setCommentStandardEnum(commentStandardEnum);
        ApiResponse apiResponse=new ApiResponse();
        List<getCommentDto> commentDtoList=problemService.getComments(checkCommentDto);
        if(commentDtoList.size()>0){
            apiResponse.setResult(commentDtoList);
            apiResponse.setSuccessResponse();
        }else{
            apiResponse.setNOTFOUNDResponse("댓글이 비어있습니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


    @PostMapping("/problem/api/makeproblem")
    public ResponseEntity<ApiResponse> makeProblem(@RequestBody makeProblemDto makeproblemdto){
        ApiResponse apiResponse=new ApiResponse();
        try{
        problemService.saveProblem(makeproblemdto);
        apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="[문제 생성중 오류]";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping("/problem/api/makeproblemlist")
    public ResponseEntity<ApiResponse> makeProblemList(@RequestBody makeProblemListDto makeProblemListDto){
        ApiResponse apiResponse=new ApiResponse();
        try{
            problemService.saveProblemList(makeProblemListDto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="[문제집 생성중 오류]";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping("/problem/api/makecomment")
    public ResponseEntity<ApiResponse> makeComment(@RequestBody makeCommentDto makeCommentDto){
        ApiResponse apiResponse=new ApiResponse();
        try{
            problemService.saveComment(makeCommentDto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="[댓글 작성 오류]";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
    @PostMapping("/problem/api/updatecondition")
    public ResponseEntity<ApiResponse> updateCondition(@RequestBody updateConditionDto updateConditionDto){
        ApiResponse apiResponse=new ApiResponse();
        try{
            problemService.updateCondition(updateConditionDto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="문제 상태 변경 오류";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/problem/api/searchproblemtitle")
    public ResponseEntity<ApiResponse> searchProblemTitle(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam Long problemlistid,
            @RequestParam String word,
            @RequestParam ProblemsStandardEnum problemsStandardEnum
    ) {
        searchProblemDto searchProblemdto = new searchProblemDto();
        searchProblemdto.setPageNumber(pageNumber);
        searchProblemdto.setPageSize(pageSize);
        searchProblemdto.setProblemlistid(problemlistid);
        searchProblemdto.setWord(word);
        searchProblemdto.setProblemsStandardEnum(problemsStandardEnum);
        ApiResponse apiResponse=new ApiResponse();
        try{
            problemService.searchProblemTitle(searchProblemdto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="문제 제목 검색 오류";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/problem/api/searchproblemlisttitle")
    public ResponseEntity<ApiResponse> searchProblemListTitle(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam String word,
            @RequestParam ProblemListStandardEnum problemListStandardEnum
    ) {
        searchProblemListDto searchProblemListDto = new searchProblemListDto();
        searchProblemListDto.setPageNumber(pageNumber);
        searchProblemListDto.setPageSize(pageSize);
        searchProblemListDto.setWord(word);
        searchProblemListDto.setProblemListStandardEnum(problemListStandardEnum);

        ApiResponse apiResponse=new ApiResponse();
        try{
            problemService.searchProblemListTitle(searchProblemListDto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="문제집 제목 검색 오류";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/problem/api/searchproblemlistuserid")
    public ResponseEntity<ApiResponse> searchProblemListUserId(@RequestParam int pageNumber,
    @RequestParam int pageSize,
    @RequestParam String word,
    @RequestParam ProblemListStandardEnum problemListStandardEnum
    ) {
        searchProblemListDto searchProblemListDto = new searchProblemListDto();
        searchProblemListDto.setPageNumber(pageNumber);
        searchProblemListDto.setPageSize(pageSize);
        searchProblemListDto.setWord(word);
        searchProblemListDto.setProblemListStandardEnum(problemListStandardEnum);
        ApiResponse apiResponse=new ApiResponse();
        try{
            problemService.searchProblemListUserId(searchProblemListDto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="문제집 작성자 검색 오류";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


}
