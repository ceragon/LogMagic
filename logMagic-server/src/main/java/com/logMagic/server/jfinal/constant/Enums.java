package com.logMagic.server.jfinal.constant;

public class Enums {
	public static enum NumSelectEnum{
		S1(5),
		S2(10),
		S3(20),
		S4(50),
		S5(100),
		;
		private final int value;
		private boolean isSelected;
		private NumSelectEnum(int value) {
			this.value = value;
			this.isSelected=false;
		}
		public boolean isSelected() {
			return isSelected;
		}
		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}
		public int getValue() {
			return value;
		}
		
	}
}
