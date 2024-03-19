package com.myportfolio.web.mapper;

import com.myportfolio.web.domain.BoardAttachVO;

import java.util.List;

public interface BoardAttachMapper {
    public void insert(BoardAttachVO vo);

    public void delete(String uuid);

    public void deleteAll(Long bno);

    public List<BoardAttachVO> findByBno(Long bno);
    public List<BoardAttachVO> getOldFiles();

}
