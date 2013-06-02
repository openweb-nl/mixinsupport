package net.sourceforge.hstmixinsupport;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.site.HstServices;

public class ProxyFactory {

	MixinInterfaceScannerService mixinInterfaceScannerService = HstServices
			.getComponentManager().getComponent(
					MixinInterfaceScannerService.class.getName());

	public HippoBean getProxy(HippoBean bean, ClassLoader classLoader) throws RepositoryException {
		if (bean == null) {
			throw new IllegalArgumentException("Bean argument is required.");
		}
		List<Class<?>> interfaces = getInterfaces(bean);
		interfaces.add(bean.getClass());
		return (HippoBean) Proxy.newProxyInstance(classLoader, interfaces.toArray(new Class<?>[interfaces.size()]), new MixinInvocationHandler(bean));
	}

	private List<Class<?>> getInterfaces(HippoBean bean)
			throws RepositoryException {
		List<Class<?>> result = new ArrayList<Class<?>>();
		NodeType[] mixinNodeTypes = bean.getNode().getMixinNodeTypes();
		for (NodeType nodeType : mixinNodeTypes) {
			Class<?> interfaze = mixinInterfaceScannerService
					.getInterface(nodeType.getName());
			if (interfaze != null) {
				result.add(interfaze);
			}
		}
		return result;
	}

}
