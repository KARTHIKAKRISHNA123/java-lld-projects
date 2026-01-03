import java.util.*;

class FileNode {
    private String name; //getter and setter
    private boolean isDirectory; //getter
    private String content; // getter and setter
    private Map<String, FileNode> children; //getter
    private FileNode parent; //parent
    private boolean isDeleted; //getter and setter

    public FileNode (String name, boolean isDirectory, FileNode parent) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.content = "";
        this.children = new HashMap<>();
        this.parent = parent;
        this.isDeleted = false;

    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;

    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, FileNode> getChildren() {
        return children;
    }

    public FileNode getParent() {
        return parent;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

