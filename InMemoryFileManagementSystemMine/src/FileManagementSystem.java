import java.util.*;
public class FileManagementSystem {
    private FileNode root;
    private List<FileNode> deletedNodes;
    public FileManagementSystem() {
        root = new FileNode("root", true, null);
        deletedNodes = new ArrayList<>();
    }

    public void create(String path, boolean isDirectory) {
        String[] parts = path.split("/"); //documents/images -> [documents, images]
        FileNode current = root;

        for (int i = 0; i < parts.length - 1; i++) {
            if (!current.getChildren().containsKey(parts[i])) {
                System.out.println("Parent Directory does not exists!");
                return;
            }
            current = current.getChildren().get(parts[i]);





        }

        String name = parts[parts.length - 1];
        if (current.getChildren().containsKey(name)) {
            System.out.println("File / Directory already exists!");
            return;
        }
        FileNode newNode = new FileNode(name, isDirectory, current);
        current.getChildren().put(name, newNode);
        System.out.println((isDirectory? "Directory" : "File") + " created Successfully" );


    }

    public void list (String path) {
        FileNode node = findNode(path);
        if (node == null) {
            System.out.println("Path not found!");
            return;
        }
        printTree(node, 0);
    }

    private void printTree(FileNode node, int level) {
        if (node.isDeleted()) return;

        String indent = " ".repeat(level);
        System.out.println(indent + (node.isDirectory() ? "[DIR] " : "[FILE] "  ) + node.getName());

        if (node.isDirectory()) {
            for (FileNode child: node.getChildren().values()) {
                printTree(child, level + 1);
            }
        }
    }

    public void updateContent(String path, String content) {
        FileNode node = findNode(path);
        if (node == null || node.isDirectory()) {
            System.out.println("File Not Found");
            return;
        }
        node.setContent(content);
        System.out.println("Content Updated Successfully");
    }

    public void rename(String path, String newName) {
        FileNode node = findNode(path);
        if (node == null) {
            System.out.println("Path Not found!");
            return;
        }

        FileNode parent = node.getParent();
        parent.getChildren().remove(node.getName());
        node.setName(newName);
        parent.getChildren().put(newName, node);
        System.out.println("Renamed Successfully");
    }

    public void delete(String path) {
        FileNode node = findNode(path);
        if (node == null) {
            System.out.println("Path Not found");
            return;
        }

        node.setIsDeleted(true);
        deletedNodes.add(node);
        node.getParent().getChildren().remove(node.getName());
        System.out.println("Deleted Successfully");
    }

    public void restore(String name) {
        for (FileNode node : deletedNodes) {
            // 1. First, find the matching file
            if (node.getName().equals(name)) {

                // 2. NOW check if this specific file's parent is alive
                if (node.getParent().isDeleted()) {
                    System.out.println("Cannot restore file. Parent directory is also deleted!");
                    return; // Stop. We found the file but can't restore it.
                }

                // 3. If parent is safe, proceed with restore
                node.setIsDeleted(false);
                node.getParent().getChildren().put(name, node);

                // 4. Safe to remove because we return immediately
                deletedNodes.remove(node);
                System.out.println("Restored successfully");
                return;
            }
        }

        System.out.println("Deleted item not found");
    }

    private FileNode findNode(String path) {
        if (path.equals("root")) return root;

        FileNode current = root;
        String[] parts = path.split("/");

        for (String part: parts) {
            if (!current.getChildren().containsKey(part)) return null;

            current = current.getChildren().get(part);
        }

        return current;
    }

    
    
}
