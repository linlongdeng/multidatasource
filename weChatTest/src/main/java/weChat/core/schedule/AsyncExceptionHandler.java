package weChat.core.schedule;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void handleUncaughtException(Throwable ex, Method method,
			Object... params) {
		logger.error("执行异步任务出错，方法是{}, 参数是{}", method.getName(), params);
		logger.error("执行异步线程出错", ex);

	}

}
