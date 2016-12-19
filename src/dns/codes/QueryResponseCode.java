package dns.codes;

public enum QueryResponseCode {

    /**
     * No error condition.
     */
    NO_ERROR {
        @Override
        public byte toCode() {
            return 0;
        }
    },

    /**
     * The name server was unable to interpret the query.
     */
    FORMAT_ERROR {
        @Override
        public byte toCode() {
            return 1;
        }
    },

    /**
     * The name server was unable to process this query due to a problem with the name server.
     */
    SERVER_FAILURE {
        @Override
        public byte toCode() {
            return 2;
        }
    },

    /**
     * Meaningful only for responses from an authoritative name server, this code signifies that the domain name
     * referenced in the query does not exist.
     */
    NAME_ERROR {
        @Override
        public byte toCode() {
            return 3;
        }
    },

    /**
     * The name server does not support the requested kind of query.
     */
    NOT_IMPLEMENTED {
        @Override
        public byte toCode() {
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
        public byte toCode() {
            return 5;
        }
    },

    /**
     * Codes not yet implemented
     */
    UNDEFINED {
        @Override
        public byte toCode() {
            return 6;
        }
    };

    /**
     * Returns the int value of this code
     * @return int value of this code
     */
    public abstract byte toCode();

    /**
     * Returns the correspondent response code
     *
     * @param bits Response code in bit form
     * @return Correspondent response code
     */
    public static QueryResponseCode fromBits(boolean[] bits) {
        byte code = 0;

        code |= (bits[3] ? 1 : 0);
        code |= (bits[2] ? 1 : 0) << 1;
        code |= (bits[1] ? 1 : 0) << 2;
        code |= (bits[0] ? 1 : 0) << 3;

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

    /**
     * Returns the response code in bit form
     *
     * @param responseCode The response code to convert
     * @return Bit form of the response code
     */
    public static boolean[] toBits(QueryResponseCode responseCode) {
        boolean[] booleans = new boolean[4];

        booleans[3] = ((responseCode.toCode() >> 4) & 1) != 0;
        booleans[2] = ((responseCode.toCode() >> 5) & 1) != 0;
        booleans[1] = ((responseCode.toCode() >> 6) & 1) != 0;
        booleans[0] = ((responseCode.toCode() >> 7) & 1) != 0;

        return booleans;

    }



}
