package myfs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "Node")
@XmlRootElement(name = "Node", namespace = "http://masterinfo.univlr.fr")
@XmlSeeAlso({File.class, Directory.class})
public abstract class Node {

    @XmlAttribute( name="name", namespace ="http://masterinfo.univlr.fr", required = true )
    private String name;

    @XmlTransient
    public abstract List<Node> getContent();

    public Node() { }

    public Node(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
