package net.sourceforge.hstmixinsupport;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sourceforge.hstmixinsupport.annotations.Mixin;

import org.reflections.Reflections;

public class MixinInterfaceScannerService {

	private final Map<String, Class<?>> map = new HashMap<String, Class<?>>();

	public MixinInterfaceScannerService() {
		Reflections reflections = new Reflections("");
		Set<Class<?>> interfaces = reflections
				.getTypesAnnotatedWith(Mixin.class);
		for (Class<?> interfaze : interfaces) {
			Mixin annotation = interfaze.getAnnotation(Mixin.class);
			map.put(annotation.value(), interfaze);
		}
	}

	public Class<?> getInterface(String qnameOfMixin) {
		Class<?> result = null;
		if (map.containsKey(qnameOfMixin)) {
			result = map.get(qnameOfMixin);
		}
		return result;
	}

}
