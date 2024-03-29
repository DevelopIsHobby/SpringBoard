package com.myportfolio.web.mapper;

import com.myportfolio.web.domain.Criteria;
import com.myportfolio.web.domain.ReplyVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ReplyMapperTest {
    @Setter(onMethod_=@Autowired)
    private ReplyMapper mapper;

    private Long[] bnoArr = { 3145L, 3146L, 3147L, 3148L, 3149L};

    @Test
    public void testList2() {
        Criteria cri = new Criteria(1, 10);
        List<ReplyVO> replies = mapper.getListWithPaging(cri, 188424l);
        System.out.println("replies = " + replies);
    }

    @Test
    public void testMapper() {
        log.info(mapper);
    }

    @Test
    public void testCreate() {
        IntStream.rangeClosed(1,10).forEach(i -> {
            ReplyVO vo = new ReplyVO();

            vo.setBno(bnoArr[i%5]);
            vo.setReply("댓글 테스트" + i);
            vo.setReplyer("replyer" + i);

            mapper.insert(vo);
        });
    }

    @Test
    public void testRead() {
        Long targerRno = 5L;
        ReplyVO vo = mapper.read(targerRno);
        log.info(vo);
    }
    @Test
    public void testDelete() {
        Long targerRno = 1L;
        mapper.delete(targerRno);
    }
    @Test
    public void testUpdate() {
        Long targetRno = 2L;
        ReplyVO vo = mapper.read(targetRno);
        vo.setReply("Update Reply ");
        int count = mapper.update(vo);
        log.info("update count" + count);
    }

    @Test
    public void testList() {
        Criteria cri = new Criteria();
        List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
        replies.forEach(reply -> log.info(reply));
    }
}