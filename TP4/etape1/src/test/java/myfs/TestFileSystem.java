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

    private static myfsXML.ObjectFactory of;
    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;
    private static SchemaFactory factory;
    private static Schema fsSchema;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        try {
            // configure marshaller and unmarshaller
            JAXBContext context = JAXBContext.newInstance("myfsXML"); // package name
            marshaller = context.createMarshaller();
            unmarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // optional
            // create object factory
            of = new myfsXML.ObjectFactory();
            // schema factory
            factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            fsSchema = factory.newSchema(new File("src/main/xsd/fs.xsd"));
            validator = fsSchema.newValidator();
        } catch (JAXBException jaxbe) {
            // error
            fail(jaxbe.getMessage());
        } catch (SAXException se) {
            // error
            fail(se.getMessage());
        }
    }

    private static myfsXML.Directory convertDirToXMLDir(myfs.Directory dir) {
        myfsXML.Directory xmlDir = of.createDirectory();
        xmlDir.setName(dir.getName());
        List<myfsXML.Node> xmlContent = xmlDir.getDirectoryOrFile();
        for (myfs.Node n : dir.getContent()) {
            if (n.getContent() != null) {
                // the node n is a directory
                myfsXML.Directory newXmlDir = convertDirToXMLDir((myfs.Directory) n);
                xmlContent.add(newXmlDir);
            } else {
                // if getContent returns null then it's a file
                myfsXML.File newXmlFile = new myfsXML.File();
                newXmlFile.setName(n.getName());
                xmlContent.add(newXmlFile);
            }
        }
        return xmlDir;
    }

    // bridge between model and Java serialization classes
    private static myfsXML.FileSystem convertFSToXMLFS(myfs.FileSystem fs) {
        myfsXML.FileSystem xmlFs = of.createFileSystem();
        myfs.Directory rootDir = fs.getRootDir();
        assertTrue(rootDir != null, "Empty file system");
        myfsXML.Directory xmlRootDir = convertDirToXMLDir(rootDir);
        xmlFs.setDirectory(xmlRootDir);
        return xmlFs;
    }

    // compare two XML directories (very basic version)
    // for an advanced version see xmlunit
    private static void compareXmlDirectory(myfsXML.Directory d1, myfsXML.Directory d2) {
        // compare directory name
        assertTrue(d1.getName().equals(d2.getName()), "The directories " + d1.getName() + " and " + d2.getName() + " don't have the same name");
        // compare number of childrens
        assertTrue(d1.getDirectoryOrFile().size() == d2.getDirectoryOrFile().size(),
                "The directories don't have the same size: \n" +
                        d1.getName() + ": " + d1.getDirectoryOrFile().size() + "\n" +
                        d2.getName() + ": " + d2.getDirectoryOrFile().size() + "\n");
        // compare childrens
        for (int i = 0; i < d1.getDirectoryOrFile().size(); i++) {
            Object o1 = d1.getDirectoryOrFile().get(i);
            Object o2 = d2.getDirectoryOrFile().get(i);
            // recursive call if there are two directories
            if (o1 instanceof myfsXML.Directory && o2 instanceof myfsXML.Directory) {
                compareXmlDirectory((myfsXML.Directory) o1, (myfsXML.Directory) o2);
            } else if (o1 instanceof myfsXML.File && o2 instanceof myfsXML.File) {
                // if there are two files then the names must be the same
                myfsXML.File f1 = (myfsXML.File) o1;
                myfsXML.File f2 = (myfsXML.File) o2;
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
            validator.validate(new StreamSource(new File("src/test/xml/fs_bad_emptyfs.xml")));;
            fail("The schema is incorrect : empty file system isn't allowed");
        } catch (SAXException se) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(2)
    public void directoryWOName() {
        try {
            validator.validate(new StreamSource(new File("src/test/xml/fs_bad_dir_att_name.xml")));;
            fail("The schema is incorrect : directory without name isn't allowed");
        } catch (SAXException se) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(3)
    public void fileWOName() {
        try {
            validator.validate(new StreamSource(new File("src/test/xml/fs_bad_file_att_name.xml")));;
            fail("The schema is incorrect : file without name isn't allowed");
        } catch (SAXException se) {
            // OK
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(4)
    public void schemaValidation() {
        // check the schema against the XML file system reference file
        try {
            validator.validate(new StreamSource(new File("src/test/xml/fs_good_reference.xml")));;
        } catch (SAXException se) {
            fail("The schema is incorrect against XML file system reference file: " + se.getMessage());
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    @Test
    @Order(5)
    public void TestFSOK() {
        try {
            // create a (small) valid file system
            myfs.FileSystem myFs = new myfs.FileSystem();
            myfs.Directory rootDir = new myfs.Directory("root");
            myFs.setRootDir(rootDir);
            myfs.File aFile = new myfs.File("foo.txt");
            rootDir.getContent().add(aFile);
            myfs.Directory subDir = new myfs.Directory("subDirectory");
            myfs.Directory subSubDir = new myfs.Directory("subSubDirectory");
            rootDir.getContent().add(subDir);
            myfs.File anotherFile = new myfs.File("bar.txt");
            subDir.getContent().add(anotherFile);
            subDir.getContent().add(subSubDir);
            myfs.File anotherFile2 = new myfs.File("bar2.txt");
            subDir.getContent().add(anotherFile2);

            // transform POJO into XML object
            myfsXML.FileSystem xmlFs = convertFSToXMLFS(myFs);

            // marshalling file system to XML file
            marshaller.marshal(new JAXBElement(
                            new QName("http://masterinfo.univlr.fr", "fs"),
                            myfsXML.FileSystem.class, xmlFs),
                    new FileOutputStream("target/fs.xml"));

            // unmarshalling XML reference file
            JAXBElement<myfsXML.FileSystem> root = unmarshaller.unmarshal(
                    new StreamSource(new File("src/test/xml/fs_good_reference.xml")), myfsXML.FileSystem.class);
            myfsXML.FileSystem xmlFsRef = root.getValue();

            // compare POJO FS coming from two XML FS (the structure and the content must be the same)
            compareXmlDirectory(xmlFsRef.getDirectory(), xmlFs.getDirectory());

        } catch (JAXBException jaxbe) {
            // error
            fail(jaxbe.getMessage());
        } catch (IOException ioe) {
            // error
            fail(ioe.getMessage());
        }
    }

}

