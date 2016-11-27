package dns.codes;

public enum DnsResponseCode {

    /**
     * No error condition.
     */
    NO_ERROR {
        @Override
        public int toCode() {
            return 0;
        }
    },

    /**
     * The name server was unable to interpret the query.
     */
    FORMAT_ERROR {
        @Override
        public int toCode() {
            return 1;
        }
    },

    /**
     * The name server was unable to process this query due to a problem with the name server.
     */
    SERVER_FAILURE {
        @Override
        public int toCode() {
            return 2;
        }
    },

    /**
     * Meaningful only for responses from an authoritative name server, this code signifies that the domain name
     * referenced in the query does not exist.
     */
    NAME_ERROR {
        @Override
        public int toCode() {
            return 3;
        }
    },

    /**
     * The name server does not support the requested kind of query.
     */
    NOT_IMPLEMENTED {
        @Override
        public int toCode() {
            return 4;
        }
    },

    /**
     * The name server refuses to perform the specified operation for policy reasons. For example, a name server may not
     * wish to provide the information to the particular requester, or a name server may not wish to perform a
     * particular operation (e.g., zone transfer) for particular data.
     */
    REFUSED {
        @Override
        public int toCode() {
            return 5;
        }
    },

    /**
     * Codes not yet implemented
     */
    UNDEFINED {
        @Override
        public int toCode() {
            return 6;
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
    public static DnsResponseCode fromCode(int code) {
        switch(code) {
            case 0:
                return NO_ERROR;
            case 1:
                return FORMAT_ERROR;
            case 2:
                return SERVER_FAILURE;
            case 3:
                return NAME_ERROR;
            case 4:
                return NOT_IMPLEMENTED;
            case 5:
                return REFUSED;
            default:
                return UNDEFINED;
        }
    }



}
