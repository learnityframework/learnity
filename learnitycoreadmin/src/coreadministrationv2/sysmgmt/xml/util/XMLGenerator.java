package coreadministrationv2.sysmgmt.xml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XMLGenerator {

	public static byte[] generateRoleXmlDoc(ServletContext servletContext, GenericDto dto) {
		return getXmlDoc(servletContext, dto, XSDPath.ROLE_XSD.getSchemaPath());
	}
	
	public static byte[] generateManifestXmlDoc(ServletContext servletContext, GenericDto dto) {
		return getXmlDoc(servletContext, dto, XSDPath.MANIFEST_XSD.getSchemaPath());
	}

	/**
	 * To create the xml from a bean
	 * 
	 * @param dto
	 *            whose corresponding xml is required to create(It must
	 *            implement Generic dto)
	 * @return XML in form of OMelement
	 */
	private static byte[] getXmlDoc(ServletContext servletContext, GenericDto dto, String relativeXsdPath) {
		byte[] sendingBytes = null;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {

			final JAXBContext jaxbContext = JAXBContext.newInstance(dto.getClass());

			StringWriter writer = new StringWriter();

			String xsdPath = servletContext.getRealPath(relativeXsdPath);
			File schemaFile = new File(xsdPath);

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			Schema schema = schemaFactory.newSchema(schemaFile);

			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setSchema(schema);
			marshaller.marshal(dto, writer);

			// jaxbContext.createMarshaller().marshal(dto, writer);
			Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(writer.toString().getBytes()));

			final Comment comment = dom.createComment("XML is generated on " + new Date());
			dom.appendChild(comment);

			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			XMLSerializer serializer = new XMLSerializer(bytes, format);
			serializer.serialize(dom);
			writer.close();
			sendingBytes = bytes.toByteArray();
			bytes.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sendingBytes;

	}
}
