package com.bengyun.webservice.tool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.bengyun.webservice.bean.ResponseHistogramBean;

import lombok.Data;

@Data
public class FluxScriptUtils {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private CloseableHttpClient httpclient;
	private HttpPost httpPost;

	public FluxScriptUtils() {
		httpclient = HttpClients.createDefault();		
		httpPost = new HttpPost("http://47.88.225.240:8086/api/v2/query");
	}
	
	/**
	 * Query Histogram()
	 * */
	public List<ResponseHistogramBean> Histogram(
			String publisher,
			String name,
			String startTime,
			String endTime,
			float[] levels
			) {
		StringBuilder strQuery = new StringBuilder();
		strQuery.append("from(bucket:\"mainflux/autogen\") ");
		strQuery.append("|> range(start:" + startTime + ", stop:" + endTime + ") ");
		strQuery.append("|> filter(fn: (r) => ");
		strQuery.append("   r._measurement == \"messages\" and ");
		strQuery.append("   r._field == \"value\" and ");
		strQuery.append("   r.name == \"" + name + "\" and");
		strQuery.append("   r.channel == \"e5b6fdf5-396a-45a4-9c2a-48aa0503e55b\" and ");
		strQuery.append("   r.publisher == \"" + publisher +  "\") ");
		strQuery.append("|> histogram(bins: " + JSON.toJSONString(levels) +") ");
		logger.info(strQuery.toString());
		StringEntity entity = new StringEntity(strQuery.toString(), 
				ContentType.create("application/vnd.flux", Consts.UTF_8));
		entity.setChunked(true);
		httpPost.setEntity(entity);
		httpPost.addHeader("accept", "application/csv");
		List<ResponseHistogramBean> aHistogramList = new ArrayList<>();
		CloseableHttpResponse response1 = null;
		try {
			/* Send Request */ response1 = httpclient.execute(httpPost);
		    HttpEntity entity1 = response1.getEntity();
		    /* Get Response Stream */ Reader in = new InputStreamReader(entity1.getContent());
		    /* Skip First 3 Lines Of Comment */ int lineNum = 0;
		    while (lineNum < 3) if((char)in.read() == '\n') lineNum++;
		    /* Parse CSV */ Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		    for (CSVRecord record : records) {
		        int aNum = Integer.valueOf(record.get("_value"));
		        float aLevel = Float.valueOf(record.get("le"));
		        if (aNum > 0) aHistogramList.add(new ResponseHistogramBean(aNum, aLevel));
		    }
		    EntityUtils.consume(entity1);
		    in.close();
		    response1.close();
		} catch (IOException e) {
			logger.error("Query failed, error: {}", e.getMessage());
		} catch (IllegalArgumentException e) { /* Can Not Get Column("_value") While Histogram Return Nothing */
			logger.error("Query failed, error: {}", e.getMessage());
		}
		return aHistogramList;
	}
}
