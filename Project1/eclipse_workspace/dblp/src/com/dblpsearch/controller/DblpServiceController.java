package com.dblpsearch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dblpsearch.model.Hit;
import com.dblpsearch.model.Hits;
import com.dblpsearch.model.SearchInput;
import com.dblpsearch.model.SearchResult;
import com.dblpsearch.model.Shards;
import com.dblpsearch.model.Source;

@RestController
@RequestMapping("/service/dblp/")
public class DblpServiceController {
	
	@RequestMapping(method = RequestMethod.GET)
	public SearchResult getAll1() {
		
		Hit hit1 = new Hit();
		Source source1 = new Source();
		source1.setTitle(":A multi-agent system integrating reinforcement learning, bidding and genetic algorithms.");
		source1.setAuthor(":Dehu Qi:Ron Sun");
		source1.setJournal(":Web Intelligence and Agent Systems");
		source1.setYear(":2003");
		hit1.setSource(source1);
		
		Hit hit2 = new Hit();
		Source source2 = new Source();
		source2.setTitle(":Generic command interpretation algorithms for conversational agents.");
		source2.setAuthor(":Laurent Mazuel:Nicolas Sabouret");
		source2.setJournal(":Web Intelligence and Agent Systems");
		source2.setYear(":2008");
		hit2.setSource(source2);
		
		Hit hit3 = new Hit();
		Source source3 = new Source();
		source3.setTitle(":sslGolog: When conditional compositions of web services meet semantic links and causal laws.");
		source3.setAuthor(":Laurent Mazuel:Nicolas Sabouret");
		source3.setJournal(":Freddy Lécué:Alexandre Delteil:Alain Léger");
		source3.setYear(":2011");
		hit3.setSource(source3);
		
		List<Hit> hitList = new ArrayList<Hit>();
		hitList.add(hit1);
		hitList.add(hit2);
		hitList.add(hit3);
		
		Hits hits = new Hits();
		hits.setHits(hitList);
		
		SearchResult dblpObject = new SearchResult();
		dblpObject.setHits(hits);
		dblpObject.setTook(27);
		dblpObject.setTimedOut(false);
		Shards shards = new Shards();
		shards.setTotal(5);
		shards.setSuccessful(5);
		shards.setFailed(0);
		dblpObject.setShards(shards);
		
		return dblpObject;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody SearchResult getAll(@RequestBody SearchInput searchInput) {
		
		Hit hit1 = new Hit();
		Source source1 = new Source();
		source1.setTitle(":A multi-agent system integrating reinforcement learning, bidding and genetic algorithms.");
		source1.setAuthor(":Dehu Qi:Ron Sun");
		source1.setJournal(":Web Intelligence and Agent Systems");
		source1.setYear(":2003");
		hit1.setSource(source1);
		
		Hit hit2 = new Hit();
		Source source2 = new Source();
		source2.setTitle(":Generic command interpretation algorithms for conversational agents.");
		source2.setAuthor(":Laurent Mazuel:Nicolas Sabouret");
		source2.setJournal(":Web Intelligence and Agent Systems");
		source2.setYear(":2008");
		hit2.setSource(source2);
		
		Hit hit3 = new Hit();
		Source source3 = new Source();
		source3.setTitle(":sslGolog: When conditional compositions of web services meet semantic links and causal laws.");
		source3.setAuthor(":Laurent Mazuel:Nicolas Sabouret");
		source3.setJournal(":Freddy Lécué:Alexandre Delteil:Alain Léger");
		source3.setYear(":2011");
		hit3.setSource(source3);
		
		List<Hit> hitList = new ArrayList<Hit>();
		hitList.add(hit1);
		hitList.add(hit2);
		hitList.add(hit3);
		
		Hits hits = new Hits();
		hits.setHits(hitList);
		
		SearchResult dblpObject = new SearchResult();
		dblpObject.setHits(hits);
		dblpObject.setTook(27);
		dblpObject.setTimedOut(false);
		Shards shards = new Shards();
		shards.setTotal(5);
		shards.setSuccessful(5);
		shards.setFailed(0);
		dblpObject.setShards(shards);
		
		return dblpObject;
	}
}
