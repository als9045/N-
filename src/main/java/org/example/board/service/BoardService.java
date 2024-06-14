package org.example.board.service;

import org.example.board.BoardDTO.BoardDTO;
import org.example.board.BoardDTO.PageRequestDTO;
import org.example.board.BoardDTO.PageResultDTO;
import org.example.board.entity.Board;
import org.example.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO dto) {

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member).build();
        return board;

    }

    default BoardDTO endtityToDto(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno((board.getBno()))
                .title(board.getTitle())
                .content(board.getContent())
                .registerDate(board.getRegdate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerEmail(member.getEmail())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;

    }
}
