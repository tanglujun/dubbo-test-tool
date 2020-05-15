
package com.donny.dubbo.test.tool;

import java.util.Map;


public class DubboAttachmentHolder {

    private final static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    public static void set(Map<String, Object> ctx) {
        THREAD_LOCAL.set(ctx);
    }
}
