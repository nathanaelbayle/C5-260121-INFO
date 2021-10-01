package myfs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "File")
@XmlRootElement(name = "File", namespace = "http://masterinfo.univlr.fr")
public class File extends Node {

    public File() {
    }

    public File(String name) {
        super(name);
    }

    public List<Node> getContent() {
        return null;
    }
}
