package cosmetics.demo.Controller;

import cosmetics.demo.Domain.Entity.BoardEntity;
import cosmetics.demo.Service.BoardService;
import cosmetics.demo.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    /* 게시글 목록 */
    @GetMapping("/board/list")
    public ResponseList list() {
        List<BoardDto> boardList = boardService.getBoardlist();

        return new ResponseList(boardList.size(), boardList);
    }
    @Data
    @AllArgsConstructor
    static class ResponseList<T>{
        private int boardCnt;
        private T data;
    }

    /* 게시글 상세 */
    @GetMapping("/board/{no}")
    public BoardDto detail(@PathVariable("no") Long no) {
        BoardDto boardDTO = boardService.getPost(no);
        boardService.addViews(boardDTO);

        return boardDTO;
    }

    /* 게시글 쓰기 */
    @PostMapping("/board/post")
    public BoardDto write(@RequestParam("memberId") Long memberId, @RequestBody @Valid BoardDto boardDto) {
        boardDto.setViewcnt(0);
        boardService.savePost(memberId, boardDto);

        return boardDto;
    }

    /* 게시글 수정 */
    @GetMapping("/board/post/edit/{no}")
    public BoardDto edit(@PathVariable("no") Long no) {
        BoardDto boardDTO = boardService.getPost(no);

        return boardDTO;
    }

    @PutMapping("/board/post/edit/{no}")
    public BoardDto boardUpdate(@PathVariable("no") Long no, @RequestBody @Valid BoardDto boardDTO) {
        BoardDto boardDto = boardService.updatePost(no, boardDTO);
        return boardDto;
    }

    /* 게시글 삭제 */
    @DeleteMapping("/board/delete/{no}")
    public void delete(@RequestParam("memberId") Long memberId, @PathVariable("no") Long no) {
        boardService.deletePost(memberId, no);
    }

    /* 검색 */
    @GetMapping("/board/search")
    public ResponseList search(@RequestParam(value="keyword") String keyword) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        return new ResponseList(boardDtoList.size(), boardDtoList);
    }
}

