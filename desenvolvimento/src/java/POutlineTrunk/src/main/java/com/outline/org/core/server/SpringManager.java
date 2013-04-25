package com.outline.org.core.server;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class SpringManager {

	private static SpringManager manager;
	private ServletContext context;

	private SpringManager() {
		super();
	}
	
	public static SpringManager getInstancia(){
		if (manager == null){
			manager = new SpringManager();
		}
		return manager;
	}
	
	@SuppressWarnings({ "unchecked" })
	protected <T> T getBean(final Class<?> beanClass) {
		String beanName = beanClass.getSimpleName();
		beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
		return (T) getBean(beanName);
	}
	
	@SuppressWarnings({ "unchecked" })
	protected <T> T getBean(final String beanName) {
		final ApplicationContext ctx = (ApplicationContext) context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return (T) ctx.getBean(beanName);
	}
	
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
