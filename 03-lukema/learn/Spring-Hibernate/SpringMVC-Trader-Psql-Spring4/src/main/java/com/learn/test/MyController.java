package com.learn.test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class MyController extends AbstractController {

    private static final Logger LOG = LogManager.getLogger();

	private String indexView;
	private String searchResultsView;

	@PostConstruct
	public void postConstruct() {
		LOG.info("postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		LOG.info("preDestroy");
	}

	public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) {
		String query = request.getParameter("query");
		if (query != null) {
			return new ModelAndView(getSearchResultsView());
		} else {
			return new ModelAndView(getIndexView());
		}
	}

	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public String getSearchResultsView() {
		return searchResultsView;
	}

	public void setSearchResultsView(String searchResultsView) {
		this.searchResultsView = searchResultsView;
	}
}
