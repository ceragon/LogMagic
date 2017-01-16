package com.logMagic.server.jfinal.model;

public class NumSelectModel {
	private int num;
	private boolean select;
	
	
	public NumSelectModel(int num) {
		super();
		this.num = num;
		this.select = false;
	}
	public NumSelectModel(int num,boolean isSelect) {
		super();
		this.num = num;
		this.select = isSelect;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public boolean getSelect() {
		return select;
	}
	public void setSelect(boolean isSelect) {
		this.select = isSelect;
	}
	
}
