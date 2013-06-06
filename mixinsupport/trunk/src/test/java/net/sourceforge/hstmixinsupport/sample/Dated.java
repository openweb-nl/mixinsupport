package net.sourceforge.hstmixinsupport.sample;

import java.util.Date;

import net.sourceforge.hstmixinsupport.annotations.Mixin;
import net.sourceforge.hstmixinsupport.annotations.JcrPath;

@Mixin("test:dated")
public interface Dated {
	
	@JcrPath("test:date")
	public Date getDate();

}
