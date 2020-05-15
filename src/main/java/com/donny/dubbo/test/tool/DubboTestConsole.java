/*
 * 深圳市灵智数科有限公司版权所有.
 */
package com.donny.dubbo.test.tool;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明
 * <p>
 *
 * @author 唐陆军
 * @version 1.0.0
 * @date 2020/5/8
 */
public class DubboTestConsole {


    public static void main(String[] args) {

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-test-tool");

// 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("N/A");


// 引用远程服务
// 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
// 弱类型接口名
        reference.setInterface("com.lingzhi.saas.customer.client.CustomerRemoteService");
//        reference.setVersion("1.0.0");
        reference.setUrl("dubbo://localhost:21870");
// 声明为泛化接口
        reference.setGeneric("true");
        //超时1分钟
        reference.setTimeout(60000);

        //reference.setFilter("com.donny.dubbo.test.tool.ConsumerContextFilter");

// 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        Map<String,Object> headers=new HashMap<>();
        headers.put("tenant-id", "1");
        headers.put("store-id", "1");
        DubboAttachmentHolder.set(headers);

// 基本类型以及Date,List,Map等不需要转换，直接调用
  //      Object result = genericService.$invoke("getByOpenId", new String[] {"java.lang.String"}, new Object[] {"world"});

// 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("openid", "xxx");
// 如果返回POJO将自动转成Map
        Object result = genericService.$invoke("getByOpenId",
                new String[]{"com.lingzhi.saas.customer.dto.request.CustomerOpenIdRequest"},
                new Object[]{person}
                );
        System.out.println(JSON.toJSONString(result));

        reference.destroy();

    }
}
