package com.ezen.board.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.board.domain.Member;
import com.ezen.board.persistence.MemberRepository;

/*
 *  MemberRepository에서 조회한 회원정보를 SecurityUser 객체로 감싸거 리턴
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optional = memberRepo.findById(username);
		
		if (optional.isPresent()) {
			Member member = optional.get();
			
			return new SecurityUser(member);
		} else {
			throw new UsernameNotFoundException(username + ": 사용자 없음.");
		}
	}
}
