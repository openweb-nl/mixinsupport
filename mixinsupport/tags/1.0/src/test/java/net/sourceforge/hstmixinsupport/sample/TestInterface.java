package net.sourceforge.hstmixinsupport.sample;

import net.sourceforge.hstmixinsupport.annotations.Mixin;

@Mixin("test:Test")
public interface TestInterface {

	public long getLongMethod();

	public double getDoubleMethod();

	public boolean isBooleanMethod();

	public String getStringMethod();

}
