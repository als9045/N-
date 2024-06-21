package org.example.board.Repository.search;

import org.example.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    Board search1();

    Page<Object[]> searchPage(String tyope, String keyword, Pageable pageable);
}
