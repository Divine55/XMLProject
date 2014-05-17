import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

/**
 * Created by ypoltavs on 5/17/14.
 */
public class XMLMain {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        File file = new File("Car.xml");
        XMLParser parser = new XMLParser(file);

        //parser.parse();

        //parser.printAttributes();

        parser.printValues();

        //parser.createXML();
    }
}
