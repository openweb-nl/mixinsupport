package net.sourceforge.hstmixinsupport.sample;

import java.util.Date;

import net.sourceforge.hstmixinsupport.annotations.Mixin;
import net.sourceforge.hstmixinsupport.annotations.Path;

@Mixin("test:dated")
public interface Dated {
	
	@Path("test:date")
	public Date getDate();

}
