package net.sourceforge.hstmixinsupport.mock;

import java.util.Map;

import javax.jcr.Node;

import org.hippoecm.hst.provider.jcr.JCRValueProviderImpl;

public class MockValueProvider extends JCRValueProviderImpl {

	private static final long serialVersionUID = 1L;
	
	public MockValueProvider(Node jcrNode, Map<String, Object> properties) {
		super(null);
		this.properties = properties;
	}
	
	
	final Map<String, Object> properties;

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

}
