package myfs;

public class Essai {
    public static void main(String[] args) {
        // create a (small) file system
        FileSystem myFs = new FileSystem();
        Directory rootDir = new Directory("root");
        myFs.setRootDir(rootDir);
        File aFile = new File("foo.txt");
        rootDir.getContent().add(aFile);
        Directory subDir = new Directory("subDirectory");
        Directory subSubDir = new Directory("subSubDirectory");
        rootDir.getContent().add(subDir);
        File anotherFile = new File("bar.txt");
        subDir.getContent().add(anotherFile);
        subDir.getContent().add(subSubDir);
        File anotherFile2 = new File("bar2.txt");
        subDir.getContent().add(anotherFile2);
    }
}