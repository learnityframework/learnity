package coreadministrationv2.sysmgmt.xml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class XMLGenerator {

	/**
	 * To create the xml from a bean
	 * 
	 * @param dto
	 *            whose corresponding xml is required to create(It must
	 *            implement Generic dto)
	 * @return XML in form of OMelement
	 */
	public static byte[] getXmlDoc(GenericDto dto) {
		byte[] sendingBytes = null;
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try {

			final JAXBContext jaxbContext = JAXBContext.newInstance(dto
					.getClass());

			StringWriter writer = new StringWriter();

			jaxbContext.createMarshaller().marshal(dto, writer);
			Document dom = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(new ByteArrayInputStream(writer.toString()
							.getBytes()));
			
			final Comment comment = dom.createComment("XML is generated on "+new Date());
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
