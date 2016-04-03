package Index;

import static org.elasticsearch.node.NodeBuilder.*;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import weka.classifiers.bayes.NaiveBayesMultinomialUpdateable;

public class Indexer {

	public static void main(String[] args) throws Exception {
		TextClassifier cl = new TextClassifier(new NaiveBayesMultinomialUpdateable());
		String[] topics = {
                "Artificial Intelligence",
            	"Computer Graphics",
           		"Data mining",
           		"Database",
           		"High Performance Computing",
           		"Information retrieval",
    	        "Machine learning",
    	        "Operations research",
    	        "Software engineering",
    	        "Wireless communications and networking"
       	  };
		cl.setup(topics, "train/");
		System.out.println(cl.classify("Machine learning"));
		DirectoryInput userDirectory = new DirectoryInput();
		String rawDirectory = userDirectory.getValidDirectoryPathForInputFiles();
		String fileName = userDirectory.GetFullPath(rawDirectory, DirectoryInput.XmlInputDataFileName);

		Node node =
				nodeBuilder()
				.settings(Settings.settingsBuilder().put("path.home", 
						"C:/Users/boo/Desktop/IRproject/elasticsearch-2.2.1"))
				.client(true)
				.node();

		Client client = node.client();
		Parser inputFileParser = new Parser(client, cl);

		//ToDo: Have a parser and embed elastic search client here.
		inputFileParser.parse(fileName);


		// on shutdown

		node.close();
	}
}
