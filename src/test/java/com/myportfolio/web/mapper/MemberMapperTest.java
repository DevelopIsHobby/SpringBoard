package com.myportfolio.web.mapper;

import com.myportfolio.web.domain.MemberVO;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberMapperTest {
    @Setter(onMethod_=@Autowired)
    private MemberMapper mapper;

    @Test
    public void testRead() {
        MemberVO vo = mapper.read("admin90");

        log.info(vo);

        vo.getAuthList().forEach(authVO -> {
            log.info(authVO);
        });
    }
}