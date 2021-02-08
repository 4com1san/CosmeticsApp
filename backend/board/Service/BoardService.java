package cosmetics.demo.Service;

import cosmetics.demo.Domain.Entity.BoardEntity;
import cosmetics.demo.Domain.Entity.Category;
import cosmetics.demo.Domain.Entity.Member.MemberEntity;
import cosmetics.demo.Domain.Repository.BoardRepository;
import cosmetics.demo.Domain.Repository.MemberRepository;
import cosmetics.demo.dto.BoardDto;
import cosmetics.demo.dto.CommentDto;
import cosmetics.demo.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public List<BoardDto> getBoardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        boardEntities.stream().forEach(boardEntity -> {
            boardDtoList.add(new BoardDto(boardEntity));
        });

        return boardDtoList;
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }


    public BoardDto getPost(Long id) {
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        return new BoardDto(boardEntity);
    }

    @Transactional
    public Long savePost(Long memberId, BoardDto boardDto) {
        MemberEntity findMember = memberRepository.findOne(memberId);

        BoardEntity board = BoardEntity.builder()
                .id(boardDto.getId())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .viewcnt(boardDto.getViewcnt())
                .category(boardDto.getCategory())
                .build();

        board.setMember(findMember);
        return boardRepository.save(board).getId();
    }
    @Transactional
    public Long addViews(BoardDto boardDto){
        int view = boardDto.getViewcnt();
        boardDto.setViewcnt(view+1);
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long memberId, Long boardId) {
        boardRepository.deleteById(boardId);
        MemberEntity findMember = memberRepository.findOne(memberId);
        List<BoardEntity> findBoards = boardRepository.findByMemberEntityId(memberId);
        findMember.setBoards(findBoards);
    }

    //==게시글 찾기==//
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(new BoardDto(boardEntity));
        }

        return boardDtoList;
    }

    //==게시글 업뎃==//
    @Transactional
    public BoardDto updatePost(Long id, BoardDto board){
        Optional<BoardEntity> findBoard = boardRepository.findById(id);
        BoardEntity boardEntity = findBoard.get();
        BoardEntity.builder()
                .content(board.getContent())
                .title(board.getTitle())
                .viewcnt(board.getViewcnt())
                .memberEntity(boardEntity.getMemberEntity())
                .modifiedDate(board.getModifiedDate())
                .build();
        BoardDto boardDto = new BoardDto(boardEntity);
        return boardDto;
    }

    //== 카테고리별 페이징==//
    public List<BoardDto> categoryPage(Category category, Pageable page){
        Page<BoardEntity> categories = boardRepository.findByCategory(category, page);
        List<BoardEntity> contents = categories.getContent();
        List<BoardDto> boardDtos = contents.stream()
                .map(o -> new BoardDto(o))
                .collect(Collectors.toList());

        return boardDtos;
    }

}