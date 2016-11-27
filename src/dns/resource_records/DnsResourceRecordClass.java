package dns.resource_records;

public enum DnsResourceRecordClass {

    INTERNET {
        @Override
        public short toCode() {
            return 1;
        }
    },

    UNDEFINED {
        @Override
        public short toCode() {
            return -1;
        }
    };

    public abstract short toCode();

    public static DnsResourceRecordClass fromCode(int code) {
        switch(code) {
            case 1:
                return INTERNET;
            default:
                return UNDEFINED;
        }
    }
}
