package com.myportfolio.web.service;

import com.myportfolio.web.domain.BoardAttachVO;
import com.myportfolio.web.domain.BoardVO;
import com.myportfolio.web.domain.Criteria;
import com.myportfolio.web.mapper.BoardAttachMapper;
import com.myportfolio.web.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private BoardMapper mapper;

    private BoardAttachMapper attachMapper;

    @Override
    @Transactional
    public void register(BoardVO board) {
        log.info("register....");
        mapper.insertSelectKey(board);
        log.info(board);
        if(board.getAttachList() == null || board.getAttachList().size() <=0) {
            return;
        }
         board.getAttachList().forEach(attach -> {
             attach.setBno(board.getBno());
             attachMapper.insert(attach);
         });
    }

    @Override
    public BoardVO get(Long bno) {
        return mapper.read(bno);
    }

    @Override
    public boolean modify(BoardVO board) {
        log.info("modify...");
        attachMapper.deleteAll(board.getBno());
        boolean modifiyResult = mapper.update(board)==1;
        if(modifiyResult && board.getAttachList() !=null && board.getAttachList().size() >0) {
            board.getAttachList().forEach(attach -> {
                attach.setBno(board.getBno());
                attachMapper.insert(attach);
            });
        }

        return modifiyResult;
    }

    @Override
    @Transactional
    public boolean remove(Long bno) {
        log.info("remove..." + bno);

        attachMapper.deleteAll(bno);

        return mapper.delete(bno)==1;
    }

//    @Override
//    public List<BoardVO> getList() {
//        return mapper.getList();
//    }

    @Override
    public List<BoardVO> getList(Criteria cri) {
        return mapper.getListWithPageing(cri);
    }

    @Override
    public int getTotal(Criteria cri) { return mapper.getTotalCount(cri); }

    @Override
    public List<BoardAttachVO> getAttachList(Long bno) {

        log.info("get Attach list by bno : " + bno);
        List<BoardAttachVO> list = attachMapper.findByBno(bno);
        log.info(list);
        return list;
    }
}
