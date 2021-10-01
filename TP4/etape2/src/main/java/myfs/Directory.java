package myfs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "Directory")
@XmlRootElement(name = "directory", namespace = "http://masterinfo.univlr.fr")
public class Directory extends Node
{

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
