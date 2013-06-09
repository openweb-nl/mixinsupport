package net.sourceforge.hstmixinsupport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sourceforge.hstmixinsupport.mock.MockTestBeanFactory;
import net.sourceforge.hstmixinsupport.sample.TestBean;
import net.sourceforge.hstmixinsupport.sample.TestInterface;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoItem;
import org.junit.Assert;
import org.junit.Test;

public class MixinMethodHandlerTest {
	
	@Test
	public void nullTest() throws IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		TestBean createBean = MockTestBeanFactory.mockBean(new HashMap<String, Object>(), new HashMap<String, Object>());

		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		interfaces.add(TestInterface.class);
		javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();
		factory.setInterfaces(interfaces.toArray(new Class<?>[interfaces.size()]));
		factory.setSuperclass(HippoItem.class);
		HippoBean proxy = (HippoBean) factory.create(new Class<?>[0], new Object[0], new MixinMethodHandler(createBean));
		TestInterface testInterface = (TestInterface) proxy; 
		Assert.assertEquals(0.0, testInterface.getDoubleMethod(), 0.0);
		Assert.assertEquals(0, testInterface.getLongMethod());
		Assert.assertNull(testInterface.getStringMethod());
	}
	
	@Test
	public void propertiesTest() throws IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("test:doubleMethod", 3.5);
		properties.put("test:longMethod", 5L);
		properties.put("test:stringMethod", "test");
		TestBean createBean = MockTestBeanFactory.mockBean(properties, new HashMap<String, Object>());

		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		interfaces.add(TestInterface.class);
		javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();
		factory.setInterfaces(interfaces.toArray(new Class<?>[interfaces.size()]));
		factory.setSuperclass(HippoItem.class);
		HippoBean proxy = (HippoBean) factory.create(new Class<?>[0], new Object[0], new MixinMethodHandler(createBean));
		TestInterface testInterface = (TestInterface) proxy;

		Assert.assertEquals(3.5, testInterface.getDoubleMethod(), 0.0);
		Assert.assertEquals(5L, testInterface.getLongMethod());
		Assert.assertEquals("test", testInterface.getStringMethod());
	}
}
