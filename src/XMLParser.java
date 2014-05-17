import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ypoltavs on 5/17/14.
 */
public class XMLParser {
    File file = null;
    Document doc = null;
    DocumentBuilder builder = null;
    DocumentBuilderFactory factory = null;

    public XMLParser(File file) throws ParserConfigurationException, SAXException, IOException {
        this.file = file;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        doc = builder.parse(file);
    }

    public void parse() {
        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes();

        System.out.println(root.getNodeName() + ":");

        NamedNodeMap attributes = root.getAttributes();
        for ( int l = 0; l < attributes.getLength(); l++ ) {
            Node attribute = attributes.item(l);
            String name = attribute.getNodeName();
            String value = attribute.getNodeValue();
            System.out.println("Attributes: " + name + ": " + value);
        }

        for ( int i = 0; i < children.getLength(); i++ ) {
            Node child = children.item(i);
            String str = child.getNodeName();

            if ( str.equals("#text") ) {
                continue;
            }

            if ( child.getChildNodes().getLength() <= 1 ) {
                System.out.println(child.getNodeName() + ": " + child.getTextContent());
            } else {
                NodeList parent = child.getChildNodes();
                System.out.println(child.getNodeName() + ":");
                for ( int j = 0; j < parent.getLength(); j++ ) {
                    Node childP = parent.item(j);
                    String str1 = childP.getNodeName();

                    if ( str1.equals("#text") ) {
                        continue;
                    }

                    System.out.println(childP.getNodeName() + ": " + childP.getTextContent());
                }
            }
        }
    }

    public void printAttributes() {
        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes();

        System.out.println(root.getNodeName() + ":");

        NamedNodeMap attributes = root.getAttributes();
        for ( int l = 0; l <attributes.getLength(); l++ ) {
            Node attribute = attributes.item(l);
            String name = attribute.getNodeName();
            String value = attribute.getNodeValue();
            System.out.println(name + ": " + value);
        }

        for ( int i = 0; i < children.getLength(); i++ ) {
            Node child = children.item(i);
            NamedNodeMap attributesP = child.getAttributes();
            if ( attributesP != null ) {
                //System.out.println(child.getNodeName() + ":");
                for ( int k = 0; k < attributesP.getLength(); k++ ) {
                    Node attributeP = attributesP.item(k);
                    String name = attributeP.getNodeName();
                    String value = attributeP.getNodeValue();
                    System.out.println(name + ": " + value);
                }
            }
        }
    }

    public void printValues() {
        Element root = doc.getDocumentElement();
        NodeList children = root.getChildNodes();

        System.out.println(root.getNodeName() + ":");

        for ( int i = 0; i < children.getLength(); i++ ) {
            Node child = children.item(i);
            if ( child instanceof Element ) {
                Element childElement = (Element) child;
                Text textNode = (Text)childElement.getFirstChild();
                String text = textNode.getData().trim();
                String name = childElement.getNodeName().toString();
                System.out.println(name + ": " + text);
            }

            if ( child.getChildNodes().getLength() > 1 ) {
                System.out.println("Boo");
                NodeList parent = child.getChildNodes();
                for ( int j = 0; j < parent.getLength(); j++ ) {
                    System.out.println("Boo1");
                    Node childP = parent.item(i);
                    if ( childP instanceof Element ) {
                        Element childElement = (Element) childP;
                        Text textNode = (Text)childElement.getFirstChild();
                        String text = textNode.getData().trim();
                        String name = childElement.getNodeName().toString();
                        System.out.println(name + ": " + text);
                    }
                }
            }
        }
    }

    public void createXML() throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        File file1 = new File("created.xml");
        Document document = builder.newDocument();
        Element rootElement = document.createElement("car");
        Element childElement1 = document.createElement("doors");
        Text textNode1 = document.createTextNode("2");
        childElement1.appendChild(textNode1);
        Element color = document.createElement("color");
        Text colorText = document.createTextNode("Red");
        color.appendChild(colorText);
        Element owner = document.createElement("owner");
        Element name = document.createElement("name");
        Text nameText = document.createTextNode("Ivan");
        name.appendChild(nameText);
        Element childElement5 = document.createElement("license");
        Text licenseText = document.createTextNode("B");
        childElement5.appendChild(licenseText);
        owner.appendChild(name);
        owner.appendChild(childElement5);
        rootElement.appendChild(childElement1);
        rootElement.appendChild(color);
        rootElement.appendChild(owner);
        document.appendChild(rootElement);
        rootElement.setAttribute("name", "Ferrari");
        owner.setAttribute("country", "Ukraine");
        owner.setAttribute("city", "Kiev");

        Transformer optimusPrime = TransformerFactory.newInstance().newTransformer();
        optimusPrime.transform(new DOMSource(document), new StreamResult(new FileOutputStream(file1)));
    }
}
