package net.sourceforge.hstmixinsupport.utils;

import java.lang.reflect.Field;
import java.util.Map;

import net.sourceforge.hstmixinsupport.mock.MockObjectConverter;
import net.sourceforge.hstmixinsupport.mock.MockValueProvider;
import net.sourceforge.hstmixinsupport.sample.TestBean;


public class TestBeanFactory {

	public static TestBean createBean(Map<String, Object> properties, Map<String, Object> singleSubnodes) {
		try {
			TestBean result = new TestBean();
			Field valueProviderField = TestBean.class.getSuperclass().getDeclaredField("valueProvider");
			valueProviderField.setAccessible(true);
			valueProviderField.set(result, new MockValueProvider(null, properties));
			result.setObjectConverter(new MockObjectConverter(singleSubnodes));
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
