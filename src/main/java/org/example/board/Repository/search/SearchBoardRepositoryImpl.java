package org.example.board.Repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.example.board.entity.Board;
import org.example.board.entity.QBoard;
import org.example.board.entity.QMember;
import org.example.board.entity.QReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1..............");
        System.out.println("search1..............");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);

        log.info("jpqlQuesry=====" + jpqlQuery);

        List<Tuple> result = tuple.fetch();

        log.info("result=====" + result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

       log.info("searchPage...............");

       QBoard board = QBoard.board;
       QReply reply = QReply.reply;
       QMember member = QMember.member;

       JPQLQuery<Board> jpqlQuery = from(board);
       jpqlQuery.leftJoin(member).on(board.writer.eq(member));
       jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

       //select b, w, count(t) from board b
        //Left join b,writer w left join reply r on r.board = b
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null) {
            String[] typeArr = type.split(",");

            BooleanBuilder condition = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        condition.or(board.title.contains(keyword));
                        break;
                    case "w":
                        condition.or(member.email.contains(keyword));
                        break;
                    case "c":
                        condition.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(condition);
        }

        tuple.where(booleanBuilder);

        tuple.groupBy(board);

        this.getQuerydsl().applyPagination(pageable,tuple);

        List<Tuple> result = tuple.fetch();

        log.info(result);

        Long count = tuple.fetchCount();

        log.info("count : " + count);

        return new PageImpl<>(result.stream().map(Tuple::toArray).collect(Collectors.toList()), pageable, count);

                }
            }
