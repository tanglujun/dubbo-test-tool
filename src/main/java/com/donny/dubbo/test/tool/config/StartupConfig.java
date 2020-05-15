/*
 * 深圳市灵智数科有限公司版权所有.
 */
package com.donny.dubbo.test.tool.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * 功能说明
 * <p>
 *
 * @author 唐陆军
 * @version 1.0.0
 * @date 2020/5/8
 */
@Configuration
public class StartupConfig {

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        System.out.println("应用已经准备就绪 ... 启动浏览器");
        Runtime runtime = Runtime.getRuntime();
        try {
            String [] cmd={"cmd","/C","start chrome.exe http://127.0.0.1:9000/static/index.html"};
            runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}