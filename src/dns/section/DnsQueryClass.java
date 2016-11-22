package dns.section;

public enum DnsQueryClass {

    INTERNET {
        @Override
        int toCode() {
            return 1;
        }
    };

    abstract int toCode();
}
