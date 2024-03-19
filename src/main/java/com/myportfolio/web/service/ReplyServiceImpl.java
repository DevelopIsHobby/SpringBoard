package com.myportfolio.web.service;

import com.myportfolio.web.domain.Criteria;
import com.myportfolio.web.domain.ReplyPageDTO;
import com.myportfolio.web.domain.ReplyVO;
import com.myportfolio.web.mapper.BoardMapper;
import com.myportfolio.web.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService{
    private ReplyMapper mapper;
    private BoardMapper boardMapper;

    @Override
    @Transactional
    public int register(ReplyVO vo) {
        boardMapper.updateReplyCnt(vo.getBno(),1);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        return mapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        return mapper.update(vo);
    }

    @Override
    @Transactional
    public int remove(Long rno) {
        ReplyVO vo = mapper.read(rno);
        boardMapper.updateReplyCnt(vo.getBno(),-1);
        return mapper.delete(rno);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {
        return mapper.getListWithPaging(cri, bno);
    }

    @Override
    public ReplyPageDTO getListPage(Criteria cri, Long bno) {
        return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
    }

}
