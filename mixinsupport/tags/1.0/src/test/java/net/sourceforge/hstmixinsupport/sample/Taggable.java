package net.sourceforge.hstmixinsupport.sample;

import net.sourceforge.hstmixinsupport.annotations.JcrPath;
import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("hippostd:taggable")
public interface Taggable {
	
	@JcrPath("hippostd:tags")
	public String[] getTags();

}
