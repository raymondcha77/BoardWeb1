package com.ezen.board.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ezen.board.domain.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	@Query("SELECT b FROM Board b")
	Page<Board> getBoardList(Pageable paging);
	
	@Modifying
	@Query("DELETE Board b WHERE b.seq=:seq")
	void deleteBoardBySeq(@Param("seq") Long seq);
}
