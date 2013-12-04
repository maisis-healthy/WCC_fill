/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.maisis.healthy.pps2.persistencerim;

import healthy.Archetype;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * classe para leitura do campo body e escrita no campo data do arquétipo DEMO
 *
 * @author Carlos Cardoso <carlos.cardoso@maisis.pt>
 */
public class XMLfields {

    private Document xmlData;
    private Document xmlBody;

    /**
     * *
     *
     * @param archetypeResponse
     * @return Document
     */
    public Document setXMLfields(Archetype archetypeResponse) {
        try {
            xmlBody = DocumentHelper.parseText(archetypeResponse.getBody());
            //unidades do peso definidas no body
            List<Node> nodes = xmlBody.selectNodes("//ns:attribute/ns:item/@units");
            List<String> units = new ArrayList<>(0);
            for (Node node : nodes) {
                String nodeValue = node.getStringValue();
                units.add(nodeValue.toString());
            }
            //lista que foi definida no body
            List<Element> nodesList = xmlBody.selectNodes("//ns:attribute[@id='at0009']/ns:property/codeList");
            List<String> fields = new ArrayList<>(0);
            for (Element node : nodesList) {
                String nodeData = node.getStringValue();
                fields.add(nodeData.toString());
            }

            //escrever no campo data Peso
            xmlData = DocumentHelper.parseText(archetypeResponse.getData());
            //at0004
            Node peso = xmlData.selectSingleNode("//ns:at0004/ns:magnitude");
            peso.setText("65");
            Node pesoUnidades = xmlData.selectSingleNode("//ns:at0004/ns:units");
            pesoUnidades.setText(units.get(1)); // lb

            Node coment = xmlData.selectSingleNode("//ns:at0024/ns:value");
            coment.setText("sadasdasd");
            Node erro = xmlData.selectSingleNode("//ns:at0025/ns:value");
            erro.setText("sdsadadad");

            Node valueChoose = xmlData.selectSingleNode("//ns:at0009/ns:definingCode/ns:codeString");
            valueChoose.setText(fields.get(0)); //Lightly clothed/underwear

            System.out.println("dataModified: " + xmlData.asXML());
            return xmlData;
        } catch (DocumentException ex) {
            Logger.getLogger(XMLfields.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Document getXmlData() {
        return xmlData;
    }
}
