package codesmell.transformer.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class JaxbUtil {

	public static Object getInstance(Class klass, InputStream xmlStream) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(klass);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return jaxbUnmarshaller.unmarshal(xmlStream);
	}

	public static byte[] toXml(Object obj) throws JAXBException {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) JaxbUtil.toXml(obj, new ByteArrayOutputStream());
		if (baos != null) {
			return baos.toByteArray();
		} else {
			return null;
		}
	}

	public static OutputStream toXml(Object obj, OutputStream os) throws JAXBException {
		if (os != null) {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			jaxbMarshaller.marshal(obj, os);
		}
		return os;
	}

	public static Object getInstanceWithValidation(Class klass, InputStream xmlStream, String schemaName) throws JAXBException {
		try {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(klass.getClassLoader().getResource(schemaName));
			JAXBContext jaxbContext = JAXBContext.newInstance(klass);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setSchema(schema);
			return unmarshaller.unmarshal(xmlStream);	
		}
		catch (SAXException e){
			throw new JAXBException(e.getMessage(), e);
		}
	}
}
