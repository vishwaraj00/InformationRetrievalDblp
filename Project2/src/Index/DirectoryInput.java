package Index;

import java.io.File;

/**
 * This class is responsible for getting the directory where all the source files are found.
 */
public class DirectoryInput {
	public static String XmlInputDataFileName="dblp.xml";
	public static String XmlDataDefinitionFileName="dblp.dtd";

	public String getValidDirectoryPathForInputFiles(){
		int maxTries = 3;
		while(0 < maxTries){
			System.out.println("Enter a valid directory Path for xml data and data definition:");
			// TODO: change to scanner input for path.
			//String inputFilesDirectoryPath = in.nextLine();
			String inputFilesDirectoryPath = "data/";
			File file = new File(inputFilesDirectoryPath);
			if ((file.isDirectory()) && isDirectoryHavingXmlInputDataFile(inputFilesDirectoryPath) && isDirectoryHavingXmlDataDefinitionFile(inputFilesDirectoryPath)){
				System.out.println("Accepting the input files directory Path: '"+ inputFilesDirectoryPath + "'.");
				return inputFilesDirectoryPath;
			}
			else{
				maxTries--;
				System.out.println("The path entered: '"+ inputFilesDirectoryPath +"' does not belong to a directory with required files.");
			}
		}

		System.out.println("Could not get a valid path, maximum tries exceeded.");
		return "";
	}

	private boolean isDirectoryHavingXmlInputDataFile(String directoryPath){
		return isValidFilePath(GetFullPath(directoryPath, XmlInputDataFileName));
	}

	private boolean isDirectoryHavingXmlDataDefinitionFile(String directoryPath){
		return isValidFilePath(GetFullPath(directoryPath, XmlDataDefinitionFileName));
	}

	public String GetFullPath(String dirPath, String fileName){
		File file1 = new File(dirPath);
		File file2 = new File(file1, fileName);
		return file2.getPath();
	}

	private boolean isValidFilePath(String filePath){
		File file = new File(filePath);
		if(file.exists() && (file.isFile())){
			System.out.println("File detected: '"+ filePath +"'.");
			return true;
		}
		else{
			System.out.println("File: '"+ filePath +"' not found.");
			return false;
		}
	}
}
