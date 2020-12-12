package com.contributors.rest.githubtopcontributors.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User {
	private String login;
}
