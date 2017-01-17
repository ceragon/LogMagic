package com.logMagic.server.jfinal.controller;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.logMagic.server.jfinal.constant.Enums.NumSelectEnum;
import com.logMagic.server.jfinal.model.NumSelectModel;
import com.logMagic.server.util.TimeUtil;

public abstract class BaseController extends Controller{
	public void setNumSelectAttr(int size){
		List<NumSelectModel> numSelectModels=new ArrayList<NumSelectModel>();
		for (NumSelectEnum selectEnum : NumSelectEnum.values()) {
			if (selectEnum.getValue()==size) {
				numSelectModels.add(new NumSelectModel(selectEnum.getValue(), true));
			}else{
				numSelectModels.add(new NumSelectModel(selectEnum.getValue()));
			}
		}
		setAttr("numSelects", numSelectModels);
	}
	public void setTimeSelectAttr(long startMillins,long endMillins){
		setAttr("nav-start-time", TimeUtil.timeToStr(startMillins));
		setAttr("nav-end-time", TimeUtil.timeToStr(endMillins));
	}
}
