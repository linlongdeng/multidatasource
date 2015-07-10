package weChat.core.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.HiddenHttpMethodFilter;

public class MyHiddenHttpMethodFilter extends HiddenHttpMethodFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 什么都不做，直接绕过该过滤器，原因是碰到content-type
		// 为application/x-www-form-urlencoded会去解析request的body
		// 导致后续代码无法解析请求了
		filterChain.doFilter(request, response);
		// super.doFilterInternal(request, response, filterChain);
	}

}
