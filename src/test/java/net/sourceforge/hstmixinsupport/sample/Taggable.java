package net.sourceforge.hstmixinsupport.sample;

import net.sourceforge.hstmixinsupport.annotations.Path;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("hippostd:taggable")
public interface Taggable {
	
	@Path("hippostd:tags")
	public String[] getTags();

}
