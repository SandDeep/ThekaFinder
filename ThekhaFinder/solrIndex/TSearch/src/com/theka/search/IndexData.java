package com.theka.search;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theka.search.dao.DbConnect;
import com.theka.search.domain.SolrData;
import com.theka.search.domain.SolrData.NearBy;

public class IndexData {

	private DbConnect dbConnect;
	private String solrUrl;

	public void beginIndex() throws Exception {

		List<SolrData> dbData = dbConnect.getData();

		SolrClient solr = null;
		try {
			solr = new HttpSolrClient(solrUrl);
			for (SolrData data : dbData) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("locId", data.getLocId());
				document.addField("place", data.getPlace());
				document.addField("address", data.getAddress());
				document.addField("metadata", data.getMetadata());
				document.addField("nearByList",
						(data.getNearByList() == null) ? "" : convertToJson(data.getNearByList()));
				solr.add(document);
			}

			solr.commit();
		} finally {
			solr.close();
		}

	}

	public void deleteIndex() throws Exception {
		SolrClient solr = null;
		try {
			solr = new HttpSolrClient(solrUrl);
			solr.deleteByQuery("*:*");
			solr.commit();
		} finally {
			solr.close();
		}
	}

	private String convertToJson(List<NearBy> list) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, list);
		byte[] data = out.toByteArray();
		return new String(data);
	}

	public DbConnect getDbConnect() {
		return dbConnect;
	}

	public void setDbConnect(DbConnect dbConnect) {
		this.dbConnect = dbConnect;
	}

	public String getSolrUrl() {
		return solrUrl;
	}

	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}

}
