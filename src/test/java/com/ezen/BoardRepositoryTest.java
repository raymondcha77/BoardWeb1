package com.ezen;

import java.util.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.ezen.board.domain.Board;
import com.ezen.board.domain.Member;
import com.ezen.board.domain.Role;
import com.ezen.board.persistence.BoardRepository;
import com.ezen.board.persistence.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTest {

	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private BoardRepository boardRepo;
	
	@Test
	@Ignore
	public void testInsert() {
		Member member1 = new Member();
		member1.setId("member1");
		member1.setPassword("member123");
		member1.setName("홍길동");
		member1.setRole(Role.ROLE_MEMBER);
		member1.setEnabled(true);
		
		Member member2 = new Member();
		member2.setId("admin1");
		member2.setPassword("admin123");
		member2.setName("장보고");
		member2.setRole(Role.ROLE_ADMIN);
		member2.setEnabled(true);
		
		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setTitle(member1.getName() + "의 게시글 제목" + i);
			board.setContent(member1.getName() + "의 게시글 내용" + i);
			board.setMember(member1);
		}
		memberRepo.save(member1);

		for(int i=1; i<=3; i++) {
			Board board = new Board();
			board.setTitle(member2.getName() +"의 게시글 제목" + i);
			board.setContent(member2.getName() +"의 게시글 내용" + i);
			board.setMember(member2);
		}
		
		memberRepo.save(member2);
	}
	
	@Test
	@Ignore
	public void testGetBoard() {
		Optional<Board> optionalBoard = boardRepo.findById(1L);
		
		Board board = optionalBoard.get();
		
		System.out.println(board.getSeq());
		System.out.println(board.getTitle());
		System.out.println(board.getContent());
		System.out.println(board.getMember().getName());
	}
	
	@Test
	public void testGetBoardList() {
		
		Member member = memberRepo.findById("member1").get();
		System.out.println(member.getName() + "이 작성한 게시글 >>>>>");
		
		for(Board board: member.getBoardList()) {
			System.out.println(board);
		}
		
		System.out.println("<<<<< 전체 게시글 >>>>>");
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");
		Page<Board> pageList = boardRepo.getBoardList(paging);
		
		List<Board> boardList = pageList.getContent();
		for(Board board : boardList) {
			System.out.println(board);
		}
	}

}
