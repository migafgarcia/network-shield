import utils.BitUtils;

import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) {

        byte test2 = 13;

        boolean[] booleans = BitUtils.byteToBooleanArray(test2);
        System.out.println(Arrays.toString(booleans));

        System.out.println(BitUtils.booleanArrayToByte(booleans));



    }
}
