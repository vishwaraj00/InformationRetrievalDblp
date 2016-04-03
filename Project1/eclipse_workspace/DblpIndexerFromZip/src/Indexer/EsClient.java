package Indexer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EsClient {

	private static String BulkIndexingUrl = "";
	private static String ServerUrl = "";
	private StringBuilder dataStore;
	private int size;
	private static int MaxBulkSize = 60000;
	private static int MaxRetry = 3;
	private static String Header;

	public EsClient(){
		this("http://localhost:9200");
	}

	private EsClient(String providedServerUrl){
		ServerUrl = providedServerUrl;
		BulkIndexingUrl = ServerUrl + "/dblp/dblp/_bulk";
		dataStore = new StringBuilder();
		size = 0;
		Header = CleanString("{ \"index\" : { \"_index\" : \"dblp\", \"_type\" : \"bulk\"} }");
	}

	public boolean BulkPOST(JsonObject json){
		if(MaxBulkSize <= size){
			//POST bulk with retry
			int retry = 0;
			while((retry < MaxRetry) && (!HttpPost(dataStore))){
				retry++;
			}
			
			//reset data stores
			dataStore.setLength(0);
			size = 0;
			
			//return result
			if(MaxRetry == retry){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			dataStore.append(Header);
			dataStore.append(CleanString(json.toString()));
			size++;
			return false;
		}
	}

	public boolean FlushClient(){
		if(0 <= size){
			//POST bulk with retry
			int retry = 0;
			while((retry < MaxRetry) && (!HttpPost(dataStore))){
				retry++;
			}
			
			//reset data stores
			dataStore.setLength(0);
			size = 0;
			
			//return result
			if(MaxRetry == retry){
				return false;
			}
			else{
				return true;
			}
		}
		else{
			return true;
		}
	}
	
	private static String CleanString(String str){
		str = str.trim().replace(System.getProperty("line.separator"), "");
		str += System.getProperty("line.separator");
		return str;
	}

	private boolean HttpPost(StringBuilder input){
		boolean isHttpPostSuccessful = false;
		String responseBody = "";
		//System.out.println("Starting Post of json string.");
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(BulkIndexingUrl);

			InputStream iStream = new ByteArrayInputStream(input.toString().getBytes());;
			InputStreamEntity reqEntity = new InputStreamEntity(iStream, ContentType.APPLICATION_JSON);
			reqEntity.setContentEncoding("UTF-8");
			reqEntity.setChunked(true);

			httpPost.setEntity(reqEntity);
			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(
						final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};

			responseBody = httpclient.execute(httpPost, responseHandler);
			httpclient.close();
			isHttpPostSuccessful = true;
		}
		catch(Exception ex){
			System.out.println("Exception while sending json string: '" + ex.toString() + "' .\nAnd provided response string : '" + responseBody + "'.");
			//System.out.println("Stack trace: " + ex.getStackTrace().toString());
		}
		finally{
			System.out.println("Sent one batch!");
			//System.out.println("Json sending response string: '" + responseBody + "'.");
		}

		return isHttpPostSuccessful;
	}
}
