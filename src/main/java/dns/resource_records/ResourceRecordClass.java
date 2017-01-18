package dns.resource_records;

public enum ResourceRecordClass {

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

    public static ResourceRecordClass fromCode(int code) {
        switch(code) {
            case 1:
                return INTERNET;
            default:
                return UNDEFINED;
        }
    }
}
