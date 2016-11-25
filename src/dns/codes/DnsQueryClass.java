package dns.codes;

public enum DnsQueryClass {

    INTERNET {
        @Override
        int toCode() {
            return 1;
        }
    },

    UNDEFINED {
        @Override
        int toCode() {
            return -1;
        }
    };

    abstract int toCode();

    public static DnsQueryClass fromCode(int code) {
        switch(code) {
            case 1:
                return INTERNET;
            default:
                return UNDEFINED;
        }
    }
}
