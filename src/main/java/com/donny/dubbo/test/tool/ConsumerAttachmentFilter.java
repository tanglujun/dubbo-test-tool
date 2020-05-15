package com.donny.dubbo.test.tool;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Map;


@Activate(
        group = {"consumer"}
)
public class ConsumerAttachmentFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Map<String, Object> headers = DubboAttachmentHolder.get();
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            invocation.setAttachment(entry.getKey(),entry.getValue());
        }
        Result result = invoker.invoke(invocation);
        return result;
    }
}
