package com.myportfolio.web.mapper;

import com.myportfolio.web.domain.BoardVO;
import com.myportfolio.web.domain.Criteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMapper {
//    @Select("select * from tb1_board where bno>0")
    public List<BoardVO> getList();

    public List<BoardVO> getListWithPageing(Criteria cri);

    public void insert(BoardVO board);

    public void insertSelectKey(BoardVO board);

    public BoardVO read(Long bno);

    public int delete(Long bno);

    public int update(BoardVO board);

    public int getTotalCount(Criteria cri);

    public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
