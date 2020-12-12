package com.contributors.rest.githubtopcontributors.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SearchResult {
	private Integer total_count;
	private List<User> items;
	
}
