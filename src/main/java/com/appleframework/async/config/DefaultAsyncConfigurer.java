package com.appleframework.async.config;

import com.appleframework.async.constant.AsyncConstant;
import com.appleframework.async.constant.HandleMode;
import com.appleframework.async.pool.RunnableAround;
import org.springframework.util.StringUtils;

/**
 * @author woter
 * @date 2017-12-4 下午2:32:44
 */
public class DefaultAsyncConfigurer implements AsyncConfigurer {

	@Override
	public void configureExecutorConfiguration(ExecutorConfiguration configuration) {
		if (configuration == null) {
			return;
		}
		configuration.setTraced(false);
	}

	@Override
	public RunnableAround getRunnableAround() {
		return null;
	}

	@Override
	public void configureThreadPool(ThreadPoolConfiguration configuration) {
		if (configuration == null) {
			return;
		}
		Integer corePoolSize = configuration.getCorePoolSize();
		Integer maxPoolSize = configuration.getMaxPoolSize();
		Integer maxAcceptCount = configuration.getMaxAcceptCount();
		Long keepAliveTime = configuration.getKeepAliveTime();
		Boolean allowCoreThreadTimeout = configuration.getAllowCoreThreadTimeout();
		String rejectedExecutionHandler = configuration.getRejectedExecutionHandler();

		if (!StringUtils.hasText(rejectedExecutionHandler)) {
			rejectedExecutionHandler = HandleMode.CALLERRUN.toString();
		} else {
			if (!HandleMode.REJECT.toString().equals(rejectedExecutionHandler)
					&& !HandleMode.CALLERRUN.toString().equals(rejectedExecutionHandler)) {
				throw new IllegalArgumentException("Invalid configuration properties async.rejectedExecutionHandler");
			}
		}

		if (corePoolSize == null || corePoolSize <= 0) {
			corePoolSize = Runtime.getRuntime().availableProcessors() * 4;
		}
		if (maxPoolSize == null || maxPoolSize <= 0) {
			maxPoolSize = corePoolSize * 2;
		}
		if (maxAcceptCount == null || maxAcceptCount < 0) {
			maxAcceptCount = corePoolSize;
		}

		if (keepAliveTime == null || keepAliveTime < 0) {
			keepAliveTime = AsyncConstant.ASYNC_DEFAULT_KEEPALIVETIME;
		}
		if (allowCoreThreadTimeout == null) {
			allowCoreThreadTimeout = true;
		}

		configuration.setAllowCoreThreadTimeout(allowCoreThreadTimeout);
		configuration.setCorePoolSize(corePoolSize);
		configuration.setKeepAliveTime(keepAliveTime);
		configuration.setMaxAcceptCount(maxAcceptCount);
		configuration.setMaxPoolSize(maxPoolSize);
		configuration.setRejectedExecutionHandler(rejectedExecutionHandler);
	}

}
