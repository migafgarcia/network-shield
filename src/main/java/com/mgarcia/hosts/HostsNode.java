package com.mgarcia.hosts;

import java.util.HashMap;

import javax.naming.directory.Attribute;

/**
 * Class representing an authority in the hosts tree
 * Each HostsNode contains child nodes for lower authority domains
 */
class HostsNode {
    private String authority;
    private HashMap<String, HostsNode> children;

    /**
     * Default constructor
     *
     * @param authority this nodes authority
     */
    HostsNode(String authority) {
        this.authority = authority;
    }

    /**
     * Creates an empty child
     */
    void addChildren() {
        if(children == null)
            children = new HashMap<>();
    }

    /**
     * Creates a new child node if it doesn't already exist, returning its reference.
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

    /**
     * Returns this nodes children
     *
     * @return this nodes children
     */
    HashMap<String, HostsNode> getChildren() {
        return children;
    }

    /**
     * Returns this nodes authority
     *
     * @return this nodes authority
     */
    String getAuthority() {
        return authority;
    }


}
