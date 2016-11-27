import hosts.HostsTree;

import java.net.MalformedURLException;

public class NetworkShield {

    public static void main(String[] args) {
        HostsTree hostsTree = new HostsTree();

        try {
            hostsTree.addUrl("audio2.spotify.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
