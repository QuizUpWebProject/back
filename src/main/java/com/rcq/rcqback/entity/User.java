package com.rcq.rcqback.entity;

import com.rcq.rcqback.entity.groupstudy.GroupStudy;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String usermail;
    @Column(unique = true)
    private String nickname;

    private String password;
    @ManyToMany(mappedBy = "members")
    private Set<GroupStudy> joinedGroups = new HashSet<>();
}
