package org.apache.jackrabbit.jcr2spi;

import javax.annotation.meta.When;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeTypeManager;

import org.apache.jackrabbit.jcr2spi.NodeImpl;
import org.apache.jackrabbit.jcr2spi.SessionImpl;
import org.apache.jackrabbit.jcr2spi.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.jcr2spi.state.NodeState;
import org.mockito.Mockito;

public class MockNode extends NodeImpl{

	protected MockNode() throws RepositoryException {
		super(getMockSession(), Mockito.mock(NodeState.class), null);
	}

	private static SessionImpl getMockSession() throws RepositoryException {
		SessionImpl session = Mockito.mock(SessionImpl.class);
		NodeTypeManagerImpl nodeTypeManager = Mockito.mock(NodeTypeManagerImpl.class);
		Mockito.when(nodeTypeManager.hasNodeType((String)null)).thenReturn(true);
		Mockito.when(session.getNodeTypeManager()).thenReturn(nodeTypeManager);
		return session;
	}
	
	public static void main(String[] args) throws RepositoryException {
		new MockNode();
	}

}
