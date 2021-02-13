package cosmetics.demo.Domain.Repository;

import cosmetics.demo.Domain.Entity.BoardEntity;
import cosmetics.demo.Domain.Entity.Category;
import cosmetics.demo.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByTitleContaining(String keyword);
    Page<BoardEntity> findBoardEntitiesByCategory(Category category, Pageable pageable);
    List<BoardEntity> findByMemberEntityId(Long memberId);
    Page<BoardEntity> findBoardEntitiesByCategoryAndTitleContaining(String keyword, Category category, Pageable pageable);
}
