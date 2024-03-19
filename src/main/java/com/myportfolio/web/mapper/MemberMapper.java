package com.myportfolio.web.mapper;

import com.myportfolio.web.domain.MemberVO;

public interface MemberMapper {
    public MemberVO read(String userid);
}
