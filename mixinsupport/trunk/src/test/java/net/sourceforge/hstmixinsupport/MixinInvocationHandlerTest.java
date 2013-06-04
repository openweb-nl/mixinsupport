package net.sourceforge.hstmixinsupport;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.hstmixinsupport.sample.CarouselBannerPicker;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoItem;
import org.junit.Assert;
import org.junit.Test;

public class MixinInvocationHandlerTest {
	
	@Test
	public void test() {

		List<Class<?>> interfaces = new ArrayList<Class<?>>();
		interfaces.add(HippoBean.class);
		interfaces.add(CarouselBannerPicker.class);
		HippoBean proxy = (HippoBean) Proxy.newProxyInstance(Thread
				.currentThread().getContextClassLoader(), interfaces
				.toArray(new Class<?>[interfaces.size()]),
				new MixinInvocationHandler(new HippoItem()));
		Assert.assertEquals(true, proxy instanceof CarouselBannerPicker);
		CarouselBannerPicker carouselPicker = (CarouselBannerPicker) proxy;
	}
}
