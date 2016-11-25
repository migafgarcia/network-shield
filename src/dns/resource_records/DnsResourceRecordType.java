package dns.resource_records;

public enum DnsResourceRecordType {

    A {
        @Override
        int toCode() {
            return 1;
        }
    },

    AAAA {
        @Override
        int toCode() {
            return 28;
        }
    },

    MX {
        @Override
        int toCode() {
            return 15;
        }
    },

    SOA {
        @Override
        int toCode() {
            return 6;
        }
    },

    CNAME {
        @Override
        int toCode() {
            return 5;
        }
    },

    UNDEFINED{
        @Override
        int toCode() {
            return -1;
        }
    };

    abstract int toCode();

    public static DnsResourceRecordType fromCode(int code) {
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
