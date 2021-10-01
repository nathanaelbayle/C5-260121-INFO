package myfs;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "FileSystem")
@XmlRootElement(name = "fs", namespace = "http://masterinfo.univlr.fr")
public class FileSystem {

    @XmlTransient
    private Directory rootDir;

    public FileSystem() {
    }

    @XmlElement( name = "directory", namespace = "http://masterinfo.univlr.fr", required = true)
    public void setRootDir(Directory rootDir) {
        this.rootDir = rootDir;
    }
    
    public Directory getRootDir() {
        return this.rootDir;
    }

}