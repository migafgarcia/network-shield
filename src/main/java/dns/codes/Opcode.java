package dns.codes;

/**
 *
 */
public enum Opcode {

    QUERY (0),
    IQUERY (1),
    STATUS (2),
    UNDEFINED (3),
    NOTIFY (4),
    UPDATE (5);

    private final int opcode;

    Opcode(int opcode) {
        this.opcode = opcode;
    }

    public int opcode() {
        return opcode;
    }

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
            case 4:
                return NOTIFY;
            case 5:
                return UPDATE;
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

        booleans[3] = ((opcode.opcode() >> 4) & 1) != 0;
        booleans[2] = ((opcode.opcode() >> 5) & 1) != 0;
        booleans[1] = ((opcode.opcode() >> 6) & 1) != 0;
        booleans[0] = ((opcode.opcode() >> 7) & 1) != 0;

        return booleans;

    }

}
