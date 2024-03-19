package com.myportfolio.web.service;

import com.myportfolio.web.domain.BoardVO;
import com.myportfolio.web.domain.Criteria;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardServiceImplTest {

    @Setter(onMethod_=@Autowired)
    private BoardService service;

    @Test
    public void testExist() {
        log.info(service);
        assertNotNull(service);
    }

    @Test
    public void register() {
        BoardVO board = new BoardVO();
        board.setTitle("새로운 제목");
        board.setContent("새로운 내용");
        board.setWriter("newbie");

        service.register(board);
        log.info("생성된 게시물의 번호 : "+board.getBno());
    }

    @Test
    public void get() {
        log.info(service.get(26L));
    }

    @Test
    public void modify() {
        BoardVO board = service.get(26L);
        board.setTitle("수정된 제목입니다.");
        log.info("modify result : " + service.modify(board));
    }

    @Test
    public void remove() {
        log.info("remove result : " + service.remove(26L));
    }

    @Test
    public void getList() {

        service.getList(new Criteria(2, 10)).forEach(board->log.info(board));
    }
}