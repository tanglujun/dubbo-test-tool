/*
 * 深圳市灵智数科有限公司版权所有.
 */
package com.donny.dubbo.test.tool.request;

import lombok.Data;

import java.util.List;

/**
 * 功能说明
 * <p>
 *
 * @author 唐陆军
 * @version 1.0.0
 * @date 2020/5/8
 */
@Data
public class DubboTestInvokeRequest {

    private String url;

    private String interfaceName;

    private String methodName;

    private Integer timeout;

    private List<KeyValueRequest> headers;

    private List<KeyValueRequest> params;
}
