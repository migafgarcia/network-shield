package dns.section;

import dns.*;

public class DnsQuestion {

    private String name;

    private DnsResourceRecord resourceRecord;

    private DnsQueryClass queryClass;


    public static DnsQuestion parseQuestion(byte[] buffer) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDnsQuestion{" +
                "name='" + name + '\'' +
                ", resourceRecord=" + resourceRecord +
                ", queryClass=" + queryClass +
                '}';
    }
}
