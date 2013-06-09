package net.sourceforge.hstmixinsupport.mock;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jcr.Node;

import org.hippoecm.hst.provider.PropertyMap;
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

	@Override
	public PropertyMap getPropertyMap() {
		return new MockPropertyMap();
	}

	private class MockPropertyMap implements PropertyMap {

		@Override
		public Map<String, Boolean[]> getBooleanArrays() {
			return filterArrayPropertiesByType(Boolean.class);
		}

		@Override
		public Map<String, Boolean> getBooleans() {
			return filterPropertiesByType(Boolean.class);
		}

		@Override
		public Map<String, Calendar[]> getCalendarArrays() {
			return filterArrayPropertiesByType(Calendar.class);
		}

		@Override
		public Map<String, Calendar> getCalendars() {
			return filterPropertiesByType(Calendar.class);
		}

		@Override
		public Map<String, Double[]> getDoubleArrays() {
			return filterArrayPropertiesByType(Double.class);
		}

		@Override
		public Map<String, Double> getDoubles() {
			return filterPropertiesByType(Double.class);
		}

		@Override
		public Map<String, Long[]> getLongArrays() {
			return filterArrayPropertiesByType(Long.class);
		}

		@Override
		public Map<String, Long> getLongs() {
			return filterPropertiesByType(Long.class);
		}

		@Override
		public Map<String, String[]> getStringArrays() {
			return filterArrayPropertiesByType(String.class);
		}

		@Override
		public Map<String, String> getStrings() {
			return filterPropertiesByType(String.class);
		}

		@Override
		public Map<String, Object> getAllMapsCombined() {
			return properties;
		}

		@Override
		public void flush() {
		}

		@SuppressWarnings("unchecked")
		private <T> Map<String, T> filterPropertiesByType(Class<T> clazz) {
			Map<String, T> result = new HashMap<String, T>();
			Set<String> keySet = properties.keySet();
			for (String key : keySet) {
				Object value = properties.get(key);
				if (value != null && clazz.isAssignableFrom(value.getClass())) {
					result.put(key, (T) value);
				}
			}
			return result;
		}
		@SuppressWarnings("unchecked")
		private <T> Map<String, T[]> filterArrayPropertiesByType(Class<T> clazz) {
			Map<String, T[]> result = new HashMap<String, T[]>();
			Set<String> keySet = properties.keySet();
			for (String key : keySet) {
				Object value = properties.get(key);
				if (value != null && value.getClass().isArray() && clazz.isAssignableFrom(value.getClass().getComponentType())) {
					result.put(key, (T[]) value);
				}
			}
			return result;
		}
	}

}
