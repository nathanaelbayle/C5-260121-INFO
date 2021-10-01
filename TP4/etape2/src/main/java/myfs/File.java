package myfs;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType( 
    name = "File"
)
@XmlRootElement( 
    name = "file", 
    namespace = "http://masterinfo.univlr.fr"
)
public class File extends Node {

    public File() {
    }

    public File(String name) {
        super(name);
    }

    @Override
    public List<Node> getContent() {
        return null;
    }
}
