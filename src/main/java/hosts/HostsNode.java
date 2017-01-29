package hosts;

import java.util.HashMap;

class HostsNode {
    private String authority;
    private HashMap<String, HostsNode> children;

    HostsNode(String authority) {
        this.authority = authority;
    }

    void addChildren() {
        if(children == null)
            children = new HashMap<>();
    }

    /**
     * Creates a new node if it doesn't already exist, returning its reference.
     * If the node exists, returns it.
     *
     * @param authority
     * @return
     */
    HostsNode addChildren(String authority) {
        if(children == null)
            children = new HashMap<>();

        return children.computeIfAbsent(authority, k -> new HostsNode(authority));
    }

    HashMap<String, HostsNode> getChildren() {
        return children;
    }

    String getAuthority() {
        return authority;
    }
}
