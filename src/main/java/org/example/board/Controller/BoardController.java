package org.example.board.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.board.BoardDTO.BoardDTO;
import org.example.board.BoardDTO.PageRequestDTO;
import org.example.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list........" +pageRequestDTO);

        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(Model model) {
        log.info("register");
    }

    @PostMapping("/register")
    public String register(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
    log.info("register");

        Long bno = boardService.register(boardDTO);

        log.info("Bno: " + bno);

        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model) {

        log.info("Bno........" +bno);

        BoardDTO boardDTO = boardService.get(bno);

        log.info("boardDTO: " + boardDTO);

        model.addAttribute("dto", boardDTO);

    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("remove");

        boardService.removeWithReplies(bno);

        // 게시글 삭제 후 삭제된 게시글의 번호(bno)를 메시지로 전달
        redirectAttributes.addFlashAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {

        log.info("modify----------");
        log.info("boardDTO: " + boardDTO);

        boardService.modify(boardDTO);

        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/list";

    }


    }



