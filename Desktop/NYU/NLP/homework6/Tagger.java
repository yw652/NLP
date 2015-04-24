
import opennlp.maxent.*;
import opennlp.maxent.io.*;
import java.io.*;
import opennlp.model.EventStream;

public class Tagger{
    public static void main (String[] args) {
        modeling("event1", "model1");
        modeling("event2", "model2");
        modeling("event3", "model3");
        modeling("event4", "model4");
    }
    public static void modeling (String dataName, String modelName) {
        try {
            FileReader datafr = new FileReader(new File(dataName));
            EventStream es = new BasicEventStream(new PlainTextByLineDataStream(datafr));
            GISModel model = GIS.trainModel(es, 100, 4);
            File outputFile = new File(modelName);
            GISModelWriter writer = new SuffixSensitiveGISModelWriter(model, outputFile);
            writer.persist();
        } catch (Exception e) {
            System.out.print("Unable to create model due to exception: ");
            System.out.println(e);
        }
    }
}
