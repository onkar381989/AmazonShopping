package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadData {
	
	public static Hashtable<String, String> getPropertiesFileData(String filePath) throws Exception {
		Hashtable<String, String> data = new Hashtable<String, String>();
		try {
			File file = new File(filePath);
			if(file.exists()) {
				Properties loadedProperties;
				loadedProperties = new Properties();
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
				loadedProperties.load(in);
				in.close();

				Enumeration<Object> em = loadedProperties.keys();
				while (em.hasMoreElements()) {
					String key = em.nextElement().toString().trim();
					String value = loadedProperties.get(key).toString().trim();
					data.put(key.replaceAll("\\s+", ""), value);
				}
			} 
		} catch (Exception e) {
			throw new Exception("Exception in getPropertiesFileData() :: " + e.getMessage());
		}
		return data;
	}
	
	// Read element repository
	public static Hashtable<String, Hashtable<String, String>> getElementParamsByTitle(String absoluteFilePath) throws Exception {
		Hashtable<String, Hashtable<String, String>> elementParams = new Hashtable<String, Hashtable<String, String>>();
		try {
			File file = new File(absoluteFilePath);
			if (file.isFile()) {
				DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = documentBuilder.parse(file);
				NodeList nodeList = doc.getElementsByTagName("Element");
				for (int node = 0; node < nodeList.getLength(); node++) {
					Hashtable<String, String> childElements = new Hashtable<String, String>();
					Element param = (Element) nodeList.item(node);
					if (!param.getAttribute("title").toString().equals("")) {
						for (Node childNode = param.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {
								childElements.put(childNode.getNodeName(), childNode.getTextContent());
							}
						}
						elementParams.put(param.getAttribute("title").toString(), childElements);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Exception in XmlReaderWriter getElementParamsByTitle() :: " + e.getMessage());
		}
		return elementParams;
	}
}
