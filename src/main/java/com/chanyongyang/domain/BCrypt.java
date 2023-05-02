package com.chanyongyang.domain;

public class BCrypt {
	public static void main(String[] args) {
		
		String pw = "1234";
		String result = org.mindrot.jbcrypt.BCrypt.hashpw(pw, org.mindrot.jbcrypt.BCrypt.gensalt(9));
		String result2 = org.mindrot.jbcrypt.BCrypt.hashpw(pw, org.mindrot.jbcrypt.BCrypt.gensalt(8));
		System.out.println(result);
		System.out.println(result2);
		System.out.println(org.mindrot.jbcrypt.BCrypt.checkpw("1234", "$2a$10$/G9KQqMA7U9X82OSJl9GtO"));
		
//		String result2 = org.mindrot.jbcrypt.BCrypt.hashpw(setPw, org.mindrot.jbcrypt.BCrypt.gensalt(8));
//		System.out.println(org.mindrot.jbcrypt.BCrypt.checkpw("getPw", result));
	}
}
