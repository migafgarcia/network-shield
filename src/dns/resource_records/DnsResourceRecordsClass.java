package dns.resource_records;

public enum DnsResourceRecordsClass {

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

    public static DnsResourceRecordsClass fromCode(int code) {
        switch(code) {
            case 1:
                return INTERNET;
            default:
                return UNDEFINED;
        }
    }
}
