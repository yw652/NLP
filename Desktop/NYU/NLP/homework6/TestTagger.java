import opennlp.maxent.*;
import opennlp.maxent.io.*;
import opennlp.model.EventStream;
import java.io.*;
import java.util.*;
import java.util.HashMap;

public class TestTagger{
	public static void main(String [] args) throws IOException{
		TestTagger tagger = new TestTagger();
		// produce the results for the models of each feature set
		tagger.tagWithModel1("model1", "result1");
		tagger.tagWithModel2("model2", "result2");
		tagger.tagWithModel3("model3", "result3");
		tagger.tagWithModel4("model4", "result4");
		// Produce the number of the correct tags
		tagger.evaluate("test.txt", "result1");
		tagger.evaluate("test.txt", "result2");
		tagger.evaluate("test.txt", "result3");
		tagger.evaluate("test.txt", "result4");

		// tag the noun-group
		tagger.NPGroups("test.txt", "testTagger");
		tagger.NPGroups("result1", "NPgroup1");
		tagger.NPGroups("result2", "NPgroup2");
		tagger.NPGroups("result3", "NPgroup3");
		tagger.NPGroups("result4", "NPgroup4");

		// calculate the recall, precision and f-measure of the noun phrase group files
		tagger.precisionRecall("testTagger", "NPGroup1");
		tagger.precisionRecall("testTagger", "NPGroup2");
		tagger.precisionRecall("testTagger", "NPGroup3");
		tagger.precisionRecall("testTagger", "NPGroup4");

	}
	private void tagWithModel1(String model, String result){
		try{
			GISModel m = (GISModel) new SuffixSensitiveGISModelReader(new File(model)).getModel();
			Scanner in = new Scanner(new File("test.txt"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(result)));
			while(in.hasNextLine()){
				String line = in.nextLine();
				if (!line.isEmpty()){
					String [] str = line.split(" ");
					String [] features = new String[2];
					features[0] = "current=" + str[0];
					features[1] = "pos=" + str[1];
					String tag = m.getBestOutcome(m.eval(features));
					out.println(str[0] + " " + str[1] + " " + tag);
				} else {
					out.println();
				}

			}
			out.flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void tagWithModel2(String model, String result){
		try{
			GISModel m = (GISModel) new SuffixSensitiveGISModelReader(new File(model)).getModel();
			Scanner in = new Scanner(new File("test.txt"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(result)));
			while(in.hasNextLine()){
				String line = in.nextLine();
				if(!line.isEmpty()){
					String [] str = line.split(" ");
					String [] features = new String[3];
					features[0] = "current=" + str[0];
					features[1] = "pos=" + str[1];
					features[2] = "initCapitalized=" + Character.isUpperCase(str[0].charAt(0));
					String tag = m.getBestOutcome(m.eval(features));
					out.println(str[0] + " " + str[1] + " " + tag);
				} else {
					out.println();
				}
			}
			out.flush();
		} catch (Exception e){
			System.out.println(e);
		}
	}
	private void tagWithModel3(String model, String result){
		try{
			GISModel m = (GISModel) new SuffixSensitiveGISModelReader(new File(model)).getModel();
			Scanner in = new Scanner(new File("test.txt"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(result)));
			String [] prevLine = null;
			String [] features = null;
			while(in.hasNextLine()){
				String line = in.nextLine();
				if(!line.isEmpty()){
					String [] str = line.split(" ");
					if (prevLine != null){
						// Previous Line
						features[5] = "next=" + str[0];
						features[6] = "nextPOS=" + str[1];
						String tag = m.getBestOutcome(m.eval(features));
						out.println(prevLine[0] + " " + prevLine[1] + " " + tag);

						// Current Line
						features = new String[7];
						features[0] = "prev=" + prevLine[0];
						features[1] = "prevPOS=" + prevLine[1];
					} else {
						features = new String[7];
						features[0] = "prev=null";
						features[1] = "prevPOS=null";
					}
					features[2] = "current=" + str[0];
					features[3] = "pos=" + str[1];
					features[4] = "initCapitalized=" + Character.isUpperCase(str[0].charAt(0));
					prevLine = str;
				} else {
					features[5] = "next=null";
					features[6] = "nextPOS=null";
					String tag = m.getBestOutcome(m.eval(features));
					out.println(prevLine[0] + " " + prevLine[1] + " " + tag);
					out.println();
					prevLine = null;
				}
			}
			out.flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void tagWithModel4(String model, String result) throws IOException{
		try{
			GISModel m = (GISModel) new SuffixSensitiveGISModelReader(new File(model)).getModel();
			Scanner in = new Scanner(new File("test.txt"));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(result)));
			String [] prevLine = null;
			String [] features = null;
			while(in.hasNextLine()){
				String line = in.nextLine();
				if (!line.isEmpty()){
					String [] str = line.split(" ");
					if(prevLine != null){
						// previous line
						features[6] = "next=" + str[0];
						features[7] = "nextPOS=" + str[1];
						String tag = m.getBestOutcome(m.eval(features));
						out.println(prevLine[0] + " " + prevLine[1] + " " + tag);

						// current line
						features = new String [8];
						features[0] = "prev=" + prevLine[0];
						features[1] = "prevPOS=" + prevLine[1];
					} else {
						features = new String[8];
						features[0] = "prev=null";
						features[1] = "prevPOS=null";
					}
					features[2] = "current=" + str[0];
					features[3] = "pos=" + str[1];
					features[4] = "initCapitalized=" + Character.isUpperCase(str[0].charAt(0));
					if (str[0].length() >= 3){
						features[5] = "isSuffixIng=" + str[0].substring(str[0].length() - 3).equals("ing");
					} else {
						features[5] = "isSuffixIng=False";
					}
					prevLine = str;
				} else {
					features[6] = "next=null";
					features[7] = "nextPOS=null";
					String tag = m.getBestOutcome(m.eval(features));
					out.println(prevLine[0] + " " + prevLine[1] + " " + tag);
					out.println();
					prevLine = null;
				}
			}
			out.flush();
		} catch (Exception e){
			System.out.println(e);
		}
		
	}
	public static void evaluate(String testFile, String resultFile) throws FileNotFoundException {
		Scanner readTest = new Scanner(new File(testFile));
		Scanner readResult = new Scanner(new File(resultFile));
		int correct = 0;
		int count = 0;
		try{
			while(readTest.hasNextLine() && readResult.hasNextLine()){
				count++;
				String lineTest = readTest.nextLine();
				String lineResult = readResult.nextLine();
				if(!lineTest.isEmpty()){
					if(lineTest.equals(lineResult)){
						++correct;
					}
				}
			}
			double accuracy = ((double) correct / count) ;
			System.out.println("Correct tags " + correct + ", accuracy is " + accuracy);
		} catch (Exception e){
			System.out.println(e);
		}

	}
	
	public static void NPGroups(String result, String nGroup) throws IOException {
		try{
			Scanner in = new Scanner (new File(result));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(nGroup)));
			// boolean variable indicating whether there's a current Noun-Group
			boolean isGroup = false;
			String output = "";
			while(in.hasNextLine()){
				String line = in.nextLine();
				if(!line.isEmpty()){
					String [] str = line.split(" ");
					if (isGroup == false && str[2].equals("I")){
						output += str[0] + " "; 
					} else if (isGroup == true && !(str[2].equals("I"))){
						out.println(output);
						// out.println("n1");
						if (str[2].equals("O")){ // if "O", currently not in NG, return to false and clear out output
							isGroup = false;
							output = "";
						} else { // if "B", indicate the start of the next NG, and add this word to output
							isGroup = true;
							output += str[0] + " ";
						}
					} else if (isGroup == true && str[2].equals("I")){ // if currently in NG and word is in NG, add
						output += str[0] + " ";
					} else if (isGroup == false && !(str[2].equals("I"))){ // if in NG and word isn't "I", the break of the current NG
						if (!output.isEmpty()){ // if output has content, print
							out.println(output);
							// out.println("n2");
							output = "";
						}
						if (str[2].equals("B")){ // if it's the start of the next NG, update output and isGroup
							output += str[0] + " ";
							isGroup = true;
						}
					}
				} else {
					if (isGroup == true){
						out.println(output);
						// out.println("n3");
						output = "";
						isGroup = false;
					}
					out.println();
				}
			}
			out.flush();
		} catch (Exception e){
			System.out.println(e);
		}
	}

	public static void precisionRecall(String test, String result){
		try {
			System.out.println("here");
			Scanner in1 = new Scanner(new File(test));
			Scanner in2 = new Scanner(new File(result));
			// for precision
			int totalSelect = 0;
			// for recall
			int totalCorrect = 0;
			// # correct tags
			int truePositive = 0;
			// line parsing
			String line1;
			String line2;
			while(true) {
				if (in1.hasNextLine() && in2.hasNextLine()){
					line1 = in1.nextLine();
					line2 = in2.nextLine();
				} else {
					break;
				}
				HashMap<String, Boolean> map = new HashMap<String, Boolean>();
				while (true) {
					if (!line1.isEmpty()){
						map.put(line1, true);
						++totalCorrect;
					}
					if (in1.hasNextLine()){
						line1 = in1.nextLine();
						if (line1.isEmpty()){
							break;
						}
					}
				}
				while (true) {
					if (!line2.isEmpty()){
						++ totalSelect;
						if (map.containsKey(line2)) {
							++truePositive;
						}
					}
					if (in2.hasNextLine()){
						line2 = in2.nextLine();
						if (line2.isEmpty()){
							break;
						}
					}
				}
			}
			if (!in1.hasNextLine()){
				if (in2.hasNextLine()){
					line2 = in2.nextLine();
					if (!line2.isEmpty()){
						++totalSelect;
					}
					while(in2.hasNextLine()){
						line2 = in2.nextLine();
						if(!line2.isEmpty()){
							++totalSelect;
						}
					}
				}
			}
			if (!in2.hasNextLine()) {
				if (in1.hasNextLine()) {
					line1 = in1.nextLine();
					if (!line1.isEmpty()){
						++totalCorrect;
					}
					while(in1.hasNextLine()){
						line1 = in1.nextLine();
						if(!line1.isEmpty()){
							++totalCorrect;
						}
					}
				}
			}
			double precision = (double) truePositive / totalSelect;
			double recall = (double) truePositive / totalCorrect;
			double fMeasure = 2 * precision * recall / (precision + recall);
			System.out.println("For " + result + ": ");
			System.out.print("Precision is " + precision + ", ");
			System.out.print("Recall is " + recall + ", ");
			System.out.println("F-Measure is " + fMeasure);


		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
