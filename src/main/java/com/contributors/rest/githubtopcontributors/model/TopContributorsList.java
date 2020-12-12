package com.contributors.rest.githubtopcontributors.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = { "topContributors"})
public class TopContributorsList {
	List<String> topContributors;

}
