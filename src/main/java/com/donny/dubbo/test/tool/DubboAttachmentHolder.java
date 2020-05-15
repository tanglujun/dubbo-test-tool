/*
 * 深圳市灵智数科有限公司版权所有.
 */
package com.donny.dubbo.test.tool;

import java.util.Map;

/**
 * 功能说明
 * <p>
 *
 * @author 唐陆军
 * @version 1.0.0
 * @date 2020/5/8
 */
public class DubboAttachmentHolder {

    private final static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    public static Map<String, Object> get() {
        return THREAD_LOCAL.get();
    }

    public static void set(Map<String, Object> ctx) {
        THREAD_LOCAL.set(ctx);
    }
}
