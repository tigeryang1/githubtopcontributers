package com.contributors.rest.githubtopcontributors.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



import com.contributors.rest.githubtopcontributors.entity.SearchResult;
import com.contributors.rest.githubtopcontributors.model.TopContributorsList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TopContributorsProxy {
	private static final String githubApiUrl = "https://api.github.com/search/users?q=location:{location}&sort={sort}&order={order}&page={page}&per_page={per_page}";
	private static final String SORT_PARAM = "repository";
	private static final String ORDER_PARAM = "desc";
	private static final String PER_PAGE_PARAM = "50";
	private static final String PAGE_PARAM = "1";

	private RestTemplate restTemplate;
	 
	@Autowired
	public TopContributorsProxy(RestTemplateBuilder builder) {
	    this.restTemplate = builder.build();
	} 

	public TopContributorsList findTopContributors(String place, Integer count) {
        log.debug("Calling findTopContributors with params: place={}, count={}", place, count);
		
		Integer pages = calculatePages(count, getContributorsTotalCount(place));
		List<String> allContributors = fetchContributors(pages, place);	      
		
	    TopContributorsList topContributors = new TopContributorsList(allContributors);

	    return topContributors;
	}
	
	public List<String> fetchContributors(int pages, String place){
		List<String> allContributorNames = new ArrayList<>();
		Map<String,String> params = generateParams(place);
		
	    for (Integer i = 0; i < pages; ++i) {
	    	params.put("page", i.toString());
			SearchResult result = restTemplate.getForObject(githubApiUrl, SearchResult.class, params);
	    	result.getItems().forEach(item -> allContributorNames.add(item.getLogin()));
	    }
		return allContributorNames;
	}
	

	
	
	public Map<String,String> generateParams(String place){
		Map<String, String> params = new HashMap<String, String>();
		params.put("location", place);
		params.put("sort", SORT_PARAM);
		params.put("order", ORDER_PARAM);
		params.put("per_page", PER_PAGE_PARAM);
		params.put("page", PAGE_PARAM);
		
		return params;
	}
	

	
	public Integer calculatePages(Integer count, Integer totalResults) {
		if (totalResults < count) {
	       
			return totalResults/50;
		}
		return count/50;
	}
	
	public Integer getContributorsTotalCount(String place) {
		SearchResult result = restTemplate.getForObject(githubApiUrl, SearchResult.class, generateParams(place));
		return result.getTotal_count();
	}
}
