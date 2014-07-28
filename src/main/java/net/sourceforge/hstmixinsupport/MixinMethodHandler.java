package net.sourceforge.hstmixinsupport;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

import javax.jcr.Node;

import org.hippoecm.hst.content.beans.standard.HippoBean;

public class MixinMethodHandler extends Handler implements MethodHandler {

    public MixinMethodHandler(HippoBean bean) {
        super(bean);
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        Object result = null;
        if (proceed != null) {
            result = thisMethod.invoke(bean, args);
        } else {
            if (args == null || args.length == 0) {
                Class<?> returnType = thisMethod.getReturnType();
                String path = getPath(thisMethod);
                if (isPropertyType(returnType)) {
                    result = bean.getProperty(path);
                } else if (bean.getNode().hasNode(path)) {
                    Node node = bean.getNode().getNode(path);
                    if (node.isNodeType("hippo:mirror")) {
                        result = handleHippoMirrorCalls(thisMethod, returnType, path);
                    } else {
                        result = handleNoneHippoMirrorCalls(returnType, path);
                    }

                }
                if (result == null) {
                    result = getProperNullValue(thisMethod);
                }
            } else {
                throw new IllegalArgumentException("this handler only supports undefined getter methods");
            }

        }
        return result;
    }

}
