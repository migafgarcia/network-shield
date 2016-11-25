package dns.codes;

public enum DnsOpcode {
    /**
     * Standard query
     */
    QUERY {
        @Override
        int toCode() {
            return 0;
        }
    },

    /**
     * Inverse query
     */
    IQUERY {
        @Override
        int toCode() {
            return 1;
        }
    },

    /**
     * DNS status request
     */
    STATUS {
        @Override
        int toCode() {
            return 2;
        }
    },

    UNDEFINED {
        @Override
        int toCode() {
            return 3;
        }
    };


    abstract int toCode();

    public static DnsOpcode fromCode(int code) {
        switch(code) {
            case 0:
                return QUERY;
            case 1:
                return IQUERY;
            case 2:
                return STATUS;
            default:
                return UNDEFINED;
        }
    }
}
