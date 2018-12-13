package com.itany.bookservice.conf;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    @Bean("solrClient")
    public HttpSolrClient solrClient(){
        HttpSolrClient solrClient = new HttpSolrClient("http://localhost:8080/solr/t_book");
        solrClient.setParser(new XMLResponseParser());
        solrClient.setSoTimeout(5000);
        return solrClient;
    }

}
