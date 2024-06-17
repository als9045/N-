package org.example.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.board.BoardDTO.BoardDTO;
import org.example.board.BoardDTO.PageRequestDTO;
import org.example.board.BoardDTO.PageResultDTO;
import org.example.board.Repository.BoardRepository;
import org.example.board.Repository.ReplyRepository;
import org.example.board.entity.Board;
import org.example.board.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImple implements BoardService{

    @Override
    public void modify(BoardDTO dto) {
        Board board = repository.getReferenceById(dto.getBno());

        if(board != null){
            board.changeTitle(dto.getTitle());
            board.changeContent(dto.getContent());

            repository.save(board);
        }
    }

    private final BoardRepository repository;

    private  final ReplyRepository replyRepository;

    @Override
    public BoardDTO get(Long bno) {

        Object result = repository.getBoardBybno(bno);

        Object[] arr = (Object[]) result;

        return endtityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {;
        replyRepository.deletebyBno(bno);

        repository.deleteById(bno);
    }

    @Override
    public Long register(BoardDTO dto) {
    log.info(dto);

    Board board = dtoToEntity(dto);

    repository.save(board);

    return board.getBno();
    }


    //entityToDTO를 이용해서 PageResultDTO 객체를 구성
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> endtityToDto((Board)
                en[0],(Member)en[1],(Long)en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending())
                );
        return new PageResultDTO<>(result, fn);

    }



}


