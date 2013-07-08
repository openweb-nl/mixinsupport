package net.sourceforge.hstmixinsupport;

import net.sourceforge.hstmixinsupport.sample.Dated;

import org.junit.Assert;
import org.junit.Test;


public class MixinInterfaceScannerServiceTest {

	MixinInterfaceScannerService mixinInterfaceScannerService = new MixinInterfaceScannerService();
	
	@Test
	public void getInterfaceTest() {
		Class<?> datedInterface = mixinInterfaceScannerService.getInterface("test:dated");
		Assert.assertEquals(Dated.class, datedInterface);
		Assert.assertEquals(null, mixinInterfaceScannerService.getInterface("test:unknown"));
	}
}
