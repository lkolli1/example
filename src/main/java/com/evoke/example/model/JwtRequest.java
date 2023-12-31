package com.evoke.example.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
@Data
public class JwtRequest {

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max=50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 4, max = 40)
	private String password;

	private Set<String> role;
}
