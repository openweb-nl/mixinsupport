package net.sourceforge.hstmixinsupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.hippoecm.hst.content.beans.standard.HippoBean;

public class MixinInvocationHandler implements InvocationHandler {
	
	private HippoBean bean;
	private Class<? extends HippoBean> beanClass;

	public MixinInvocationHandler(HippoBean bean) {
		this.bean = bean;
		this.beanClass = bean.getClass();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result;
		if (method.getClass().equals(beanClass)) {
			result = method.invoke(bean, args);		
		} else {
			//TODO
			result = null;
		}
		return result;
	}


}
