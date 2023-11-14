package com.rcq.rcqback.entity.problem;

import com.rcq.rcqback.util.ProblemConditionEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="problem")
public class Problem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;

    @Column(name="viewcount")
    private int viewcount;

    @Column(name = "problem_condition")
    @Enumerated(EnumType.STRING)
    private ProblemConditionEnum problemConditionEnum;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "problemlist_id") // Problem 엔티티의 problemList와 매핑되는 컬럼
    private ProblemList problemList;

}
