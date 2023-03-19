package com.springboot.fives.login.vo;

import lombok.Getter;
import lombok.Setter;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User {
 
	private String id;
	private String password;
	private String name;
}
