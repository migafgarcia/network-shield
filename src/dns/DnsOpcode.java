package dns;

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
    };


    abstract int toCode();
}
