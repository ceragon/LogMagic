package com.elcolect.server.jfinal.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;

import com.elcolect.server.elastic.ElasticSearchHandler;
import com.elcolect.server.jfinal.model.hello.CodeLine;
import com.jfinal.core.Controller;

public class ELogController extends Controller{
	private final static Logger log=LogManager.getLogger(ELogController.class);
	public void query() throws UnknownHostException{
		QueryBuilder queryBuilder=QueryBuilders.matchAllQuery();
		TermsBuilder termsBuilder=AggregationBuilders.terms("2");
		termsBuilder.field("classline.raw");
		termsBuilder.size(10);
		ElasticSearchHandler handler=new ElasticSearchHandler("10.1.8.215");
		Aggregations aggregations=handler.countSearchData(queryBuilder,termsBuilder, "logstash-*");
		List<CodeLine> codeLines=new ArrayList<CodeLine>();
		for (Aggregation aggregation : aggregations) {
			StringTerms terms=(StringTerms)aggregation;
			for (Bucket bucket : terms.getBuckets()) {
				CodeLine codeLine=new CodeLine();
				codeLine.setLine((String)bucket.getKey());
				codeLine.setCount(bucket.getDocCount());
				codeLines.add(codeLine);
			}
		}
		setAttr("codeLines", codeLines);
		render("query.vm");
	}
}
