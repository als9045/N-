package org.example.board.Repository.search;

import lombok.extern.log4j.Log4j2;
import org.example.board.entity.Board;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1..............");
        return null;
    }
}
