package org.example.board.service;

import org.example.board.BoardDTO.BoardDTO;
import org.example.board.BoardDTO.PageRequestDTO;
import org.example.board.BoardDTO.PageResultDTO;
import org.example.board.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void test() {

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test..")
                .writerEmail("als9045@namcasd,com").build();

        Long bno = boardService.register(dto);
    }

@Test
    public void testList() {

    PageRequestDTO pageRequestDTO = new PageRequestDTO();

    PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

    for (BoardDTO boardDTO : result.getDtoList()) {
        System.out.println(boardDTO);
    }

}
}
