package dns;

public enum DnsQueryClass {

    INTERNET {
        @Override
        int toCode() {
            return 1;
        }
    };

    abstract int toCode();
}
