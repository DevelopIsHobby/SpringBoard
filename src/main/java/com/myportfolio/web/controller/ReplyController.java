package com.myportfolio.web.controller;

import com.myportfolio.web.domain.Criteria;
import com.myportfolio.web.domain.ReplyPageDTO;
import com.myportfolio.web.domain.ReplyVO;
import com.myportfolio.web.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies/*")
@Log4j
@AllArgsConstructor
public class ReplyController {
    private ReplyService service;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value="/new",
                    consumes = "application/json", // 들어오는 데이터 타입을 명시
                    produces = {MediaType.TEXT_PLAIN_VALUE}) // 반환하는 데이터 타입을 명시
    public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
        log.info("ReplyVO : " + vo);
        if(vo.getReply() == null || vo.getReply() == "") {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int insertCount = service.register(vo);
        log.info("Reply Insert Count : " + insertCount);


        return insertCount == 1 ?
                new ResponseEntity<>("success", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value="/pages/{bno}/{page}",
                    produces = {
                        MediaType.APPLICATION_XML_VALUE,
                            MediaType.APPLICATION_JSON_VALUE
                    })
    public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page,
                                                @PathVariable("bno") Long bno) {
        Criteria cri = new Criteria(page, 10);
        log.info("get Reply List bno: " + bno);
        log.info("cri: " + cri);

        return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
    }

    @GetMapping(value="/{rno}",
            produces = {MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
        log.info("get: " + rno);

        return new ResponseEntity<>(service.get(rno), HttpStatus.OK);
    }

    @PreAuthorize("principal.username == #vo.replyer")
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) {
        log.info("remove : " + rno);
        log.info("vo : " + vo);
        return service.remove(rno) == 1 ?
                new ResponseEntity<>("success", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("principal.username == #vo.replyer")
    @RequestMapping(method={RequestMethod.PUT, RequestMethod.PATCH},
                        value="/{rno}",
                        consumes = "application/json",
                        produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") Long rno) {
        vo.setRno(rno);
        log.info("rno: " + rno);

        log.info("modify: " + vo);
        return service.modify(vo)==1?
                new ResponseEntity<>("success", HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
