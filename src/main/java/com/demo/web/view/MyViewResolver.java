package com.demo.web.view;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class MyViewResolver implements ViewResolver, Ordered {

	private Map<Set<String>, ViewResolver> viewResolverMap = new HashMap<Set<String>, ViewResolver>();

	private ViewResolver defaultViewResolver = null;

	private int order;

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		Set<Set<String>> keySets = viewResolverMap.keySet();
		for (Set<String> key : keySets) {
			for (String string : key) {
				if (viewName.endsWith(string)) {
					ViewResolver viewResolver = viewResolverMap.get(key);
					return viewResolver.resolveViewName(viewName, locale);
				}
			}
		}
		if (defaultViewResolver != null) {
			return defaultViewResolver.resolveViewName(viewName, locale);
		}
		return null;
	}

	public int getOrder() {
		return order;
	}

	public Map<Set<String>, ViewResolver> getViewResolverMap() {
		return viewResolverMap;
	}

	public void setViewResolverMap(Map<Set<String>, ViewResolver> viewResolverMap) {
		this.viewResolverMap = viewResolverMap;
	}

	public ViewResolver getDefaultViewResolver() {
		return defaultViewResolver;
	}

	public void setDefaultViewResolver(ViewResolver defaultViewResolver) {
		this.defaultViewResolver = defaultViewResolver;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
