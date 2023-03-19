package com.springboot.fives.login.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.fives.login.vo.User;

import lombok.AllArgsConstructor;

//@Service
//@AllArgsConstructor
public class CustomUserDetailsService {
 

	//@Autowired
	//MemberService memberService;

	//@Override
	//public UserDetails loadUserByUsername(String id) {
 /* 
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		User user = new User();
		user.setId(id);
		user.setPassword(password);		

 
		if (memberService.checkMember(user)) {
			grantedAuthorities.add(new SimpleGrantedAuthority("USER")); // USER 라는 역할을 넣어준다.
			

			user.setName(grantedAuthorities);
			memberService.saveMember(user);

			return new User(user.getId(), user.getPassword(), grantedAuthorities);
		} else {
			throw new UsernameNotFoundException("can not find User : " + id);
		}
*/			
		//return (UserDetails) new User();
	//}

}
