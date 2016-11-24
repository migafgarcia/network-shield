import utils.BitUtils;

import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) {
        byte[] test = {1,2};
        System.out.println(BitUtils.byteArrayToInt(test));

        byte test2 = 1;
        System.out.println(Arrays.toString(BitUtils.byteToBooleanArray(test2)));


    }
}
