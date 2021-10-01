package myfs;

import java.util.ArrayList;
import java.util.List;


public class Directory extends Node {
    private List<Node> content;

    public Directory() {
        this.content = new ArrayList<>();
    }

    public Directory(String name) {
        super(name);
        this.content = new ArrayList<>();
    }

    public List<Node> getContent() {
        return content;
    }

    public void setContent(List<Node> content) {
        this.content = content;
    }
}
