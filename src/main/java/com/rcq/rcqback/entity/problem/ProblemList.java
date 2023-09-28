package com.rcq.rcqback.entity.problem;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="problemlist")
public class ProblemList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "userid")
    private String userid;

    @Column(name = "title")
    private String title;

    @Column(name="category")
    private String category;

    @Column(name="recommendcount")
    private int recommendcount;

    @Column(name ="viewcount")
    private int viewcount;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @OneToMany
    @JoinColumn(name="problemlist")
    private List<Problem> problems = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "problemlist") // 다른 테이블과의 관계 매핑
    private List<Comment> comments=new ArrayList<>();


}
