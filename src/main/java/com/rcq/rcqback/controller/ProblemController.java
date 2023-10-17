package com.rcq.rcqback.controller;


import com.rcq.rcqback.dto.problem.checkProblemListDto;
import com.rcq.rcqback.dto.problem.getProblemListDto;
import com.rcq.rcqback.dto.problem.makeProblemDto;
import com.rcq.rcqback.service.problem.ProblemService;
import com.rcq.rcqback.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProblemController {

    @Autowired
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService= problemService;
    }

    @GetMapping("/problem/api/getlist")
    public ResponseEntity<ApiResponse> getProblemList(@RequestBody checkProblemListDto checkProblemListDto){
        ApiResponse apiResponse=new ApiResponse();
        List<getProblemListDto> problemListDtos=problemService.getProblemList(checkProblemListDto);
        if(problemListDtos.size()>0){
            apiResponse.setResult(problemListDtos);
            apiResponse.setSuccessResponse();
        }else{
            apiResponse.setNOTFOUNDResponse("해당 카테고리에 문제목록이 존재하지않습니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping("/problem/api/makeproblem")
    public ResponseEntity<ApiResponse> makeProblem(makeProblemDto makeproblemdto){
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
    public ResponseEntity<ApiResponse> makeProblemList(makeProblemDto makeproblemdto){
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


}
