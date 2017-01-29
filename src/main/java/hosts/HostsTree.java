package hosts;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class HostsTree {

    private HostsNode root;

    // TODO(migafgarcia): calculated size is wrong
    private int size;

    // IP regex from http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
    private static final String IP_REGEX =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    // TODO(migafgarcia): work on this
    private static final String URL_REGEX = "(\\w+\\.)*(\\w+)\\.?";

    public HostsTree() {
        this.root = new HostsNode(".");
        this.size = 0;
    }

    public void addUrl(String host) throws MalformedURLException {

        //if (!validateUrl(host))
          //  throw new MalformedURLException(host + " is malformed");

        HostsNode current = this.root;
        String[] splitHost = host.split("\\.");

        for(int i = splitHost.length - 1; i >= 0; i--) {
            current = current.addChildren(splitHost[i]);
            if(current.getChildren() != null && current.getChildren().size() == 0) {
                size++;
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

    public String toString() {
        return toString(root, "\n");
    }

    private String toString(HostsNode current, String currentPath) {

        if(current.getChildren().size() == 0) {
            return currentPath;
        }

        StringBuilder stringBuilder = new StringBuilder();

        Iterator<HostsNode> itr = current.getChildren().values().iterator();

        while(itr.hasNext()) {
            HostsNode i = itr.next();
            stringBuilder.append(toString(i, i.getAuthority() + "." + currentPath));
            //stringBuilder.append('\n');
        }

        return stringBuilder.toString();

    }

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

    public int getSize() {
        return size;
    }

    private static boolean validateUrl(String host) {
        return Pattern.matches(URL_REGEX, host);
    }

    private static boolean validateIp(String ip) {
        return Pattern.matches(IP_REGEX, ip);
    }


}
