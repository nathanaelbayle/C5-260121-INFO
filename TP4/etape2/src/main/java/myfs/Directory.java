package myfs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "Directory")
@XmlRootElement(name = "Directory", namespace = "http://masterinfo.univlr.fr")
@XmlAccessorType(XmlAccessType.FIELD)

public class Directory extends Node
{
    /**
     * 
     * Prevents the mapping of a JavaBean property/type to XML representation.
     * When placed on a class, it indicates that the class shouldn’t be mapped to XML by itself. 
     * Properties on such class will be mapped to XML along with its derived classes as if the class is inlined.
     * 
     */
    @XmlTransient
    private List<Node> content;

    public Directory() {
        this.content = new ArrayList<>();
    }

    public Directory(String name) {
        super(name);
        this.content = new ArrayList<>();
    }

    @XmlElementRef
    public List<Node> getContent() {
        return this.content;
    }

    public void setContent(List<Node> content) {
        this.content = content;
    }
}
