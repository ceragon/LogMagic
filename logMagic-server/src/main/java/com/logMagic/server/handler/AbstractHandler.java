package com.logMagic.server.handler;

import java.util.Map;


public abstract class AbstractHandler {

	public abstract Map<String, Long> countGroupBy(String indexName,String tableName,String groupByField,int showSize,long startTime,long endTime);
}
