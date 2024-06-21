package org.example.board.Repository;

import org.example.board.entity.Board;
import org.example.board.entity.Member;
import org.example.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public  void insertBoard() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder().email("user"+i +"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title...."+i)
                    .content("content...."+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

@Transactional
@Test
    public void testRead1(){
    //Optional<Board>는 값이 존재할 수도 있고 존재하지 않을 수도 있는 값을 감싸는 컨테이너
    Optional<Board> result = boardRepository.findById(100L);

    Board board = result.get();

    System.out.println("보드 값~~~~~~"+board);
    System.out.println("이게 뭔 값이지?- --"+board.getWriter());
}
 @Test
    public  void testRead2(){
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("--------------"+ Arrays.toString(arr));
 }

@Test
    public void getBoard(){

    List<Object[]> result = boardRepository.getBoard(100L);

    for (Object[] arr : result) {
        System.out.println(Arrays.toString(arr));
        Board board = (Board) arr[0];
        Reply reply =(Reply) arr[1];

        System.out.println("타이틀~~~~~~~"+board.getTitle());
        System.out.println("택스트~~~~~~~~"+reply.getText());
    }
    }

    @Test
    public void getBoardWithReplyCOunt(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){

        Object result = boardRepository.getBoardBybno(100l);

        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){

        boardRepository.search1();
    }
}


