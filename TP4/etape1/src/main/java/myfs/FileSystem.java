package myfs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileSystem", propOrder = {
    "directory"
})
public class FileSystem {

    private Directory rootDir;

    public FileSystem() {
    }

    public void setRootDir(Directory rootDir) {
        this.rootDir = rootDir;
    }

    public Directory getRootDir() {
        return this.rootDir;
    }

}