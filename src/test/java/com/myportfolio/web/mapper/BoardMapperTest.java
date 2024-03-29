package com.myportfolio.web.mapper;

import com.myportfolio.web.domain.BoardVO;
import com.myportfolio.web.domain.Criteria;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardMapperTest {

    @Setter(onMethod_=@Autowired)
    private BoardMapper mapper;

    @Test
    public void testCount() {
        Criteria cri=  new Criteria();
        cri.setKeyword("");
        cri.setType("");
        log.info("count : " + mapper.getTotalCount(cri));
    }

    @Test
    public void testSearch() {
        Criteria cri=  new Criteria();
        cri.setKeyword("새로");
        cri.setType("TC");
        List<BoardVO> list = mapper.getListWithPageing(cri);
        list.forEach(board -> log.info(board));
        log.info("count" + mapper.getTotalCount(cri));
    }

    @Test
    public void testPaging() {
        Criteria cri = new Criteria();
        cri.setPageNum(3);
        cri.setAmount(10);
        List<BoardVO> list = mapper.getListWithPageing(cri);
        list.forEach(board -> log.info(board));
    }

    @Test
    public void getList() {
        mapper.getList().forEach(board -> log.info(board));
    }

    @Test
    public void testInsert() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");
        mapper.insert(board);
        log.info(board);
    }

    @Test
    public void testInsertSelectKey() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");
        mapper.insertSelectKey(board);
        log.info(board);
    }

    @Test
    public void testRead() {
        BoardVO board = mapper.read(3L);
        log.info(board);
    }

    @Test
    public void testDelete() {
        log.info("Delete count : " + mapper.delete(22L));
    }

    @Test
    public void updateTest() {
        BoardVO board = new BoardVO();
        board.setBno(5L);
        board.setTitle("수정된 제목");
        board.setContent("수정된 내용");
        board.setWriter("user00");
        int count = mapper.update(board);
        log.info("Update Count : " + count);
    }
}