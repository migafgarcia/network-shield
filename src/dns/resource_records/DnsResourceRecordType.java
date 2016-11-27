package dns.resource_records;

public enum DnsResourceRecordType {

    A {
        @Override
        public short toCode() {
            return 1;
        }
    },

    AAAA {
        @Override
        public short toCode() {
            return 28;
        }
    },

    MX {
        @Override
        public short toCode() {
            return 15;
        }
    },

    SOA {
        @Override
        public short toCode() {
            return 6;
        }
    },

    CNAME {
        @Override
        public short toCode() {
            return 5;
        }
    },

    UNDEFINED{
        @Override
        public short toCode() {
            return -1;
        }
    };

    public abstract short toCode();

    public static DnsResourceRecordType fromCode(short code) {
        switch(code) {
            case 1:
                return A;
            case 28:
                return AAAA;
            case 15:
                return MX;
            case 6:
                return SOA;
            case 5:
                return CNAME;
            default:
                return UNDEFINED;
        }
    }


}
