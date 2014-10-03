package net.sourceforge.hstmixinsupport;

import java.util.List;

import javassist.util.proxy.ProxyFactory;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;

public class JavassistProxyFactory extends AbstractProxyFactory {

    public HippoBean getProxy(HippoBean bean) {
        try {
            if (bean == null) {
                throw new IllegalArgumentException("Bean argument is required.");
            }
            List<Class<?>> interfaces = getInterfaces(bean);
            // we sort the interfaces to make sure Javassist caches the classes
            // properly.
            sortInterfaces(interfaces);
            ProxyFactory factory = new ProxyFactory();
            factory.setInterfaces(interfaces.toArray(new Class<?>[interfaces.size()]));
            factory.setSuperclass(bean.getClass());
            factory.setUseCache(true);
            return (HippoBean) factory.create(new Class<?>[0], new Object[0], new MixinMethodHandler(bean));
        } catch (Exception e) {
            throw new HstComponentException(e.getMessage(), e);
        }
    }

}
