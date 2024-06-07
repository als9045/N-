package org.example.board.Repository;

import org.example.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMember() {

        IntStream.rangeClosed(1,100).forEach(i -> {
        //1부터 100까지의 정수를 포함하는 IntStream을 생성합니다. rangeClosed() 메서드는 시작값과 종료값을 포함하는 범위를 생성
            Member member = Member.builder()
                    .email("user"+i + "@aaa.com")
                    .password("1111")
                    .name("User"+i)
                    .build();
            memberRepository.save(member);
        });
    }
}
