package com.elcolect.server.elastic;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;


public class ElasticSearchHandler {
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
    public Aggregations countSearchData(QueryBuilder queryBuilder,TermsBuilder aggregation,String IndexName) {
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
}
