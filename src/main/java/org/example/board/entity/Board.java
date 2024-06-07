package org.example.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //@ToString은 항상 exclude
public class Board extends baseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //Auto Increment 같은 동작
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
}
