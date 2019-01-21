package com.appleframework.async.proxy;

import com.appleframework.async.bean.AsyncMethod;
import com.appleframework.async.cache.AsyncProxyCache;
import com.appleframework.async.core.AsyncExecutor;
import com.appleframework.async.core.AsyncFutureCallable;
import com.appleframework.async.core.AsyncFutureTask;
import com.appleframework.async.exception.AsyncException;
import com.appleframework.async.util.CommonUtil;
import com.appleframework.async.util.ReflectionHelper;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

/**
 * <p>
 *
 *
 *
 * </p>
 *
 * @author woter
 * @date 2016-3-23 下午6:13:58
 */
public class AsyncMethodInterceptor implements MethodInterceptor {

    private Object targetObject;

    public AsyncMethodInterceptor(Object targetObject) {

        this.targetObject = targetObject;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        final String cacheKey = CommonUtil.buildkey(targetObject, method);

        final AsyncMethod asyncMethod = AsyncProxyCache.getAsyncMethod(cacheKey);

        if (asyncMethod == null || !ReflectionHelper.canProxyInvoke(method)) {
            return ReflectionHelper.invoke(targetObject, args, method);
        }
        if (AsyncExecutor.isDestroyed()) {
            return ReflectionHelper.invoke(asyncMethod.getObject(), args, method);
        }

        final Object[] finArgs = args;

        AsyncFutureTask<Object> future = AsyncExecutor.submit(new AsyncFutureCallable<Object>() {

            @Override
            public Object call() throws Exception {
                try {
                    return ReflectionHelper.invoke(asyncMethod.getObject(), finArgs, asyncMethod.getMethod());
                } catch (Throwable e) {
                    throw new AsyncException(e);
                }
            }

            @Override
            public int maxAttemps() {
                return asyncMethod.getRetry().getMaxAttemps();
            }

            @Override
            public long timeout() {
                return asyncMethod.getTimeout();
            }

            @Override
            @SuppressWarnings("unchecked")
            public Class<? extends Throwable>[] exceptions() {
                return new Class[]{TimeoutException.class};
            }

            @Override
            public String cacheKey() {
                return cacheKey;
            }
        });
        if (asyncMethod.isVoid()) {
            return null;
        }

        return new AsyncResultProxy(future).buildProxy(method.getReturnType(), asyncMethod.getTimeout(), true);

    }
}
