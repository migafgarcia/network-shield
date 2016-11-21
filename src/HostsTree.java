import java.net.MalformedURLException;
import java.util.regex.Pattern;

public class HostsTree {

    private HostsNode root;

    // IP regex from http://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
    private static final String IP_REGEX =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final String URL_REGEX = "(\\w+\\.)*(\\w+)\\.?";


    public HostsTree() {
        this.root = new HostsNode(".");
    }

    public void addUrl(String host) throws MalformedURLException {

        if (!validateUrl(host))
            throw new MalformedURLException(host + " is malformed");

        HostsNode current = this.root;

        


    }

    private static boolean validateUrl(String host) {
        return Pattern.matches(URL_REGEX, host);
    }

    private static boolean validateIp(String ip) {
        return Pattern.matches(IP_REGEX, ip);
    }


}
