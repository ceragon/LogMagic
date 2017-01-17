package com.logMagic.server.jfinal.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.logMagic.server.LogMagicServer;
import com.logMagic.server.handler.AbstractHandler;
import com.logMagic.server.jfinal.model.hello.CodeLine;
import com.logMagic.server.util.StringUtil;
import com.logMagic.server.util.TimeUtil;

public class ELogController extends BaseController{
	private final static Logger log=LogManager.getLogger(ELogController.class);
	public void query() throws UnknownHostException{
		long now=System.currentTimeMillis();
		String sizeStr=getPara("size");
		String startTimeStr=getPara("startTime");
		String endTimeStr=getPara("endTime");
		int size=10;
		long startMillins=TimeUtil.getBeginTime(now);
		long endMillins=now;
		if (!StringUtil.isEmpty(sizeStr)) {
			size=Integer.valueOf(sizeStr);
		}
		if (!StringUtil.isEmpty(startTimeStr)) {
			startMillins=TimeUtil.timeFromStr(startTimeStr);
		}
		if (!StringUtil.isEmpty(endTimeStr)) {
			endMillins=TimeUtil.timeFromStr(endTimeStr);
		}
		AbstractHandler handler=LogMagicServer.getHandler();
		Map<String, Long> data=handler.countGroupBy("logstash-*", null, "classline.raw",size,startMillins,endMillins);
		List<CodeLine> codeLines=new ArrayList<CodeLine>();
		for (Entry<String, Long> entry : data.entrySet()) {
			CodeLine codeLine=new CodeLine();
			codeLine.setLine(entry.getKey());
			codeLine.setCount(entry.getValue());
			codeLines.add(codeLine);
		}
		setAttr("codeLines", codeLines);
		setNumSelectAttr(size);
		setTimeSelectAttr(startMillins, endMillins);
		render("query.vm");
	}
}
