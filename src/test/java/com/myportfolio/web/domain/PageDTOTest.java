package com.myportfolio.web.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageDTOTest {
    @Test
    public void test() {
        PageDTO ph = new PageDTO(new Criteria(22, 10), 220);
        ph.print();
    }
}