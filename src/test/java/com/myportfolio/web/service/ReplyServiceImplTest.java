package com.myportfolio.web.service;

import com.myportfolio.web.domain.Criteria;
import com.myportfolio.web.mapper.ReplyMapper;
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
public class ReplyServiceImplTest {
    @Setter(onMethod_=@Autowired)
    private ReplyService service;

    @Test
    public void test() {
        Criteria cri = new Criteria(1, 10);

        System.out.println("result : " + service.getListPage(cri, 188424l));
    }
}