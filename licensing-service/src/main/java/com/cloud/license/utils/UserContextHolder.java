package com.cloud.license.utils;

import org.springframework.util.Assert;

/**
 * UserContextHolder.
 *
 * @author legion
 * @version 5.0
 * @since 25/12/2022
 */
public final class UserContextHolder {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    private UserContextHolder() {
        throw new RuntimeException("NO CALL");
    }

    public static UserContext getContext() {
        var context = userContext.get();
        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);
        }
        return userContext.get();
    }

    public static void setContext(final UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    public static UserContext createEmptyContext() {
        return new UserContext();
    }

    public static void unload() {
        userContext.remove();
    }
}
