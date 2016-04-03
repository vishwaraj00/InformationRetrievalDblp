package com.dblpsearch.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.dblpsearch.model.Bool;
import com.dblpsearch.model.Hit;
import com.dblpsearch.model.Query;
import com.dblpsearch.model.SearchForm;
import com.dblpsearch.model.SearchInput;
import com.dblpsearch.model.SearchResult;
import com.dblpsearch.model.Should;
import com.dblpsearch.utility.StringUtil;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
	@RequestMapping(method = RequestMethod.GET)
	public String viewRegistration(Map<String, Object> model) {

		List<String> limits = new ArrayList<String>();
		limits.add("5");
		limits.add("10");
		limits.add("20");
		limits.add("50");
		limits.add("70");
		limits.add("100");
		limits.add("150");
		limits.add("200");
		model.put("limits", limits);

		SearchForm searchForm = new SearchForm();
		model.put("searchForm", searchForm);

		return "SearchForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String sendSearchInputs(@ModelAttribute("searchForm") SearchForm form, Map<String, Object> model) {

		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

		if (!form.getKeyword().isEmpty()) {
			String text = form.getKeyword().trim().toLowerCase();
			Map<String, String> map0 = new HashMap<String, String>();
			Map<String, Object> should0 = new HashMap<String, Object>();
			if (StringUtil.isTextInQuotes(text)) {
				map0.put("_all", StringUtil.removeQuotes(text));
				should0.put("match_phrase", map0);
			} else {
				map0.put("_all", text);
				should0.put("match", map0);
			}
			maps.add(should0);
		}
		if (!form.getTitle().isEmpty()) {
			String text = form.getTitle().trim().toLowerCase();
			Map<String, String> map1 = new HashMap<String, String>();
			Map<String, Object> should1 = new HashMap<String, Object>();
			if (StringUtil.isTextInQuotes(text)) {
				map1.put("title", StringUtil.removeQuotes(text));
				should1.put("match_phrase", map1);
			} else {
				map1.put("title", text);
				should1.put("match", map1);
			}
			maps.add(should1);
		}
		if (!form.getAuthor().isEmpty()) {
			String text = form.getAuthor().trim().toLowerCase();
			Map<String, String> map2 = new HashMap<String, String>();
			Map<String, Object> should2 = new HashMap<String, Object>();
			if (StringUtil.isTextInQuotes(text)) {
				map2.put("author", StringUtil.removeQuotes(text));
				should2.put("match_phrase", map2);
			} else {
				map2.put("author", text);
				should2.put("match", map2);
			}
			maps.add(should2);
		}
		if (!form.getType().isEmpty()) {
			String text = form.getType().trim().toLowerCase();
			Map<String, String> map3 = new HashMap<String, String>();
			Map<String, Object> should3 = new HashMap<String, Object>();
			if (StringUtil.isTextInQuotes(text)) {
				map3.put("type", StringUtil.removeQuotes(text));
				should3.put("match_phrase", map3);
			} else {
				map3.put("type", text);
				should3.put("match", map3);
			}
			maps.add(should3);
		}
		if (!form.getJournal().isEmpty()) {
			String text = form.getJournal().trim().toLowerCase();
			Map<String, String> map4 = new HashMap<String, String>();
			Map<String, Object> should4 = new HashMap<String, Object>();
			if (StringUtil.isTextInQuotes(text)) {
				map4.put("journal", StringUtil.removeQuotes(text));
				should4.put("match_phrase", map4);
			} else {
				map4.put("journal", text);
				should4.put("match", map4);
			}
			maps.add(should4);
		}

		List<Should> shoulds = new ArrayList<Should>();
		for (Map<String, Object> term : maps) {
			Should should = new Should();
			should.setTerm(term);
			shoulds.add(should);
		}

		Bool bool = new Bool();
		bool.setShould(shoulds);
		bool.setMinimumShouldMatch(1);
		bool.setBoost(1);

		Query query = new Query();
		query.setBool(bool);

		SearchInput searchInput = new SearchInput();
		searchInput.setFrom(0);
		searchInput.setSize(Integer.parseInt(form.getLimit()));
		searchInput.setQuery(query);

		// request elasticsearch api
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:9200/dblp/_search";
		SearchResult dblpObjects = restTemplate.postForObject(url, searchInput, SearchResult.class);
		List<Hit> hitList = dblpObjects.getHits().getHits();
	
		model.put("searchForm", form);
		model.put("hitList", hitList);
		return "SearchResult";
//		return new ModelAndView("SearchResult", "hitList", hitList);

	}
}