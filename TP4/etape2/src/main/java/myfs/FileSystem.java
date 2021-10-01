package myfs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
@XmlType(
    name = "FileSystem"
)
@XmlRootElement(
    name = "fs",
    namespace = "http://masterinfo.univlr.fr"
)

public class FileSystem {

    @XmlTransient
    private Directory rootDir;

    public FileSystem() {
    }

    public void setRootDir(Directory rootDir) {
        this.rootDir = rootDir;
    }

    @XmlElement(
        name = "directory",
        required = true,
        namespace = "http://masterinfo.univlr.fr"
    )
    public Directory getRootDir() {
        return this.rootDir;
    }

}