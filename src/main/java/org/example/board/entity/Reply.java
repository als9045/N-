package org.example.board.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends  baseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long rno;

    private String text;

    private  String replyer;

    @ManyToOne
    private Board board; //연관관계 지정

}
