package Index;

import static org.elasticsearch.node.NodeBuilder.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

public class SearchYear {
	static HashMap<String,HashMap<String,Integer>> yearList;
	public static void main(String[] args) throws Exception {
		yearList= new HashMap<String,HashMap<String,Integer>>();
		System.out.println("Connecting to Elastic Server...");
		Node node =
				nodeBuilder()
				.settings(Settings.settingsBuilder().put("path.home", 
						"C:/Users/boo/Desktop/IRproject/elasticsearch-2.2.1"))
				.client(true)
				.node();

		Client client = node.client();
		System.out.println("Loading Document...");
		SearchResponse response = client.prepareSearch("dblp")
				.setTypes("year")
				.setSize(100).execute().actionGet();
		SearchHit[] hits = response.getHits().getHits();
		for (SearchHit h : hits){
			Map<String,Object> result = h.getSource();   
            //System.out.println(result);
            String year = (String) result.get("year");
            HashMap<String,Integer> topicList = new HashMap<String,Integer>();
            topicList.put("AI", Integer.parseInt((String) result.get("Artificial Intelligence")));
            topicList.put("Network", Integer.parseInt((String) result.get("Wireless Communication and Networking")));
            topicList.put("HighPerformance", Integer.parseInt((String) result.get("High Performance Computing")));
            topicList.put("Software", Integer.parseInt((String) result.get("Software Engineering")));
            topicList.put("Operation", Integer.parseInt((String) result.get("Operation Research")));
            topicList.put("Database", Integer.parseInt((String) result.get("Database")));
            topicList.put("IR", Integer.parseInt((String) result.get("Information Retrieval")));
            topicList.put("ML", Integer.parseInt((String) result.get("Machine Learning")));
            topicList.put("Graphic", Integer.parseInt((String) result.get("Computer Graphics, Vision, Animation, and Game Science")));
            topicList.put("DM", Integer.parseInt((String) result.get("Data Mining")));
            yearList.put(year,topicList);
		}
		node.close();
		System.out.println("Document Loaded!");
		System.out.println("Top 5 topic: "+findTopics("2010", 5));
	}
	
	public static List<Entry<String, Integer>> findTopics(String year, int topk){
        HashMap<String,Integer> yearTopicList = yearList.get(year);
        List<Entry<String, Integer>> greatest = findGreatest(yearTopicList, topk);
        System.out.println("Top "+topk+" entries:");
        for (Entry<String, Integer> entry : greatest)
        {
        	System.out.println(entry);
        }
        return greatest;
        
	}
	
	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> 
	findGreatest(Map<K, V> map, int n)
	{
		Comparator<? super Entry<K, V>> comparator = 
				new Comparator<Entry<K, V>>()
		{
			@Override
			public int compare(Entry<K, V> e0, Entry<K, V> e1)
			{
				V v0 = e0.getValue();
				V v1 = e1.getValue();
				return v0.compareTo(v1);
			}
		};
		PriorityQueue<Entry<K, V>> highest = 
				new PriorityQueue<Entry<K,V>>(n, comparator);
		for (Entry<K, V> entry : map.entrySet())
		{
			highest.offer(entry);
			while (highest.size() > n)
			{
				highest.poll();
			}
		}

		List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
		while (highest.size() > 0)
		{
			result.add(highest.poll());
		}
		return result;
	}
}
