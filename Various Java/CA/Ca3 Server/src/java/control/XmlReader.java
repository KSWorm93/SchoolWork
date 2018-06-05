package control;

import entity.CurrencyRates;
import facades.UserFacade;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;

public class XmlReader extends DefaultHandler implements Runnable {

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Document (Sax-event)");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End Document (Sax-event)");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        UserFacade facade = new UserFacade();
        EntityManager em = facade.getEntityManager();

        System.out.print("Element: " + localName + ": ");
        for (int i = 0; i < attributes.getLength(); i++) {

            if (attributes.getQName(i).equals("code") && attributes.getQName(i + 1).equals("desc") && attributes.getQName(i + 2).equals("rate")) {
                String code = attributes.getValue(i);
                String desc = attributes.getValue(i + 1);
                String rate = attributes.getValue(i + 2);

                Date dato = new Date();
                java.sql.Date sqlDato = new java.sql.Date(dato.getTime());

                Double realRate;

                if (rate.equals("-")) {
                    realRate = 0D;
                } else {
                    realRate = Double.parseDouble(rate);
                }

                CurrencyRates currencyRate = new CurrencyRates(sqlDato, code, desc, realRate);

                em.getTransaction().begin();
                em.persist(currencyRate);
                em.getTransaction().commit();
            }
        }
    }

    @Override
    public void run() {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            xr.setContentHandler(new XmlReader());
            URL url = new URL("http://www.nationalbanken.dk/_vti_bin/DN/DataService.svc/CurrencyRatesXML?lang=en");
            xr.parse(new InputSource(url.openStream()));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
