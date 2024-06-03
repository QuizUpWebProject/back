package com.rcq.rcqback.controller;

import com.rcq.rcqback.dto.groupstudy.*;
import com.rcq.rcqback.service.groupstudy.GroupStudyService;
import com.rcq.rcqback.util.ApiResponse;
import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.test.mode}")
    private boolean testMode;

    public GroupStudyController(GroupStudyService groupStudyService) {
        this.groupStudyService = groupStudyService;
    }

    @GetMapping("/groupstudy/api/getmygroup")
    public ResponseEntity<ApiResponse> getMyGroup(HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();
        List<getMyGroupDto> getMyGroupDtoList = new ArrayList<>();

        if (!testMode) {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                apiResponse.setFAILResponse("로그인이 필요합니다.");
                return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
            }
            getMyGroupDtoList = groupStudyService.getMyGroup(userId);
        } else {
            // 테스트 모드에서는 임의의 유저 ID 사용
            getMyGroupDtoList = groupStudyService.getMyGroup(1L); // 임의의 유저 ID
        }

        if (getMyGroupDtoList.size() > 0) {
            apiResponse.setResult(getMyGroupDtoList);
            apiResponse.setSuccessResponse();
        } else {
            apiResponse.setNOTFOUNDResponse("가입한 그룹이 없습니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/groupstudy/api/groupnamecheck")
    public ResponseEntity<ApiResponse> groupnameDuplicateCheck(@RequestParam String groupname) {
        ApiResponse apiResponse = new ApiResponse();
        if (!groupStudyService.groupnameCheck(groupname)) {
            apiResponse.setSuccessResponse();
        } else {
            apiResponse.setFAILResponse("중복이 존재합니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping("/groupstudy/api/makegroupstudy")
    public ResponseEntity<ApiResponse> makeGroupStudy(@RequestBody makeGroupStudyDto makeGroupStudydto, HttpServletRequest request) {
        ApiResponse apiResponse = new ApiResponse();

        if (!testMode) {
            HttpSession session = request.getSession();
            String usermail = (String) session.getAttribute("usermail");
            if (usermail == null) {
                apiResponse.setFAILResponse("로그인이 필요합니다.");
                return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
            }
        }

        try {
            groupStudyService.makeGroupStudy(makeGroupStudydto);
            apiResponse.setSuccessResponse();
        } catch (Exception e) {
            String message = "[그룹스터디 생성중 오류]";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message + e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/groupstudy/api/getlist")
    public ResponseEntity<ApiResponse> getGroupList(@RequestParam int pageNumber,
                                                    @RequestParam int pageSize,
                                                    @RequestParam(required = false) String category,
                                                    @RequestParam(required = false) ProblemListStandardEnum standardEnum) {

        ApiResponse apiResponse = new ApiResponse();
        checkGroupListDto checkGroupListDto = new checkGroupListDto();
        checkGroupListDto.setPageNumber(pageNumber);
        checkGroupListDto.setPageSize(pageSize);
        if (category == null) {
            checkGroupListDto.setCategory("ALL");
        } else {
            checkGroupListDto.setCategory(category);
        }
        if (standardEnum == null) {
            checkGroupListDto.setStandardEnum(ProblemListStandardEnum.LATEST);
        } else {
            checkGroupListDto.setStandardEnum(standardEnum);
        }
        List<getGroupListDto> groupListDtos = groupStudyService.getGroupList(checkGroupListDto);
        if (groupListDtos.size() > 0) {
            apiResponse.setResult(groupListDtos);
            apiResponse.setSuccessResponse();
        } else {
            apiResponse.setNOTFOUNDResponse("그룹스터디가 존재하지않습니다");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/groupstudy/api/searchgroupstudytitle")
    public ResponseEntity<ApiResponse> searchGroupStudyTitle(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam String word,
            @RequestParam ProblemListStandardEnum problemListStandardEnum) {
        searchGroupStudyDto searchGroupStudyDto = new searchGroupStudyDto();
        searchGroupStudyDto.setPageNumber(pageNumber);
        searchGroupStudyDto.setPageSize(pageSize);
        searchGroupStudyDto.setWord(word);
        searchGroupStudyDto.setProblemListStandardEnum(problemListStandardEnum);

        ApiResponse apiResponse = new ApiResponse();
        try {
            apiResponse.setResult(groupStudyService.searchGroupStudyTitle(searchGroupStudyDto));
            apiResponse.setSuccessResponse();
        } catch (Exception e) {
            String message = "GroupStudy 제목 검색 오류";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message + e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/groupstudy/api/searchgroupstudyuserid")
    public ResponseEntity<ApiResponse> searchGroupStudyUserId(@RequestParam int pageNumber,
                                                              @RequestParam int pageSize,
                                                              @RequestParam String word,
                                                              @RequestParam ProblemListStandardEnum problemListStandardEnum) {
        searchGroupStudyDto searchGroupStudyDto = new searchGroupStudyDto();
        searchGroupStudyDto.setPageNumber(pageNumber);
        searchGroupStudyDto.setPageSize(pageSize);
        searchGroupStudyDto.setWord(word);
        searchGroupStudyDto.setProblemListStandardEnum(problemListStandardEnum);

        ApiResponse apiResponse = new ApiResponse();
        try {
            apiResponse.setResult(groupStudyService.searchGroupStudyUserId(searchGroupStudyDto));
            apiResponse.setSuccessResponse();
        } catch (Exception e) {
            String message = "GroupStudy 작성자 검색 오류";
            apiResponse.setINTERNAL_SERVER_ERRORResponse(message + e.getMessage());
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
}
