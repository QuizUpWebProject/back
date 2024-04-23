package com.rcq.rcqback.entity.groupstudy;


import com.rcq.rcqback.entity.User;
import com.rcq.rcqback.entity.problem.Problem;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="groupstudy")
public class GroupStudy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupname")
    private String groupname;

    @Column(name="category")
    private String category;

    @Column(name="capacitylimit")
    private int capacityLimit;

    @Column(name="accesscode")
    private int accessCode;

    @Column(name="isopen")
    private int isOpen;

    @Column(name="about")
    private String about;

    @OneToMany
    @JoinColumn(name="problemlist")
    private List<Problem> problems = new ArrayList<>();

    @Column(name="masterid")
    private Long masterid;
    @Column(name="notice")
    private String notice;
    @Column(name="mastername")
    private String masterName;

    @ManyToMany
    @JoinTable(
            name = "user_groupstudy",
            joinColumns = @JoinColumn(name = "groupstudy_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "groupStudy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupProblem> groupProblems = new ArrayList<>();

}
