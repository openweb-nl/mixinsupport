package net.sourceforge.hstmixinsupport.sample;

import java.util.Date;

import net.sourceforge.hstmixinsupport.annotations.Mixin;
import net.sourceforge.hstmixinsupport.annotations.Property;

@Mixin("test:dated")
public interface Dated {
	
	@Property("test:date")
	public Date getDate();

}
