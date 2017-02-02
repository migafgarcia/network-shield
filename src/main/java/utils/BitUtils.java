package utils;

import java.nio.ByteBuffer;

/**
 *
 */
public class BitUtils {

    /**
     * Turns bits in a byte array to int. If size of array is bigger than 4, an overflow will occur.
     *
     * @param bytes The array to be converted
     * @return The correspondent integer value
     */
    public static short byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }

    /**
     * Converts a byte to a boolean array in big endian order.
     * Example:
     *   intToBits(13) = [false, false, false, false, true, true, false, true].
     *
     * This is equivalent of calling intToBits(_byte, 8).
     * @param _int The byte to be converted
     * @return Boolean array of size 8 representing byte
     */
    public static boolean[] intToBits(int _int) {
        return intToBits(_int, 8);
    }

    /**
     * Converts a byte to a boolean array in big endian order.
     * Example:
     *   intToBits(13, 4) = [true, true, false, true]
     * @param _int The int to be converted
     * @param size The size of the resulting boolean array
     * @return Boolean array of size size representing byte
     */
    public static boolean[] intToBits(int _int, int size) {
        boolean[] bits = new boolean[size];

        for(int i = 0; i < size; i++)
            bits[i] = ((_int >> (size - i - 1)) & 1) != 0;

        return bits;
    }

    /**
     * Creates a byte with the value in bits. The bits size should not be larger than 8.
     * @param bits The bits to be converted
     * @return The byte value of bits
     */
    public static byte bitsToByte(boolean[] bits) {
        byte _byte = 0;
        for(int i = 0; i < bits.length; i++)
            _byte |= (bits[i] ? 1 : 0) << (bits.length - i - 1);
        return _byte;
    }

}
