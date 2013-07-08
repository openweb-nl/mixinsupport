package net.sourceforge.hstmixinsupport.mock;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;

import net.sourceforge.hstmixinsupport.sample.TestBean;

import org.mockito.Mockito;

public class MockTestBeanFactory {

	public static TestBean mockBean(Map<String, Object> properties, Map<String, Object> subnodes) {
		try {
			TestBean result = new TestBean();
			result.setNode(mockNode(subnodes));
			Field valueProviderField = TestBean.class.getSuperclass().getDeclaredField("valueProvider");
			valueProviderField.setAccessible(true);
			valueProviderField.set(result, new MockValueProvider(null, properties));
			result.setObjectConverter(new MockObjectConverter(getSingleSubnodes(subnodes)));
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Map<String, Object> getSingleSubnodes(Map<String, Object> subnodes) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (String nodeName : subnodes.keySet()) {
			Object object = subnodes.get(nodeName);
			if (!(object instanceof Collection)) {
				result.put(nodeName, subnodes.get(nodeName));
			}
		}
		return result;
	}
	private static Node mockNode(Map<String, Object> subnodes) {
		try {
			Node result = Mockito.mock(Node.class);
			for (String nodeName : subnodes.keySet()) {
				Object object = subnodes.get(nodeName);
				if (object instanceof Collection) {
					@SuppressWarnings("rawtypes")
					Collection collection = (Collection) object;
					for (int i = 0; i < collection.size(); i++) {
						handelSingleSubnode(result, nodeName + "[" + (i + 1) + "]", object);
					}
				} else {
					handelSingleSubnode(result, nodeName, object);
				}
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static void handelSingleSubnode(Node result, String nodeName, Object object) throws PathNotFoundException,
			RepositoryException {
		org.hippoecm.hst.content.beans.Node annotation = object.getClass().getAnnotation(
				org.hippoecm.hst.content.beans.Node.class);
		if (annotation != null) {
			Mockito.when(result.getNode(nodeName)).thenReturn(mockSimpleNode(annotation.jcrType()));

		} else {
			throw new IllegalArgumentException("subnode beans need to be annotated by the NodeType.");
		}
	}

	private static Node mockSimpleNode(String type) {
		try {
			Node result = Mockito.mock(Node.class);
			Mockito.when(result.getPrimaryNodeType()).thenReturn(mockNodeType(type));
			Mockito.when(result.isNodeType(type)).thenReturn(true);
			return result;
		} catch (RepositoryException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static NodeType mockNodeType(String type) {
		NodeType result = Mockito.mock(NodeType.class);
		Mockito.when(result.getName()).thenReturn(type);
		return result;

	}

}
