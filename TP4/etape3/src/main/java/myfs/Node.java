package myfs;

import java.util.List;

public abstract class Node {
    private String name;

    public abstract List<Node> getContent();

    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
