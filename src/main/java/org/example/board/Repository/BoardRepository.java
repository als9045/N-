package org.example.board.Repository;

import org.example.board.Repository.search.SearchBoardRepository;
import org.example.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , SearchBoardRepository {
    @Query("select b, w from Board b left  join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);
    @Query("SELECT b, r from Board b left join Reply r on r.board = b where b.bno =:bno")
    List<Object[]> getBoard(@Param("bno") Long bno);

    @Query( value = "select  b, w, count(r) " +
             " FROM Board b " +
             " LEFT JOIN b.writer w " +
             " LEFT JOIN Reply r on r.board =b " +
             "group by b",
             countQuery = "select count(b) FROM Board b ")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b, w, count(r) " +
            "from Board b left join b.writer w " +
            "left outer join Reply r on r.board =b "+
            "WHERE b.bno = :bno")
    Object getBoardBybno(@Param("bno") Long bno);

}
