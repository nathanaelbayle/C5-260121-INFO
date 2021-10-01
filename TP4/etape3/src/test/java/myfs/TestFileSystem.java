package myfs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestFileSystem {

    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;
    private static SchemaFactory factory;
    private static Schema fsSchema;

    @BeforeAll
    public static void init() {
        try {
            // Bootstrapping from EclipseLink XML Bindings
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream iStream = classLoader.getResourceAsStream("myfs/xml-bindings.xml");
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, new StreamSource(iStream));

            // schema factory
            factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            fsSchema = factory.newSchema(new java.io.File("src/test/resources/xsd/fs.xsd"));

            // configure marshaller and unmarshaller
            JAXBContext jaxbContext = JAXBContext.newInstance("myfs", classLoader, properties);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setSchema(fsSchema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            unmarshaller = jaxbContext.createUnmarshaller();

        } catch (JAXBException jaxbe) {
            // error
            fail(jaxbe.getMessage());
        } catch (SAXException se) {
            // error
            fail(se.getMessage());
        }
    }

    // compare two directories
    private static void compareDirectories(Directory d1, Directory d2) {
        // compare directory name
        assertTrue(d1.getName().equals(d2.getName()), "The directories " + d1.getName() + " and " + d2.getName() + " don't have the same name");
        // compare number of childrens
        assertTrue(d1.getContent().size() == d2.getContent().size(),
                "The directories don't have the same size: \n" +
                        d1.getName() + ": " + d1.getContent().size() + "\n" +
                        d2.getName() + ": " + d2.getContent().size() + "\n");
        // compare childrens
        for (int i = 0; i < d1.getContent().size(); i++) {
            Node n1 = d1.getContent().get(i);
            Node n2 = d2.getContent().get(i);
            // recursive call if there are two directories
            if (n1 instanceof Directory && n2 instanceof Directory) {
                compareDirectories((Directory) n1, (Directory) n2);
            } else if (n1 instanceof myfs.File && n2 instanceof myfs.File) {
                // if there are two files then the names must be the same
                myfs.File f1 = (myfs.File) n1;
                myfs.File f2 = (myfs.File) n2;
                assertTrue(f1.getName().equals(f2.getName()),
                        "The files " + f1.getName() + " and " + f2.getName() + " don't have the same name");
            } else { // error
                fail("The two directories " + d1.getName() + " don't have the same content");
            }
        }
    }

    @Test
    @Order(1)
    public void emptyFS() {
        try {
            // create a empty file system
            FileSystem aFs = new FileSystem();
            marshaller.marshal(aFs, new FileOutputStream("target/fs.xml"));
            fail("Empty file system isn't allowed");
        } catch (JAXBException je) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(2)
    public void directoryWOName() {
        try {
            // create a directory without a name
            FileSystem aFs = new FileSystem();
            Directory rootDir = new Directory();
            aFs.setRootDir(rootDir);
            marshaller.marshal(aFs, new FileOutputStream("target/fs.xml"));
            fail("Directory without name isn't allowed");
        } catch (JAXBException je) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(3)
    public void fileWOName() {
        try {
            // create a directory without a name
            FileSystem aFs = new FileSystem();
            Directory rootDir = new Directory();
            aFs.setRootDir(rootDir);
            myfs.File aFile = new myfs.File();
            rootDir.getContent().add(aFile);
            marshaller.marshal(aFs, new FileOutputStream("target/fs.xml"));
            fail("File without name isn't allowed");
        } catch (JAXBException je) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(4)
    public void checkXMLFSTest() {
        // create a (small) file system
        FileSystem aFs = new FileSystem();
        Directory rootDir = new Directory("root");
        aFs.setRootDir(rootDir);
        myfs.File aFile = new myfs.File("foo.txt");
        rootDir.getContent().add(aFile);
        Directory subDir = new Directory("subDirectory");
        Directory subSubDir = new Directory("subSubDirectory");
        rootDir.getContent().add(subDir);
        myfs.File anotherFile = new myfs.File("bar.txt");
        subDir.getContent().add(anotherFile);
        subDir.getContent().add(subSubDir);
        myfs.File anotherFile2 = new myfs.File("bar2.txt");
        subDir.getContent().add(anotherFile2);

        try {
            // marshalling file system to XML file
            marshaller.marshal(aFs, new FileOutputStream("target/smallfs.xml"));

            // unmarshalling XML reference file
            FileSystem refFs = (FileSystem) unmarshaller.unmarshal(new java.io.File("src/test/resources/xml/fs_good_reference.xml"));

            // compare two file systems (the structure must be the same)
            compareDirectories(aFs.getRootDir(), refFs.getRootDir());

        } catch (JAXBException jaxbe) {
            // error
            fail(jaxbe.getMessage());
        } catch (IOException ioe) {
            // error
            fail(ioe.getMessage());
        }
    }

}

