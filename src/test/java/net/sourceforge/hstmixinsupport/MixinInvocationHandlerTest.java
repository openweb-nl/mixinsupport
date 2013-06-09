package net.sourceforge.hstmixinsupport;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sourceforge.hstmixinsupport.mock.MockTestBeanFactory;
import net.sourceforge.hstmixinsupport.sample.TestBean;
import net.sourceforge.hstmixinsupport.sample.TestInterface;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.Assert;
import org.junit.Test;

public class MixinInvocationHandlerTest {

	@Test
	public void nullTest() {
		TestBean createBean = MockTestBeanFactory.mockBean(new HashMap<String, Object>(), new HashMap<String, Object>());

		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		interfaces.add(HippoBean.class);
		interfaces.add(TestInterface.class);
		HippoBean proxy = (HippoBean) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				interfaces.toArray(new Class<?>[interfaces.size()]), new MixinInvocationHandler(createBean));
		TestInterface testInterface = (TestInterface) proxy;
		Assert.assertEquals(0.0, testInterface.getDoubleMethod(), 0.0);
		Assert.assertEquals(0, testInterface.getLongMethod());
		Assert.assertNull(testInterface.getStringMethod());
	}
	
	@Test
	public void propertiesTest() {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("test:doubleMethod", 3.5);
		properties.put("test:longMethod", 5L);
		properties.put("test:stringMethod", "test");
		TestBean createBean = MockTestBeanFactory.mockBean(properties, new HashMap<String, Object>());

		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		interfaces.add(HippoBean.class);
		interfaces.add(TestInterface.class);
		HippoBean proxy = (HippoBean) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				interfaces.toArray(new Class<?>[interfaces.size()]), new MixinInvocationHandler(createBean));
		TestInterface testInterface = (TestInterface) proxy;
		Assert.assertEquals(3.5, testInterface.getDoubleMethod(), 0.0);
		Assert.assertEquals(5L, testInterface.getLongMethod());
		Assert.assertEquals("test", testInterface.getStringMethod());
	}

}
