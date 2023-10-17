package com.rcq.rcqback.entity.groupstudy;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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







}
