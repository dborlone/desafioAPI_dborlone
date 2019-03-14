package br.com.pan.challenge.api.config.component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.pan.challenge.api.config.PanApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	@Autowired
	private PanApiProperty panApiProperty;
	
	/*
	 * Defines that any origin will be able to call the API by cors rules
	 */
	private static final String ANY_ORIGIN = "*";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String origemPermitida = panApiProperty.getAllowedCorsOrigin();
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.setHeader("Access-Control-Allow-Origin", origemPermitida);
		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		if(("OPTIONS".equals(req.getMethod())) && (origemPermitida.equals(req.getHeader("Origin")) || origemPermitida.equals(ANY_ORIGIN))){
			res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			res.setHeader("Access-Control-Max-Age", "3600");
			
			res.setStatus(HttpServletResponse.SC_OK);						
		} else {
			chain.doFilter(request, response);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 * @description Este método não é implementado e não pode lançar UnsupportedOperationException pois quebraria o fluxo
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 // default implementation ignored
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 * #description destroy não pode lancar UnsupportedOperationException, pois quebraria o fluxo da api
	 */
	@Override
	public void destroy() { 
		 // default implementation ignored
	}

}
