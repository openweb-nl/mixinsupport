package net.sourceforge.hstmixinsupport.mock;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectConverterImpl;
import org.hippoecm.hst.content.beans.standard.HippoBean;

public class MockObjectConverter extends ObjectConverterImpl {

	public MockObjectConverter(Map<String, Object> objectMpa) {
		super(new HashMap<String, Class<? extends HippoBean>>(), null);
		// TODO Auto-generated constructor stub
		this.objectMpa = objectMpa;
	}
	
	public Map<String, Object> objectMpa; 

	@Override
	public Object getObject(Node node, String relPath) throws ObjectBeanManagerException {
		return objectMpa.get(relPath);
	}
}
