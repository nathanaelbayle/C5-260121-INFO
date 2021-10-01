package myfs;

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