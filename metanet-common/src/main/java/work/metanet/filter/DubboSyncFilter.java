package work.metanet.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
/**dubbo异步调用传递性问题待解决*/
//@Activate(group = {Constants.PROVIDER}, order = -999)
public class DubboSyncFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //避免RpcContext透传,使用配置文件的async
        boolean isAsync = invoker.getUrl().getMethodParameter(invocation.getMethodName(), Constants.ASYNC_KEY, false);
        RpcContext.getContext().setAttachment(Constants.ASYNC_KEY, String.valueOf(isAsync));
        return invoker.invoke(invocation);
    }
}