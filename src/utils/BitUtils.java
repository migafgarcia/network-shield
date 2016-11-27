package utils;

import java.nio.ByteBuffer;

public class BitUtils {

    /**
     * Turns bits in a byte array to int. If size of array is bigger than 4, an overflow will occur.
     *
     * @param byteArray The array to be converted
     * @return The correspondent integer value
     */
    public static short byteArrayToInt(byte[] byteArray) {
        return ByteBuffer.wrap(byteArray).getShort();
    }

    /**
     *
     * @param _byte
     * @return
     */
    public static boolean[] byteToBooleanArray(byte _byte) {
        //bit = (number >> x) & 1;
        boolean[] booleanArray = new boolean[8];
        booleanArray[7] = ((_byte >> 0) & 1) != 0;
        booleanArray[6] = ((_byte >> 1) & 1) != 0;
        booleanArray[5] = ((_byte >> 2) & 1) != 0;
        booleanArray[4] = ((_byte >> 3) & 1) != 0;
        booleanArray[3] = ((_byte >> 4) & 1) != 0;
        booleanArray[2] = ((_byte >> 5) & 1) != 0;
        booleanArray[1] = ((_byte >> 6) & 1) != 0;
        booleanArray[0] = ((_byte >> 7) & 1) != 0;
        return booleanArray;
    }

    public static byte booleanArrayToByte(boolean[] booleanArray) {
        byte _byte = 0;
        _byte |= (booleanArray[7] ? 1 : 0) << 0;
        _byte |= (booleanArray[6] ? 1 : 0) << 1;
        _byte |= (booleanArray[5] ? 1 : 0) << 2;
        _byte |= (booleanArray[4] ? 1 : 0) << 3;
        _byte |= (booleanArray[3] ? 1 : 0) << 4;
        _byte |= (booleanArray[2] ? 1 : 0) << 5;
        _byte |= (booleanArray[1] ? 1 : 0) << 6;
        _byte |= (booleanArray[0] ? 1 : 0) << 7;
        return _byte;

    }

    /**
     *
     * @param arr
     * @return
     */
    public static int booleanArrayToInt(boolean[] arr){
        int n = 0;
        for (boolean b : arr)
            n = (n << 1) | (b ? 1 : 0);
        return n;
    }
}
