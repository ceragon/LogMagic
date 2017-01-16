package com.logMagic.server.jfinal.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfinal.core.Controller;
import com.logMagic.server.LogMagicServer;
import com.logMagic.server.handler.AbstractHandler;
import com.logMagic.server.jfinal.constant.Enums.NumSelectEnum;
import com.logMagic.server.jfinal.model.NumSelectModel;
import com.logMagic.server.jfinal.model.hello.CodeLine;
import com.logMagic.server.util.StringUtil;

public class ELogController extends Controller{
	private final static Logger log=LogManager.getLogger(ELogController.class);
	public void query() throws UnknownHostException{
		String sizeStr=getPara("size");
		int size=10;
		if (!StringUtil.isEmpty(sizeStr)) {
			size=Integer.valueOf(sizeStr);
		}
		AbstractHandler handler=LogMagicServer.getHandler();
		Map<String, Long> data=handler.countGroupBy("logstash-*", null, "classline.raw",size,-1,-1);
		List<CodeLine> codeLines=new ArrayList<CodeLine>();
		for (Entry<String, Long> entry : data.entrySet()) {
			CodeLine codeLine=new CodeLine();
			codeLine.setLine(entry.getKey());
			codeLine.setCount(entry.getValue());
			codeLines.add(codeLine);
		}
		setAttr("codeLines", codeLines);
		List<NumSelectModel> numSelectModels=new ArrayList<NumSelectModel>();
		for (NumSelectEnum selectEnum : NumSelectEnum.values()) {
			if (selectEnum.getValue()==size) {
				numSelectModels.add(new NumSelectModel(selectEnum.getValue(), true));
			}else{
				numSelectModels.add(new NumSelectModel(selectEnum.getValue()));
			}
		}
		setAttr("numSelects", numSelectModels);
		render("query.vm");
	}
}
