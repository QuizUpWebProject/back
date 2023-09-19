package com.rcq.rcqback.entity.problem;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne
    @JoinColumn(name = "problemlist_id")
    private ProblemList problemlist;

}
