package Index;

import static org.elasticsearch.node.NodeBuilder.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

public class SearchConference {
	static HashMap<ArrayList<String>,HashMap<String,Double>> conferenceList;
	public static void main(String[] args) throws Exception {
		conferenceList= new HashMap<ArrayList<String>,HashMap<String,Double>>();
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
				.setTypes("conference")
				.setSize(10000).execute().actionGet();
		SearchHit[] hits = response.getHits().getHits();
		for (SearchHit h : hits){
			Map<String,Object> result = h.getSource();   
            //System.out.println(result);
            String year = (String) result.get("year");
            String name = (String) result.get("name");
            ArrayList<String> key = new ArrayList<String>();
            key.add(name);
            key.add(year);
            HashMap<String,Double> topicList = new HashMap<String,Double>();
            topicList.put("AI", Double.parseDouble((String) result.get("Artificial Intelligence")));
            topicList.put("Network", Double.parseDouble((String) result.get("Wireless Communication and Networking")));
            topicList.put("HighPerformance", Double.parseDouble((String) result.get("High Performance Computing")));
            topicList.put("Software", Double.parseDouble((String) result.get("Software Engineering")));
            topicList.put("Operation", Double.parseDouble((String) result.get("Operation Research")));
            topicList.put("Database", Double.parseDouble((String) result.get("Database")));
            topicList.put("IR", Double.parseDouble((String) result.get("Information Retrieval")));
            topicList.put("ML", Double.parseDouble((String) result.get("Machine Learning")));
            topicList.put("Graphic", Double.parseDouble((String) result.get("Computer Graphics, Vision, Animation, and Game Science")));
            topicList.put("DM", Double.parseDouble((String) result.get("Data Mining")));
            conferenceList.put(key,topicList);
		}
		node.close();
		System.out.println("Document Loaded!");
		System.out.println("Conference: "+findRelatedConference("The Future of Software Engineering", "2010", 5));
	}
	
	@SuppressWarnings("unchecked")
	public static List<Entry<String, Double>> findRelatedConference(String name, String year, int topk){
		String relatedConferences = "";
		ArrayList<String> key = new ArrayList<String>();
        key.add(name);
        key.add(year);
        HashMap<String,Double> queryTopicList = conferenceList.get(key);
        HashMap<String,Double> sortedList = new HashMap<String,Double>();
        Iterator<?> it = conferenceList.entrySet().iterator();
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry pair = (Map.Entry)it.next();
            Double similarity = calculateCosineSimilarity(queryTopicList,(HashMap<String,Double>)pair.getValue());
            relatedConferences = ((ArrayList<String>)pair.getKey()).get(0)
            		+" "+((ArrayList<String>)pair.getKey()).get(1);
            Double epsilon = 0.000001;
            if (Math.abs(similarity-1)>epsilon){
            	sortedList.put(relatedConferences, similarity);
            }
            //it.remove(); // avoids a ConcurrentModificationException
            
        }
        List<Entry<String, Double>> greatest = findGreatest(sortedList, topk);
        System.out.println("Top "+topk+" entries:");
        for (Entry<String, Double> entry : greatest)
        {
        	System.out.println(entry);
        }
        return greatest;
        
	}
	
	public static Double calculateCosineSimilarity(HashMap<String, Double> firstFeatures, HashMap<String, Double> secondFeatures) {
		Double similarity = 0.0;
		Double sum = 0.0;	// the numerator of the cosine similarity
		Double fnorm = 0.0;	// the first part of the denominator of the cosine similarity
		Double snorm = 0.0;	// the second part of the denominator of the cosine similarity
		Set<String> fkeys = firstFeatures.keySet();
		Iterator<String> fit = fkeys.iterator();
		while (fit.hasNext()) {
			String featurename = fit.next();
			boolean containKey = secondFeatures.containsKey(featurename);
			if (containKey) {
				sum = sum + firstFeatures.get(featurename) * secondFeatures.get(featurename);
			}
		}
		fnorm = calculateNorm(firstFeatures);
		snorm = calculateNorm(secondFeatures);
		similarity = sum / (fnorm * snorm);
		return similarity;
	}

	/**
	 * calculate the norm of one feature vector
	 * 
	 * @param feature of one cluster
	 * @return
	 */
	public static Double calculateNorm(HashMap<String, Double> feature) {
		Double norm = 0.0;
		Set<String> keys = feature.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String featurename = it.next();
			norm = norm + Math.pow(feature.get(featurename), 2);
		}
		return Math.sqrt(norm);
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
