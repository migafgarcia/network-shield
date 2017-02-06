package com.mgarcia.dns.codes;

/**
 *
 */
public enum ResponseCode {
    // No error condition
    NO_ERROR (0),

    // The name server was unable to interpret the query
    FORMAT_ERROR (1),

    // The name server was unable to process this query due to a problem with the name server
    SERVER_FAILURE (2),

    // Meaningful only for responses from an authoritative name server, this code signifies that the domain name referenced in the query does not exist.
    NAME_ERROR (3),

    // The name server does not support the requested kind of query
    NOT_IMPLEMENTED (4),

    /**
     * The name server refuses to perform the specified operation for policy reasons. For example, a name server may not
     * wish to provide the information to the particular requester, or a name server may not wish to perform a
     * particular operation (e.g., zone transfer) for particular data.
     */
    REFUSED (5),

    // Codes not yet implemented
    UNDEFINED (6);

    /**
     *
     */
    private final int responseCode;

    /**
     *
     * @param responseCode
     */
    ResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     *
     * @return
     */
    public int responseCode() {
        return responseCode;
    }


    /**
     * Returns the correspondent response code
     *
     * @param bits Response code in bit form
     * @return Correspondent response code
     */
    public static ResponseCode fromBits(boolean[] bits) {
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
    public static boolean[] toBits(ResponseCode responseCode) {
        boolean[] booleans = new boolean[4];

        booleans[3] = ((responseCode.responseCode() >> 4) & 1) != 0;
        booleans[2] = ((responseCode.responseCode() >> 5) & 1) != 0;
        booleans[1] = ((responseCode.responseCode() >> 6) & 1) != 0;
        booleans[0] = ((responseCode.responseCode() >> 7) & 1) != 0;

        return booleans;

    }


}
