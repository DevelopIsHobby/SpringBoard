package com.myportfolio.web.controller;

import com.myportfolio.web.domain.BoardAttachVO;
import com.myportfolio.web.domain.BoardVO;
import com.myportfolio.web.domain.Criteria;
import com.myportfolio.web.domain.PageDTO;
import com.myportfolio.web.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
    private BoardService service;

//    @GetMapping("/list")
//    public void list(Model model) {
//        List<BoardVO> boards = service.getList();
//        log.info("list");
//        model.addAttribute("list", boards);
//    }

    @GetMapping("/list")
    public void list(Criteria cri, Model model) {

        List<BoardVO> boards = service.getList(cri);
        log.info("list " + cri);
        int total = service.getTotal(cri);
        model.addAttribute("list", boards);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public void registerForm() {
        log.info("registerForm");
    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String register(BoardVO board,RedirectAttributes rattr) {
        log.info("register " + board);

        if(board.getAttachList()!=null) {
            board.getAttachList().forEach(attach -> log.info(attach));
        }

        service.register(board);
        rattr.addFlashAttribute("result", board.getBno());
        return "redirect:/board/list";
    }

    @GetMapping({"/get","/modify"})
    public void get(@RequestParam("bno") Long bno, Model model, @ModelAttribute("cri") Criteria cri) {
        log.info("/get or /modify");
        model.addAttribute("board", service.get(bno));
    }


    @PostMapping("/modify")
    @PreAuthorize("principal.username==#board.writer")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri,RedirectAttributes rattr){
        log.info("modify: " + board);
        if(service.modify(board)) {
            rattr.addFlashAttribute("result", "success");
        }
//        rattr.addAttribute("pageNum", cri.getPageNum());
//        rattr.addAttribute("amount", cri.getAmount());
//        rattr.addAttribute("type", cri.getType());
//        rattr.addAttribute("keyword", cri.getKeyword());

        return "redirect:/board/list" + cri.getListLink();
    }

    @PostMapping("/remove")
    @PreAuthorize("principal.username==#writer")
    public String remove(@RequestParam(defaultValue = "1") Long bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rattr, String writer) {
        log.info("remove" + bno);

        List<BoardAttachVO> attachList = service.getAttachList(bno);

        if(service.remove(bno)) {
            deleteFiles(attachList);
            rattr.addFlashAttribute("result", "success");
        }
//        rattr.addAttribute("pageNum", cri.getPageNum());
//        rattr.addAttribute("amount", cri.getAmount());
//        rattr.addAttribute("type", cri.getType());
//        rattr.addAttribute("keyword", cri.getKeyword());

        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping(value = "/getAttachList",
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
        log.info("getAttachList " + bno);

        return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
    }

    private void deleteFiles(List<BoardAttachVO> attachList) {
        if(attachList == null || attachList.size() == 0) {
            return;
        }

        log.info("delete attach files....") ;
        log.info(attachList);

        attachList.forEach(attach -> {
            try {
                Path file = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

                Files.deleteIfExists(file);

                if(Files.probeContentType(file).startsWith("image")) {
                    Path thumbNail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
                    Files.delete(thumbNail);
                }
            } catch(Exception e) {
                log.error("delete file error" + e.getMessage());
            } // end catch
        }); // end forEach
    }
}
