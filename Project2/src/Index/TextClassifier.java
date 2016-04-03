package Index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class TextClassifier{

   // private static final long serialVersionUID = -1397598966481635120L;
	
//    public static void main(String[] args) 
//    {
//        try {
//        	TextClassifier cl = new TextClassifier(new NaiveBayesMultinomialUpdateable());
//        	File[] classes = new File("train/").listFiles(File::isDirectory);
//        	
//        	
//        	for (String topic : topics)
//        	{
//        		cl.addCategory(topic);
//        	}
//        	
//            cl.setupAfterCategorysAdded();
//            for(File f : classes){
//            	File[] instances = new File("train/"+f.getName()).listFiles();
//            	for (File instance : instances){
//            		FileInputStream fis = new FileInputStream(instance);
//            		byte[] data = new byte[(int) instance.length()];
//            		fis.read(data);
//            		fis.close();
//            		String str = new String(data, "UTF-8");
//            		//System.out.println(str+" "+f.getName());
//            		cl.addData(str, f.getName());
//            	}
//            }
//
//            //double[] result = cl.classifyMessage("Pool, also more formally known as pocket billiards"
//            
//            /* Task 1. preprocessing words first */
//            TestingData td  = new TestingData();
//            ArrayList<String> inputData =td.getData();
//            
//            for(String input : inputData )
//            {
//	            double[] result = cl.classifyMessage(input);
//	       
//	            //result = cl.classifyMessage("Pattern Matching in Trees and Nets");
//	            // testing input
//	            System.out.println("====== RESULT ====== \tCLASSIFIED AS:\t");
//	            //System.out.println(result.length);
//	            //System.out.println(Arrays.toString(result));
//	            
//	            
//	            /* Boo's code */
//	            ArrayList<Double> arrayList = new ArrayList<Double>();
//	            for(int i =0; i < result.length; i++)
//	            {
//	            	arrayList.add(result[i]);
//	            }
//	            
//	            //System.out.println(arrayList.size());
//	            ArrayList<String> categories = findMax(arrayList,result,topics,5);
//	            System.out.println(categories);
//            
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private Instances trainingData;
    private StringToWordVector filter;
    private Classifier classifier;
    private boolean upToDate;
    private FastVector classValues;
    private FastVector attributes;
    private boolean setup;
    private static String [] topics;

    private Instances filteredData;
    

    public TextClassifier(Classifier classifier) throws FileNotFoundException {
        this(classifier, 10);
    }

    public TextClassifier(Classifier classifier, int startSize) throws FileNotFoundException {
        this.filter = new StringToWordVector();
        this.classifier = classifier;
        // Create vector of attributes.
        this.attributes = new FastVector(2);
        // Add attribute for holding texts.
        this.attributes.addElement(new Attribute("text", (FastVector) null));
        // Add class attribute.
        this.classValues = new FastVector(startSize);
        this.setup = false;

    }
    public void setup(String[] topics, String trainData){
    	TextClassifier.topics = topics;
    	File[] classes = new File("train/").listFiles(File::isDirectory);
    	for (String topic : topics)
    	{
    		this.addCategory(topic);
    	}

    	this.setupAfterCategorysAdded();
    	for(File f : classes){
    		try {
    			File[] instances = new File("train/"+f.getName()).listFiles();
    			for (File instance : instances){
    				FileInputStream fis = new FileInputStream(instance);
    				byte[] data = new byte[(int) instance.length()];
    				fis.read(data);
    				fis.close();
    				String str = new String(data, "UTF-8");
    				//System.out.println(str+" "+f.getName());
    				this.addData(str, f.getName());
    			}}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public ArrayList<String> classify(String title) throws Exception{
    	double[] result = this.classifyMessage(title);
    	ArrayList<Double> arrayList = new ArrayList<Double>();
        for(int i =0; i < result.length; i++)
        {
        	arrayList.add(result[i]);
        }
        
        //System.out.println(arrayList.size());
        ArrayList<String> categories = findMax(arrayList,result,topics,2);
        return categories;
    }

    public void addCategory(String category) {
        category = category.toLowerCase();
        // if required, double the capacity.
        int capacity = classValues.capacity();
        if (classValues.size() > (capacity - 5)) {
            classValues.setCapacity(capacity * 2);
        }
        classValues.addElement(category);
    }

    public void addData(String message, String classValue) throws IllegalStateException {
        if (!setup) {
            throw new IllegalStateException("Must use setup first");
        }
        message = message.toLowerCase();
        classValue = classValue.toLowerCase();
        // Make message into instance.
        Instance instance = makeInstance(message, trainingData);
        // Set class value for instance.
        instance.setClassValue(classValue);
        // Add instance to training data.
        trainingData.add(instance);
        upToDate = false;
    }

    /**
     * Check whether classifier and filter are up to date. Build i necessary.
     * @throws Exception
     */
    private void buildIfNeeded() throws Exception {
        if (!upToDate) {
            // Initialize filter and tell it about the input format.
            filter.setInputFormat(trainingData);
            // Generate word counts from the training data.
            filteredData = Filter.useFilter(trainingData, filter);
            // Rebuild classifier.
            classifier.buildClassifier(filteredData);
            upToDate = true;
        }
    }

    public double[] classifyMessage(String message) throws Exception {
        message = message.toLowerCase();
        message = preprocessingData(message);
        if (!setup) {
            throw new Exception("Must use setup first");
        }
        // Check whether classifier has been built.
        if (trainingData.numInstances() == 0) {
            throw new Exception("No classifier available.");
        }
        buildIfNeeded();
        Instances testset = trainingData.stringFreeStructure();
        Instance testInstance = makeInstance(message, testset);

        // Filter instance.
        filter.input(testInstance);
        Instance filteredInstance = filter.output();
        return classifier.distributionForInstance(filteredInstance);

    }

    private Instance makeInstance(String text, Instances data) {
        // Create instance of length two.
        Instance instance = new Instance(2);
        // Set value for message attribute
        Attribute messageAtt = data.attribute("text");
        instance.setValue(messageAtt, messageAtt.addStringValue(text));
        // Give instance access to attribute information from the dataset.
        instance.setDataset(data);
        return instance;
    }

    public void setupAfterCategorysAdded() {
        attributes.addElement(new Attribute("_class_", classValues));
        // Create dataset with initial capacity of 100, and set index of class.
        trainingData = new Instances("MessageClassificationProblem", attributes, 100);
        trainingData.setClassIndex(trainingData.numAttributes() - 1);
        setup = true;
    }
    
    public static ArrayList<String> findMax(ArrayList<Double> arrayList, double[] input, String [] topics, int number)
    {
    	ArrayList<Double> buf =new ArrayList<Double>();
    	ArrayList<Integer> indexBuf = new ArrayList<Integer>();
    	ArrayList<String> categories = new ArrayList<String>();
    	if (Collections.max(arrayList)==0.1){
    		categories.add("unknown");
    		return categories;
    	}
    	
    	for (int i = 0; i< number ; i++)
    	{
    		/* find maximum value in arrayList */
	    	Double d = Collections.max(arrayList);
	    	
	    	//System.out.println("The highest value is : "+d);
	    	
	    	buf.add(d);
	    	arrayList.remove(d);
	    	
	    	//System.out.println(arrayList.size());
    	}
    	
    	//System.out.println("buf"+buf);
    	
    	/* find index of the values */
    	int lg = input.length;
    	
    	for(double dd : buf)
    	{
    		//System.out.println("dd : "+dd);
    		for (int i = 0; i < lg ; i++)
    	    {
	    		if(dd == input[i])
	    		{
	    			indexBuf.add(i);
	    			//System.out.println("ADD index : "+i);
	    			//System.out.println("dd : "+dd);
	    			break;
	    		}
    	    }
    	}
    	
    	for(int i = 0; i< number; i++)
    	{
    		//System.out.println(""+indexBuf.get(i)+". "+" "+topics[indexBuf.get(i)]+": Probability : "+buf.get(i));
    		
    		categories.add(topics[indexBuf.get(i)]);
    	}
    	
    	return categories;
    	
    }
    
    /* pre-processing data*/
    public static String preprocessingData(String input)
	{
	
		String result = "";
	    String[] words = input.split(" ");
	    
	    ArrayList<String> wordsList = new ArrayList<String>();
	    HashSet<String> stopWordsSet = new HashSet<String>();
	 
	    stopWordsSet.add("a");
	    stopWordsSet.add("an");
	    stopWordsSet.add("at");
	    stopWordsSet.add("and");
	    stopWordsSet.add("the");
	    stopWordsSet.add("for");
	    stopWordsSet.add("of");
	    stopWordsSet.add("in");
	    stopWordsSet.add("on");
	    stopWordsSet.add("or");
	    stopWordsSet.add("to");
	    stopWordsSet.add("via");
	    stopWordsSet.add("from");
	    stopWordsSet.add("through");
	    stopWordsSet.add("with");
	    
	    for(String word : words)
	    {
	        if(!stopWordsSet.contains(word))
	        {
	            wordsList.add(word);
	            result = result+" "+word+" ";
	        }
	    }
	   
	    return result;
    }
}
