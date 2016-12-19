package dns.codes;

/**
 * Dns Opcode enum
 */
public enum Opcode {

    /**
     * Standard query
     */
    QUERY {
        @Override
        public byte toCode() {
            return 0;
        }
    },

    /**
     * Inverse query
     */
    IQUERY {
        @Override
        public byte toCode() {
            return 1;
        }
    },

    /**
     * DNS status request
     */
    STATUS {
        @Override
        public byte toCode() {
            return 2;
        }
    },

    /**
     * Codes not yet implemented
     */
    UNDEFINED {
        @Override
        public byte toCode() {
            return 3;
        }
    };

    /**
     * Returns the byte value of this code
     * @return Byte value of this code
     */
    public abstract byte toCode();


    /**
     * Returns the correspondent opcode
     *
     * @param bits Opcode in bit form
     * @return Correspondent opcode
     */
    public static Opcode fromBits(boolean[] bits) {
        byte code = 0;

        code |= (bits[3] ? 1 : 0);
        code |= (bits[2] ? 1 : 0) << 1;
        code |= (bits[1] ? 1 : 0) << 2;
        code |= (bits[0] ? 1 : 0) << 3;

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

    /**
     * Converts the opcode to bit form
     *
     * @param opcode The opcode to convert
     * @return The opcode in bit form
     */
    public static boolean[] toBits(Opcode opcode) {
        boolean[] booleans = new boolean[4];

        booleans[3] = ((opcode.toCode() >> 4) & 1) != 0;
        booleans[2] = ((opcode.toCode() >> 5) & 1) != 0;
        booleans[1] = ((opcode.toCode() >> 6) & 1) != 0;
        booleans[0] = ((opcode.toCode() >> 7) & 1) != 0;

        return booleans;

    }


}
