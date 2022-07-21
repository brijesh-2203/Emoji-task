package com.example.emojiproject.service;

import com.example.emojiproject.model.Emoji;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QueryService {

    @Autowired
    ElasticsearchRestTemplate elasticsearchTemplate;


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    public List<Emoji> getemo(String data,String indexName)
    {
        Map<String,Float> fields1 = new HashMap<String,Float>();
        fields1.put("description",1f);
        fields1.put("aliases",3f);
        fields1.put("tags",4f);
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(data+"*").fields(fields1))
                .build();

        SearchHits<Emoji> output =
                elasticsearchTemplate.search(searchQuery, Emoji.class, IndexCoordinates.of(indexName));

        return output.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Emoji> readAccounts() throws IOException {
        final File file = new File("/home/root322/Downloads/springemoji.json");
        final Emoji[] emojis = new ObjectMapper().readValue(file, Emoji[].class);
        return Arrays.asList(emojis);
    }
    public BulkResponse writeAccounts(String indexName) throws IOException {
        final var bulkRequest = new BulkRequest();
        List<Emoji> emojis = readAccounts();
        emojis.forEach(account -> {
            final var indexRequest = new IndexRequest(indexName);
            indexRequest.source(Emoji.getAsMap(account));
            bulkRequest.add(indexRequest);
        });
        return restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    public String deleteIndex(String indexName) {
        elasticsearchTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
        return "Deleted";
    }


    public List<Emoji> getemojis(String indexName) {
        Query searchQuery2 = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery()).build();

        SearchHits<Emoji> output2 =
                elasticsearchTemplate.search(searchQuery2, Emoji.class, IndexCoordinates.of(indexName));
        return output2.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public boolean getindices(String indexName) throws IOException {

        GetIndexRequest request = new GetIndexRequest("*");
        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        String[] indices = response.getIndices();
        for (String s : indices)
        {
            if(s.contains(indexName))
            {
                System.out.println("data:"+s);
                return true;
            }
        }
        System.out.println(indices);
        return false;
    }

}
