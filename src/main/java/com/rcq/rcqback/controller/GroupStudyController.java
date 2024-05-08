package com.rcq.rcqback.controller;


import com.rcq.rcqback.dto.groupstudy.getMyGroupDto;
import com.rcq.rcqback.dto.groupstudy.makeGroupStudyDto;
import com.rcq.rcqback.dto.problem.getProblemDto;
import com.rcq.rcqback.service.groupstudy.GroupStudyService;
import com.rcq.rcqback.service.problem.ProblemService;
import com.rcq.rcqback.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://49.50.174.118:3000", "http://localhost:3000"})
@Controller
public class GroupStudyController {
    @Autowired
    private final GroupStudyService groupStudyService;
    public GroupStudyController(GroupStudyService groupStudyService) {
        this.groupStudyService= groupStudyService;
    }

    @GetMapping("/groupstudy/api/getmygroup")
    public ResponseEntity<ApiResponse> getMyGroup(
            HttpServletRequest request
    ) {

        ApiResponse apiResponse=new ApiResponse();
        HttpSession session = request.getSession();
        List<getMyGroupDto> getMyGroupDtoList=new ArrayList<>();

        getMyGroupDtoList=groupStudyService.getMyGroup((Long)session.getAttribute("userId"));

        if(getMyGroupDtoList.size()>0){
            apiResponse.setResult(getMyGroupDtoList);
            apiResponse.setSuccessResponse();
        }else{
            apiResponse.setNOTFOUNDResponse("가입한 그룹이 없습니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
    @GetMapping("/groupstudy/api/groupnamecheck")
    public ResponseEntity<ApiResponse> groupnameDuplicateCheck(@RequestParam String groupname){
        ApiResponse apiResponse=new ApiResponse();
        if(groupStudyService.groupnameCheck(groupname)){
            apiResponse.setSuccessResponse();
        }else{
            apiResponse.setFAILResponse("중복이 존재합니다.");

        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }




    @PostMapping("/groupstudy/api/makegroupstudy")
    public ResponseEntity<ApiResponse> makeGroupStudy(
            @RequestBody makeGroupStudyDto makeGroupStudydto,HttpServletRequest request
    ){

        ApiResponse apiResponse=new ApiResponse();
        HttpSession session = request.getSession();
        String usermail= (String) session.getAttribute("usermail");
        if(usermail == null) {
            apiResponse.setFAILResponse("로그인이 필요합니다.");
            return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
        }
        try{
            groupStudyService.makeGroupStudy(makeGroupStudydto);
            apiResponse.setSuccessResponse();
        }catch (Exception e){
            String message="[문제 생성중 오류]";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message+e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());

        }
    }


