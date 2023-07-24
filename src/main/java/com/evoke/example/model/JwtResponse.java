package com.evoke.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	@NonNull
	private String token;

	private String type = "Bearer";

	@NonNull
	private Long id;

	@NonNull
	private String username;

	@NonNull
	private String email;

	@NonNull
	private Set<String> roles;


	public <R> JwtResponse(String token, Long id, String username, String email, R collect) {
		this.token=token;
		this.id=id;
		this.username=username;
		this.email=email;
		this.roles=(Set<String>)collect;
	}
}
