package edu.bucknell.binvolved.categories;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.util.Log;


public class CategoryParser {

    private static final String tag = "CategoryParser";
    private static final String FILE_EXTENSION= ".png";

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private final List<Category> list;

    public CategoryParser() {
        this.list = new ArrayList<Category>();
    }

    private String getNodeValue(NamedNodeMap map, String key) {
        String nodeValue = null;
        Node node = map.getNamedItem(key);
        if (node != null) {
            nodeValue = node.getNodeValue();
        }
        return nodeValue;
    }

    public List<Category> getList() {
        return this.list;
    }

    /**
     * Parse XML file containing body part X/Y/Description
     *
     * @param inStream
     */
    public void parse(InputStream inStream) {
        try {
            // TODO: after we must do a cache of this XML!!!!
            this.factory = DocumentBuilderFactory.newInstance();
            this.builder = this.factory.newDocumentBuilder();
            this.builder.isValidating();
            Document doc = this.builder.parse(inStream, null);

            doc.getDocumentElement().normalize();

            NodeList categoryList = doc.getElementsByTagName("country");
            final int length = categoryList.getLength();

            for (int i = 0; i < length; i++) {
                final NamedNodeMap attr = categoryList.item(i).getAttributes();
                final String categoryName = getNodeValue(attr, "name");
                final String categoryAbbr = getNodeValue(attr, "abbreviation");
                final String categoryRegion = getNodeValue(attr, "region");

                // Construct Country object
                Category category = new Category(categoryName,
                        categoryRegion, categoryAbbr + FILE_EXTENSION);

                // Add to list
                this.list.add(category);

                Log.d(tag, category.toString());
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
