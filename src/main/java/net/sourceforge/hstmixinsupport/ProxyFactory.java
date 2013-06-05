package net.sourceforge.hstmixinsupport;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.site.HstServices;

public class ProxyFactory {
	

	MixinInterfaceScannerService mixinInterfaceScannerService = HstServices.getComponentManager().getComponent(
			MixinInterfaceScannerService.class.getName());

	public HippoBean getProxy(HippoBean bean) {
		return getProxy(bean, Thread.currentThread().getContextClassLoader());
	}

	public HippoBean getProxy(HippoBean bean, ClassLoader classLoader) {
		try {
			if (bean == null) {
				throw new IllegalArgumentException("Bean argument is required.");
			}
			List<Class<?>> interfaces = getInterfaces(bean);
			javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();
			factory.setInterfaces(interfaces.toArray(new Class<?>[interfaces.size()]));
			factory.setSuperclass(bean.getClass());
			return (HippoBean) factory.create(new Class<?>[0], new Object[0], new MixinInvocationHandler(bean));
		} catch (Exception e) {
			throw new HstComponentException(e.getMessage(), e);
		}
	}

	private List<Class<?>> getInterfaces(HippoBean bean) throws RepositoryException {
		List<Class<?>> result = new ArrayList<Class<?>>();
		NodeType[] mixinNodeTypes = bean.getNode().getMixinNodeTypes();
		for (NodeType nodeType : mixinNodeTypes) {
			Class<?> interfaze = mixinInterfaceScannerService.getInterface(nodeType.getName());
			if (interfaze != null) {
				result.add(interfaze);
			}
		}
		return result;
	}

}
