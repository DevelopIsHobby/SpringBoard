package com.myportfolio.web.sample;

import com.myportfolio.web.sampleDomain.SampleVO;
import com.myportfolio.web.sampleDomain.Ticket;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@RestController
@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    @GetMapping("/annoMember")
    public void doMember2() {
        log.info("logined annotation member");
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/annoAdmin")
    public void doAdmin2() {
        log.info("logined annotation admin");
    }

    @GetMapping("/all")
    public void doAll() {
        log.info("do all can access everybody");
    }

    @GetMapping("/member")
    public void doMember() {
        log.info("logined member");
    }

    @GetMapping("/admin")
    public void doAdmin() {
        log.info("admin only");
    }

    @GetMapping(value = "/getSample")
    public SampleVO getText() {
        return new SampleVO(112, "스타", "로드");
    }

    @GetMapping("/getList")
    public List<SampleVO> getList() {
        return IntStream.range(1,10).mapToObj(i->new SampleVO(i, i+"First", i+ "Last")).collect(Collectors.toList());
    }

    @GetMapping(value="/check", params = {"height", "weight"})
    public ResponseEntity<SampleVO> check(Double height, Double weight) {
        SampleVO vo = new SampleVO(0, ""+height, ""+weight);
        ResponseEntity<SampleVO> result = null;
        if(height <150) {
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        } else {
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }
        return result;
    }

    @GetMapping("/product/{cat}/{pid}")
    public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") String pid) {
        return new String[] {"category : " + cat, "productid : " + pid};
    }

    @PostMapping("/ticket")
    public Ticket convert(@RequestBody Ticket ticket) {
        log.info("convert....ticket" + ticket);
        return ticket;
    }
}
