package net.sourceforge.hstmixinsupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.site.HstServices;

public class AbstractProxyFactory {

	private static class SingletonHolder {
		private static final MixinInterfaceScannerService INSTANCE = HstServices.getComponentManager().getComponent(
				MixinInterfaceScannerService.class.getName());
	}

	private static MixinInterfaceScannerService getInstance() {
		return SingletonHolder.INSTANCE;
	}

	protected void sortInterfaces(List<Class<?>> interfaces) {
		Collections.sort(interfaces, new Comparator<Class<?>>() {

			@Override
			public int compare(Class<?> o1, Class<?> o2) {
				return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
			}
		});
	}

	protected List<Class<?>> getInterfaces(HippoBean bean) throws RepositoryException {
		List<Class<?>> result = new ArrayList<Class<?>>();
		NodeType[] mixinNodeTypes = bean.getNode().getMixinNodeTypes();
		for (NodeType nodeType : mixinNodeTypes) {
			Class<?> interfaze = getInstance().getInterface(nodeType.getName());
			if (interfaze != null) {
				result.add(interfaze);
			}
		}
		return result;
	}

}
