package com.donny.dubbo.test.tool.request;

import lombok.Data;

import java.util.List;


@Data
public class DubboTestInvokeRequest {

    private String url;

    private String interfaceName;

    private String methodName;

    private Integer timeout;

    private List<KeyValueRequest> headers;

    private List<KeyValueRequest> params;
}
