package net.sourceforge.hstmixinsupport;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.hstmixinsupport.sample.CarouselBannerPicker;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoItem;
import org.junit.Assert;
import org.junit.Test;

public class MixinMethodHandlerTest {
	
	@Test
	public void test() throws IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		interfaces.add(HippoBean.class);
		interfaces.add(CarouselBannerPicker.class);
		javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();
		factory.setInterfaces(interfaces.toArray(new Class<?>[interfaces.size()]));
		factory.setSuperclass(HippoItem.class);
		HippoBean proxy = (HippoBean) factory.create(new Class<?>[0], new Object[0], new MixinMethodHandler(new HippoItem()));
		
		Assert.assertEquals(true, proxy instanceof CarouselBannerPicker);
	}
}
