package dns;

public enum DnsResponseCode {

    /**
     * No error condition.
     */
    NO_ERROR {
        @Override
        int toCode() {
            return 0;
        }
    },

    /**
     * The name server was unable to interpret the query.
     */
    FORMAT_ERROR {
        @Override
        int toCode() {
            return 1;
        }
    },

    /**
     * The name server was unable to process this query due to a problem with the name server.
     */
    SERVER_FAILURE {
        @Override
        int toCode() {
            return 2;
        }
    },

    /**
     * Meaningful only for responses from an authoritative name server, this code signifies that the domain name
     * referenced in the query does not exist.
     */
    NAME_ERROR {
        @Override
        int toCode() {
            return 3;
        }
    },

    /**
     * The name server does not support the requested kind of query.
     */
    NOT_IMPLEMENTED {
        @Override
        int toCode() {
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
        int toCode() {
            return 5;
        }
    };

    abstract int toCode();

}
