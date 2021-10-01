package myfs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFileSystem {

    private static SchemaFactory factory;
    private static Schema fsSchema;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        try {
            // schema factory
            factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            fsSchema = factory.newSchema(new File("src/test/resources/xsd/fs_root_sl.xsd"));
            validator = fsSchema.newValidator();
        } catch (SAXException se) {
            // error
            fail(se.getMessage());
        }
    }

    @Test
    @Order(1)
    public void symbolicLink1() {
        try {
            validator.validate(new StreamSource(new File("src/test/resources/xml/fs_bad_sl_1.xml")));;
            fail("The schema is incorrect : each file system node must have an id");
        } catch (SAXException se) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(2)
    public void symbolicLink2() {
        try {
            validator.validate(new StreamSource(new File("src/test/resources/xml/fs_bad_sl_2.xml")));;
            fail("The schema is incorrect : a symbolic link must reference an existing node");
        } catch (SAXException se) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(3)
    public void fsRootDirectory() {
        try {
            validator.validate(new StreamSource(new File("src/test/resources/xml/fs_bad_root.xml")));;
            fail("The schema is incorrect : the root directory of a file system must be name 'root'");
        } catch (SAXException se) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(4)
    public void fsOK() {
        try {
            validator.validate(new StreamSource(new File("src/test/resources/xml/fs_good_reference.xml")));;
        } catch (SAXException se) {
            fail("The schema is incorrect: " + se.getMessage());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

}

