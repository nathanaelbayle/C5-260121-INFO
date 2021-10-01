package myfs;

import java.util.List;

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
