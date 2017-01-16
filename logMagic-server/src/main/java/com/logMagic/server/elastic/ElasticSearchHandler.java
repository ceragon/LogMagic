package com.logMagic.server.elastic;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

import com.logMagic.server.handler.AbstractHandler;
import com.logMagic.server.util.StringUtil;
import com.logMagic.server.util.TimeUtil;


public class ElasticSearchHandler extends AbstractHandler{
	private Client client;
	public ElasticSearchHandler() throws UnknownHostException{
		this("127.0.0.1");
	}
	public ElasticSearchHandler(String ipAddress) throws UnknownHostException{
		InetSocketTransportAddress transportAddress=new InetSocketTransportAddress(InetAddress.getByName(ipAddress), 9300);
		client=TransportClient.builder().build().addTransportAddress(transportAddress);
	}
	 // 获取少量数据100
	private List<String> getSearchData(QueryBuilder queryBuilder,String IndexName) {
        List<String> ids = new ArrayList<String>();
        SearchResponse searchResponse = client.prepareSearch(IndexName)
                .setQuery(queryBuilder).setSize(100)
                .execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit searchHit : searchHits) {
            String id = (String) searchHit.getSource().get("classline");
            ids.add(id);
        }
        return ids;
    }
	private Aggregations countSearchData(QueryBuilder queryBuilder,TermsBuilder aggregation,String IndexName) {
        SearchResponse searchResponse = client.prepareSearch(IndexName)
        		.addAggregation(aggregation)
                .setQuery(queryBuilder)
                .setSize(0)
                .execute().actionGet();
        Aggregations searchHits = searchResponse.getAggregations();
        return searchHits;
    }
	public static void main(String[] args) throws UnknownHostException {
		QueryBuilder queryBuilder=QueryBuilders.matchAllQuery();
		TermsBuilder termsBuilder=AggregationBuilders.terms("2");
		termsBuilder.field("classline.raw");
		termsBuilder.size(10);
		ElasticSearchHandler handler=new ElasticSearchHandler("10.1.8.215");
		Aggregations aggregations=handler.countSearchData(queryBuilder,termsBuilder, "logstash-*");
		for (Aggregation aggregation : aggregations) {
			StringTerms terms=(StringTerms)aggregation;
			for (Bucket bucket : terms.getBuckets()) {
				System.out.println(bucket.getKey()+"  "+bucket.getDocCount());
			}
		}
	}
	@Override
	public Map<String, Long> countGroupBy(String indexName, String typeName, String groupByField, int showSize,long startTime,long endTime) {
		Map<String, Long> mReturn=new LinkedHashMap<String, Long>();
		SearchRequestBuilder requestBuilder=client.prepareSearch(indexName);
		if (!StringUtil.isEmpty(typeName)) {
			requestBuilder.setTypes(typeName);
		}
		
		BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
		RangeQueryBuilder rangeBuilder=QueryBuilders.rangeQuery("@timestamp");
		long now=System.currentTimeMillis();
		if (startTime<0) {
			rangeBuilder.gt(TimeUtil.getBeginTime(now));
		}else{
			rangeBuilder.gt(startTime);
		}
		if (endTime<0) {
			rangeBuilder.lt(TimeUtil.getEndTime(now));
		}else{
			rangeBuilder.lt(endTime);
		}
		rangeBuilder.format("epoch_millis");
		boolQueryBuilder.must(rangeBuilder);
		requestBuilder.setQuery(boolQueryBuilder);
		TermsBuilder aggregation=AggregationBuilders.terms("anwsome");
		aggregation.field(groupByField);
		aggregation.size(showSize);
		SearchResponse searchResponse = requestBuilder.addAggregation(aggregation).setSize(0).execute().actionGet();
        Aggregations searchHits = searchResponse.getAggregations();
        for (Aggregation hitAggs : searchHits.asList()) {
			StringTerms terms=(StringTerms)hitAggs;
			for (Bucket bucket : terms.getBuckets()) {
				mReturn.put((String)bucket.getKey(), bucket.getDocCount());
			}
		}
		return mReturn;
	}
}
