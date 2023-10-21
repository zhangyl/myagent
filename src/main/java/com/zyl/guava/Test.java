package com.zyl.guava;

import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		System.out.println("你好 hello world!");
        String token = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        System.out.println(token);
        token = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(token.toUpperCase().getBytes());
        System.out.println(token);
        //RkYxQTlCQzItRjJDQi00MjcxLUI4RjctMzdCMjg3MEY0NEM2
        //RDM2NDY3OUMtQTlEMC00ODBGLUE1MUItRDA5RkUyOUNBODkx
	}
	
	

}
