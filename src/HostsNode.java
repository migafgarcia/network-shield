import java.util.HashMap;

public class HostsNode {
    private String authority;
    private HashMap<String, HostsNode> children;

    public HostsNode(String authority) {
        this.authority = authority;
        this.children = new HashMap<>();
    }

    /**
     * Creates a new node if it doesn't already exist, returning its reference.
     * If the node exists, returns it.
     *
     * @param authority
     * @return
     */
    public HostsNode addChildren(String authority) {

        HostsNode node = children.get(authority);

        if (node == null) {
            node = new HostsNode(authority);
            children.put(authority, node);
        }

        return node;
    }
}
