package net.sourceforge.hstmixinsupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.jcr.Node;

import org.hippoecm.hst.content.beans.standard.HippoBean;

public class MixinInvocationHandler extends Handler implements InvocationHandler {

    private Class<? extends HippoBean> beanClass;

    public MixinInvocationHandler(HippoBean bean) {
        super(bean);
        this.beanClass = bean.getClass();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if (method.getClass().equals(beanClass)) {
            result = method.invoke(bean, args);
        } else {
            if (args == null || args.length == 0) {
                Class<?> returnType = method.getReturnType();
                String path = getPath(method);
                if (isPropertyType(returnType)) {
                    result = bean.getProperty(path);
                } else if (bean.getNode().hasNode(path)) {
                    Node node = bean.getNode().getNode(path);
                    if (node.isNodeType("hippo:mirror")) {
                        result = handleHippoMirrorCalls(method, returnType, path);
                    } else {
                        result = handleNoneHippoMirrorCalls(returnType, path);
                    }
                }
                if (result == null) {
                    result = getProperNullValue(method);
                }

            } else {
                throw new IllegalArgumentException("this handler only supports undefined getter methods");
            }
        }
        return result;
    }

}
