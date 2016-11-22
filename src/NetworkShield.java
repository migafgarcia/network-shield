import java.net.MalformedURLException;
import java.net.URL;

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
