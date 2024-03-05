package com.rcq.rcqback.entity.comment;

import com.rcq.rcqback.entity.problem.ProblemList;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;
    @Column(name = "recommendcount")
    private Long recommendcount;

    @Column(name="usernickname")
    private String usernickname;

    @Column(name="created_at")
    private LocalDateTime created_at;


    @ManyToOne
    @JoinColumn(name = "problemlist_id")
    private ProblemList problemlist;

}
