package org.example.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.board.BoardDTO.BoardDTO;
import org.example.board.Repository.BoardRepository;
import org.example.board.entity.Board;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImple implements BoardService{

    private final BoardRepository repository;

    @Override
    public Long register(BoardDTO dto) {
    log.info(dto);

    Board board = dtoToEntity(dto);

    repository.save(board);

    return board.getBno();
    }
}
