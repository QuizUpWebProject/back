package com.rcq.rcqback.entity.groupstudy;


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

    @Column(name="about")
    private String about;

    @OneToMany
    @JoinColumn(name="problemlist")
    private List<Problem> problems = new ArrayList<>();

    @Column(name="masterid")
    private Long masterid;


}
