package myfs;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType( 
    name= "Node"
)
public abstract class Node {

    @XmlTransient
    private String name;

    @XmlTransient
    public abstract List<Node> getContent();

    public Node() { }

    public Node(String name) {
        this.name = name;
    }

    @XmlAttribute( 
        name = "name",
        required = true,
        namespace = "http://masterinfo.univlr.fr"
    )
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
