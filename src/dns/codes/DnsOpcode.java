package dns.codes;

/**
 * Dns Opcode enum
 */
public enum DnsOpcode {

    /**
     * Standard query
     */
    QUERY {
        @Override
        public int toCode() {
            return 0;
        }
    },

    /**
     * Inverse query
     */
    IQUERY {
        @Override
        public int toCode() {
            return 1;
        }
    },

    /**
     * DNS status request
     */
    STATUS {
        @Override
        public int toCode() {
            return 2;
        }
    },

    /**
     * Codes not yet implemented
     */
    UNDEFINED {
        @Override
        public int toCode() {
            return 3;
        }
    };

    /**
     * Returns the int value of this code
     * @return int value of this code
     */
    public abstract int toCode();

    /**
     *  Return the DnsOpcode correspondent to code
     *
     * @param code opcode int
     * @return correspondent opcode
     */
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
