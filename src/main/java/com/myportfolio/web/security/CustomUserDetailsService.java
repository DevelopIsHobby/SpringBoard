package com.myportfolio.web.security;

import com.myportfolio.web.domain.MemberVO;
import com.myportfolio.web.mapper.MemberMapper;
import com.myportfolio.web.security.domain.CustomUser;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Setter(onMethod_= @Autowired)
    public MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.warn("Load User By UserName : " + userName);

        MemberVO vo = memberMapper.read(userName);

        log.warn("queried by member mapper : " + vo);
        return vo == null? null : new CustomUser(vo);
    }
}
