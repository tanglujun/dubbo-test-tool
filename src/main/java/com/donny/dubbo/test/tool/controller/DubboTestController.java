package com.donny.dubbo.test.tool.controller;

import com.alibaba.fastjson.JSON;
import com.donny.dubbo.test.tool.DubboAttachmentHolder;
import com.donny.dubbo.test.tool.request.DubboTestInvokeRequest;
import com.donny.dubbo.test.tool.request.KeyValueRequest;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class DubboTestController {


    @PostMapping("/dubbotest/invoke")
    public ResponseEntity invoke(@RequestBody DubboTestInvokeRequest request) {
        String traceId=generateTraceId();
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-test-tool");
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("N/A");
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        try{
            reference.setApplication(application);
            reference.setRegistry(registry);
            reference.setInterface(request.getInterfaceName());
            reference.setUrl(request.getUrl());
            reference.setGeneric("true");
            reference.setTimeout(request.getTimeout());
            GenericService genericService = reference.get();
            if (!CollectionUtils.isEmpty(request.getHeaders())) {
                Map<String, Object> headers = new HashMap<>();
                for (KeyValueRequest header: request.getHeaders()) {
                    headers.put(header.getName(), header.getValue());
                }
                headers.put("X-B3-TraceId", traceId);
                DubboAttachmentHolder.set(headers);
            }
            List<String> paramTypes = new ArrayList<>();
            List<Object> paramValues = new ArrayList<>();
            if (!CollectionUtils.isEmpty(request.getParams())) {
                for (KeyValueRequest param : request.getParams()) {
                    paramTypes.add(param.getName());
                    paramValues.add(JSON.parse(param.getValue()));
                }
            }
            String[] paramTypeArr = new String[paramTypes.size()];
            Long start=System.currentTimeMillis();
            Object result = genericService.$invoke(request.getMethodName(), paramTypes.toArray(paramTypeArr), paramValues.toArray());
            Long end=System.currentTimeMillis();
            return ResponseEntity.ok()
                    .header("time", (end-start)+"")
                    .header("X-B3-TraceId", traceId)
                    .body(result);
        }catch (Exception ex){
            return ResponseEntity.ok().body("调用异常："+ex.getMessage());
        }finally {
            reference.destroy();
        }
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString().split("-")[0] + UUID.randomUUID().toString().split("-")[0];
    }
}
