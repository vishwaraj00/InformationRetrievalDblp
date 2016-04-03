package Document;

import java.util.ArrayList;

public class Year {
	public String year;
	public int AI, ComputerGraphic, DataMining, Database, HighPerformance,
	IR, ML, Operation, Software, Network;

	public Year(String y){
		year = y;
		AI = ComputerGraphic = DataMining =  Database= HighPerformance=
		IR = ML = Operation = Software = Network = 0;
	}
	public static String getJsonString(Year y){
		String json = "{" +
		        "\"year\":\""+y.year+"\"," +
		        "\"Artificial Intelligence\":\""+y.AI+"\"," +
		        "\"Computer Graphics, Vision, Animation, and Game Science\":\""+y.ComputerGraphic+"\"," +
		        "\"Data Mining\":\""+y.DataMining+"\"," +
		        "\"Database\":\""+y.Database+"\"," +
		        "\"High Performance Computing\":\""+y.HighPerformance+"\"," +
		        "\"Information Retrieval\":\""+y.IR+"\"," +
		        "\"Machine Learning\":\""+y.ML+"\"," +
		        "\"Operation Research\":\""+y.Operation+"\"," +
		        "\"Software Engineering\":\""+y.Software+"\"," +
		        "\"Wireless Communication and Networking\":\""+y.Network+"\"" +
		    "}";
		return json;
	}
	public void update(ArrayList<String> topics){
		for(String topic:topics){
			if (topic.equals("Artificial Intelligence")){
				AI++;
			}else if (topic.equals("Computer Graphics")){
				ComputerGraphic++;
			}else if (topic.equals("Data mining")){
				DataMining++;
			}else if (topic.equals("Database")){
				Database++;
			}else if (topic.equals("High Performance Computing")){
				HighPerformance++;
			}else if (topic.equals("Information retrieval")){
				IR++;
			}else if (topic.equals("Machine learning")){
				ML++;
			}else if (topic.equals("Operations research")){
				Operation++;
			}else if (topic.equals("Software engineering")){
				Software++;
			}else if (topic.equals("Wireless communications and networking")){
				Network++;
			}
		}
	}
}
