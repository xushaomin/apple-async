package com.appleframework.async.proxy;

import org.springframework.cglib.core.DefaultNamingPolicy;

/**
 * @author woter
 * @date 2016-9-2 上午11:06:06
 */
public class AsyncNamingPolicy extends DefaultNamingPolicy {
    public static final AsyncNamingPolicy INSTANCE = new AsyncNamingPolicy();

    @Override
    protected String getTag() {
        return "ByAsyncCGLIB";
    }
}
 