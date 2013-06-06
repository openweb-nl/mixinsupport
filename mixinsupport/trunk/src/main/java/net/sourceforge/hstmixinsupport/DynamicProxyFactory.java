package net.sourceforge.hstmixinsupport;

import java.lang.reflect.Proxy;
import java.util.List;

import javax.jcr.RepositoryException;

import org.hippoecm.hst.content.beans.standard.HippoBean;

public class DynamicProxyFactory extends AbstractProxyFactory{

	public HippoBean getProxy(HippoBean bean) throws RepositoryException {
		return getProxy(bean, bean.getClass().getClassLoader());
	}

	public HippoBean getProxy(HippoBean bean, ClassLoader classLoader)
			throws RepositoryException {
		if (bean == null) {
			throw new IllegalArgumentException("Bean argument is required.");
		}
		HippoBean result;
		List<Class<?>> interfaces = getInterfaces(bean);
		if (interfaces.size() == 0) {
			interfaces.add(HippoBean.class);
			result = (HippoBean) Proxy.newProxyInstance(classLoader,
					interfaces.toArray(new Class<?>[interfaces.size()]),
					new MixinInvocationHandler(bean));
		} else {
			result = bean;
		}
		return result;
	}


}
