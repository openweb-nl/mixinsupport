package net.sourceforge.hstmixinsupport;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import net.sourceforge.hstmixinsupport.annotations.Mixin;
import net.sourceforge.hstmixinsupport.annotations.JcrPath;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoMirrorBean;

public class Handler {
	
	protected final HippoBean bean;

	public Handler(HippoBean bean) {
		this.bean = bean;
	}

	protected Object getProperNullValue(Method method) {
		Object result;
		if (method.getReturnType() == Boolean.TYPE) {
			result = false;
		} else if (method.getReturnType() == Integer.TYPE) {
			result = 0;
		} else if (method.getReturnType() == Long.TYPE) {
			result = 0L;
		} else if (method.getReturnType() == Short.TYPE) {
			result = (short) 0;
		} else if (method.getReturnType() == Byte.TYPE) {
			result = (byte) 0;
		} else if (method.getReturnType() == Double.TYPE) {
			result = 0.0;
		} else if (method.getReturnType() == Float.TYPE) {
			result = 0.0F;
		} else if (method.getReturnType() == Void.TYPE) {
			result = Void.TYPE;
		} else {
			result = null;
		}
		return result;
	}

	protected Object handleHippoMirrorCalls(Method method, Class<?> returnType, String path) {
		Object result;
		if (List.class.equals(returnType) || Collection.class.equals(returnType)) {
			result = handleHippoMirrorCallsMultiple(method, path);
		} else {
			result = handleHippoMirrorCallsSingle(method, returnType, path);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Object handleHippoMirrorCallsSingle(Method method, Class<?> returnType, String path) {
		Object result;
		if (HippoMirrorBean.class.isAssignableFrom(returnType)) {
			result = bean.getBean(path);
		} else if (HippoBean.class.isAssignableFrom(returnType)) {
			result = bean.getLinkedBean(path, (Class<? extends HippoBean>) returnType);
		} else {
			throw new IllegalArgumentException("This handler is not handle method " + method.getName()
					+ " because of its return type.");
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Object handleHippoMirrorCallsMultiple(Method method, String path) {
		Object result;
		Class<?> parameterType = getGenericTypeOfCollection(method.getGenericReturnType());
		if (HippoMirrorBean.class.isAssignableFrom(parameterType)) {
			result = bean.getChildBeansByName(path);
		} else if (HippoBean.class.isAssignableFrom(parameterType)) {
			result = bean.getLinkedBeans(path, (Class<? extends HippoBean>) parameterType);
		} else {
			throw new IllegalArgumentException("This handler is not handle method " + method.getName()
					+ " because of its return type.");
		}
		return result;
	}

	protected Object handleNoneHippoMirrorCalls(Class<?> returnType, String path) {
		Object result;
		if (List.class.equals(returnType) || Collection.class.equals(returnType)) {
			result = bean.getChildBeansByName(path);
		} else {
			result = bean.getBean(path);
		}
		return result;
	}

	protected String getPath(Method method) {
		String result;
		JcrPath annotation = method.getAnnotation(JcrPath.class);
		if (annotation != null) {
			result = annotation.value();
		} else {
			result = getNamespace(method.getDeclaringClass()) + ":" + getPropertyLocalName(method);
		}
		return result;
	}

	private String getPropertyLocalName(Method method) {
		String result;
		String methodName = method.getName();
		if (methodName.startsWith("get")) {
			result = methodName.substring(3);
		} else if (methodName.startsWith("is")) {
			result = methodName.substring(2);
		} else {
			result = methodName;
		}
		if (result.length() > 0) {
			result = result.substring(0, 1).toLowerCase() + result.substring(1);
		}
		return result;
	}

	private String getNamespace(Class<?> clazz) {
		Mixin mixin = clazz.getAnnotation(Mixin.class);
		if (mixin == null) {
			throw new IllegalArgumentException("This method only accepts types annotated with " + Mixin.class.getName());
		}
		return getNamespace(mixin.value());
	}

	private static String getNamespace(String value) {
		return value.substring(0, Math.max(0, value.indexOf(':')));
	}

	private Class<?> getGenericTypeOfCollection(Type genericReturnType) {
		Class<?> result = null;
		if (genericReturnType instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
			if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof Class) {
				result = (Class<?>) actualTypeArguments[0];
			}
	
		}
		return result;
	}

	protected boolean isPropertyType(Class<?> returnType) {
		boolean result = false;
		if (returnType.isArray()) {
			Class<?> componentType = returnType.getComponentType();
			result = isSimplePropertyType(componentType);
		} else {
			result = isSimplePropertyType(returnType);
		}
		return result;
	}

	private boolean isSimplePropertyType(Class<?> returnType) {
		boolean result = false;
	
		if (String.class.equals(returnType) || boolean.class.equals(returnType) || Boolean.class.equals(returnType)
				|| int.class.equals(returnType) || long.class.equals(returnType) || Long.class.equals(returnType)
				|| double.class.equals(returnType) || Double.class.equals(returnType)
				|| Calendar.class.equals(returnType)) {
			result = true;
	
		}
		return result;
	}

}
