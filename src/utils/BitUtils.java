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
        ByteBuffer wrapped = ByteBuffer.wrap(byteArray);
        return wrapped.getShort();
    }

    public static boolean[] byteToBooleanArray(byte _byte) {

        boolean[] booleanArray = new boolean[8];
        booleanArray[7] = ((_byte & 0x01) != 0);
        booleanArray[6] = ((_byte & 0x02) != 0);
        booleanArray[5] = ((_byte & 0x04) != 0);
        booleanArray[4] = ((_byte & 0x08) != 0);
        booleanArray[3] = ((_byte & 0x16) != 0);
        booleanArray[2] = ((_byte & 0x32) != 0);
        booleanArray[1] = ((_byte & 0x64) != 0);
        booleanArray[0] = ((_byte & 0x128) != 0);
        return booleanArray;
    }

    public static int booleanArrayToInt(boolean[] arr){
        int n = 0;
        for (boolean b : arr)
            n = (n << 1) | (b ? 1 : 0);
        return n;
    }
}
