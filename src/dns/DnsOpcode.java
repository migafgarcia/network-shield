package dns;

/**
 * Created by mgarcia on 21-11-2016.
 */
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
