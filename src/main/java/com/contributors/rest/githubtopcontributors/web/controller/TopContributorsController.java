package com.contributors.rest.githubtopcontributors.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;




import com.contributors.rest.githubtopcontributors.model.TopContributorsList;
import com.contributors.rest.githubtopcontributors.proxy.TopContributorsProxy;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin
@RestController
@RequestMapping("/topcontributors")
@Validated
@Slf4j
public class TopContributorsController {

	private final TopContributorsProxy topContributorsProvider;
	private static final String DEFAULT_PLACE = "Atlanta";
	private static final int DEFAULT_COUNT = 50;
	private static final List<Integer> possibleNumberOfUsers = Stream.of(50,100,150).collect(Collectors.toList());
	

	

    @Autowired
    public TopContributorsController(TopContributorsProxy topContributorsProvider) {
        this.topContributorsProvider = topContributorsProvider;
    }

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public TopContributorsList getTopContributorsByPlace(
			@RequestParam(defaultValue = DEFAULT_PLACE)
			@Pattern(regexp = "^[a-zA-Z]*$")
			@NotNull
			String place, 
			@RequestParam(defaultValue = DEFAULT_COUNT+"") 
			@Positive
			@NotNull
			Integer count) {

    


        log.debug("Returning payload for getTopContributorsForPlace with place={}, total={}",
                place, count);
        if (!isDataValid(place, count)) {
            
            throw new IllegalArgumentException("invalid data is provided");
        }
        return topContributorsProvider.findTopContributors(place, count);

    }
	
	  public static boolean isDataValid(String place, Integer count) {
	    	if (StringUtils.isEmpty(place)) {
	    		return false;
	    	}
	    	
	    	if(count < 0) {
	    		return false;
	    	}
	    	
	    	if(!possibleNumberOfUsers.contains(count)) {
	    		return false;
	    	}
	    	
	    	return true;
	    }
	  
	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<String> handleBusinessException(IllegalArgumentException businessException ) {

	        return new ResponseEntity<>("Please choose the Top 50, Top 100 or Top 150", HttpStatus.NOT_FOUND);
	    }

}
