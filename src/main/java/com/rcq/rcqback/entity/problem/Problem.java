package com.rcq.rcqback.entity.problem;

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



    @ManyToOne
    @JoinColumn(name = "problemlist_id") // Problem 엔티티의 problemList와 매핑되는 컬럼
    private ProblemList problemList;

}
