package com.rcq.rcqback.service.groupstudy;


import com.rcq.rcqback.dto.groupstudy.makeGroupProblemDto;
import com.rcq.rcqback.dto.groupstudy.makeGroupStudyDto;
import com.rcq.rcqback.dto.groupstudy.*;
import com.rcq.rcqback.dto.problem.checkProblemListDto;
import com.rcq.rcqback.dto.problem.getProblemListDto;
import com.rcq.rcqback.entity.User;
import com.rcq.rcqback.entity.groupstudy.GroupProblem;
import com.rcq.rcqback.entity.groupstudy.GroupStudy;
import com.rcq.rcqback.entity.problem.ProblemList;
import com.rcq.rcqback.repository.GroupStudyRepository.GroupProblemRepository;
import com.rcq.rcqback.repository.GroupStudyRepository.GroupStudyRepository;
import com.rcq.rcqback.repository.UserRepository;
import com.rcq.rcqback.util.StandardEnum.ProblemListStandardEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GroupStudyService {

    private final GroupProblemRepository groupProblemRepository;
    private final GroupStudyRepository groupStudyRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    public boolean groupnameCheck(String nickname){
        return groupStudyRepository.existsByGroupname(nickname);
    }
    @Transactional
    public GroupStudy makeGroupStudy(makeGroupStudyDto makegroupstudydto) {
        // 새로운 문제 객체 생성
        GroupStudy groupStudy=new GroupStudy();
        groupStudy.setGroupname(makegroupstudydto.getGroupname());
        groupStudy.setCategory(makegroupstudydto.getCategory());
        groupStudy.setCapacityLimit(makegroupstudydto.getCapacitylimit());
        groupStudy.setAbout(makegroupstudydto.getAbout());
        if(makegroupstudydto.getIsOpen()==1){
            groupStudy.setAccessCode(makegroupstudydto.getAccesscode());
        }else{groupStudy.setAccessCode(0);}
        groupStudy.setIsOpen(makegroupstudydto.getIsOpen());
        groupStudy.setMasterid(makegroupstudydto.getMasterid());

        Optional<User> userOptional= userRepository.findById(makegroupstudydto.getMasterid());
        User user=userOptional.get();
        groupStudy.setMasterName(user.getNickname());
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

    @Transactional
    public List<getMyGroupDto> getMyGroup(Long userId){
        List<getMyGroupDto> getMyGroupDtoList=new ArrayList<>();
        ModelMapper modelMapper=new ModelMapper();
    // 유저 ID를 기반으로 사용자 정보 가져오기
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // 사용자가 속한 그룹스터디 목록 가져오기
            List<GroupStudy> groupStudies = new ArrayList<>(user.getGroupStudies());

            // 그룹스터디 DTO로 매핑하여 리스트에 추가
            for (GroupStudy groupStudy : groupStudies) {
                getMyGroupDto dto = new getMyGroupDto();
                dto.setId(groupStudy.getId());
                dto.setUserid(groupStudy.getMasterName());
                dto.setTitle(groupStudy.getGroupname());
                dto.setCategory(groupStudy.getCategory());
                dto.setIsOpen(groupStudy.getIsOpen());
                // 그룹스터디의 인원 및 오픈 여부 등의 정보 설정
                dto.setPeoples(groupStudy.getMembers().size());

                getMyGroupDtoList.add(dto);
            }
            return getMyGroupDtoList;
        }

        return getMyGroupDtoList;
    }
    @Transactional
    public List<getGroupListDto> getGroupList(checkGroupListDto checkGroupListDto){
        List<getGroupListDto> dtoList = new ArrayList<>();
        int pagenumber=checkGroupListDto.getPageNumber();
        int pageSize=checkGroupListDto.getPageSize();
        Sort sort=sortGroup(checkGroupListDto.getStandardEnum());
        Pageable pageable= PageRequest.of(pagenumber-1, pageSize, sort);
        Page<GroupStudy> groupStudyPage ;
        if ("ALL".equalsIgnoreCase(checkGroupListDto.getCategory())) {
            groupStudyPage = groupStudyRepository.findAll(pageable);
        } else {
            groupStudyPage = (Page<GroupStudy>) groupStudyRepository.findAllByCategory(checkGroupListDto.getCategory(), pageable);
        }
        List<GroupStudy> groupStudyList=groupStudyPage.getContent();
        for(GroupStudy groupStudy: groupStudyList){
            getGroupListDto dto=modelMapper.map(groupStudy,getGroupListDto.class);
            dto.setCurrent(groupStudy.getMembers().size());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Sort sortGroup(ProblemListStandardEnum standardEnum){
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

}
