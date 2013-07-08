package net.sourceforge.hstmixinsupport.sample;

import org.hippoecm.hst.content.beans.standard.HippoItem;


public class TestBean extends HippoItem {

	public byte byteType = 1;
	public short shortType = 2;
	public int intType = 3;
	public long longType = 4;
	public float floatType = 5.0F;
	public double doubleType = 6.0;
	public boolean booleanType = true;
	public char charType = 'h';
	public String StringType = "g";

	public byte getByteType() {
		return byteType;
	}

	public void setByteType(byte byteType) {
		this.byteType = byteType;
	}

	public short getShortType() {
		return shortType;
	}

	public void setShortType(short shortType) {
		this.shortType = shortType;
	}

	public int getIntType() {
		return intType;
	}

	public void setIntType(int intType) {
		this.intType = intType;
	}

	public long getLongType() {
		return longType;
	}

	public void setLongType(long longType) {
		this.longType = longType;
	}

	public float getFloatType() {
		return floatType;
	}

	public void setFloatType(float floatType) {
		this.floatType = floatType;
	}

	public double getDoubleType() {
		return doubleType;
	}

	public void setDoubleType(double doubleType) {
		this.doubleType = doubleType;
	}

	public boolean isBooleanType() {
		return booleanType;
	}

	public void setBooleanType(boolean booleanType) {
		this.booleanType = booleanType;
	}

	public char getCharType() {
		return charType;
	}

	public void setCharType(char charType) {
		this.charType = charType;
	}

	public String getStringType() {
		return StringType;
	}

	public void setStringType(String stringType) {
		StringType = stringType;
	}
	
}
