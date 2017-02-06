package com.mgarcia.hosts;

import java.net.MalformedURLException;
import java.util.regex.Pattern;

public class HostsTree {

    private HostsNode root;
    private int size;

    // IP regex from http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
    private static final String IP_REGEX =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    // TODO(migafgarcia): work on this
    //private static final String URL_REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
    private static final String URL_REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

    public HostsTree() {
        this.root = new HostsNode(".");
        this.size = -1;
    }

    /**
     * Adds a host to this tree
     *
     * @param host the host to add
     * @throws MalformedURLException
     */
    public void addUrl(String host) throws MalformedURLException {

        // TODO(migafgarcia): improve regex and parser
        //if (!validateUrl(host))
          //  throw new MalformedURLException(host + " is malformed");

        HostsNode current = this.root;
        String[] splitHost = host.split("\\.");

        for(int i = splitHost.length - 1; i >= 0; i--) {
            current = current.addChildren(splitHost[i]);
            if(current.getChildren() != null && current.getChildren().size() == 0) {
                break;
            }
        }

        /*
         * A shorter authority was added:
         * fgh.com was added when asd.fgh.com
         * In that case only fgh.com remains
         */


        if(current.getChildren() == null)
            current.addChildren();
        else
            current.getChildren().clear();

    }

    /**
     * Returns the number of leafs in the tree
     * @return the number of leafs in the tree
     */
    public int size() {
        this.size = size(root, 0);
        return size;
    }

    /**
     * Returns the number of leafs under current
     *
     * @param current the current node
     * @param currentSize the current size
     * @return number of leafs under current
     */
    private int size(HostsNode current, int currentSize) {
        if(current.getChildren().size() == 0) {
            return currentSize+1;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (HostsNode i : current.getChildren().values())
            currentSize = size(i, currentSize);

        return currentSize;
    }


    /**
     * Returns the string representation of the tree
     *
     * @return the string representation of the tree
     */
    public String toString() {
        return toString(root, "\n");
    }

    /**
     * Returns the string representation of all nodes under current
     *
     * @param current the current node
     * @param currentPath the current path
     * @return
     */
    private String toString(HostsNode current, String currentPath) {

        if(current.getChildren().size() == 0) {
            return currentPath;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (HostsNode i : current.getChildren().values()) {
            stringBuilder.append(toString(i, i.getAuthority() + "." + currentPath));
            //stringBuilder.append('\n');
        }

        return stringBuilder.toString();

    }

    /**
     * Checks whether this host is blocked, i.e., check if theres a path from the root to a leaf
     * along the provided hosts
     *
     * @param host the host to check
     * @return true if host is blocked, false otherwise
     */
    public boolean isBlocked(String host) {

        String[] splitHost = host.split("\\.");

        HostsNode current = root;


        for(int i = splitHost.length - 1; i >= 0; i--) {
            current = current.getChildren().get(splitHost[i]);
            if(current == null)
                return false;
            else if(current.getChildren() == null || current.getChildren().size() == 0) {
                return true;
            }
        }
        return false;


    }

    /**
     * Checks if host matches URL_REGEX
     *
     * @param host the host to check
     * @return true if it matches, false otherwise
     */
    private static boolean validateUrl(String host) {
        return Pattern.matches(URL_REGEX, host);
    }

    /**
     * Checks if ip matches IP_REGEX
     *
     * @param ip the ip to check
     * @return true if it matches, false otherwise
     */
    private static boolean validateIp(String ip) {
        return Pattern.matches(IP_REGEX, ip);
    }

}
