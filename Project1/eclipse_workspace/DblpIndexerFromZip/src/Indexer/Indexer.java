
package Indexer;


/**
 * @author vishwarajanand
 * This class is responsible for indexing of all documents to elastic search.
 */
public class Indexer {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		DirectoryInput userDirectory = new DirectoryInput();
		String rawDirectory = userDirectory.getValidDirectoryPathForInputFiles();
		String fileName = userDirectory.GetFullPath(rawDirectory, DirectoryInput.XmlInputDataFileName);

		EsClient esClient = new EsClient(); //local-host connection by default
		Parser inputFileParser = new Parser(esClient);
		inputFileParser.parse(fileName);
		esClient.FlushClient();
		
		//int numArticles = inputFileParser.ListArticles.size();
		//int numInproceedings = inputFileParser.ListInproceedings.size();
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time elapsed in milliseconds: " + totalTime);
		
		System.out.println("!! Program Completed Successfully !!");
	}
}
