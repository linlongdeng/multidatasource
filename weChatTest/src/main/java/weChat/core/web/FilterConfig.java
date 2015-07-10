package weChat.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class FilterConfig {
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new MyHiddenHttpMethodFilter();
	}
	@Bean
	public MyRequestLogFilter myRequestLogFilter(){
		return new MyRequestLogFilter();
	}
}
