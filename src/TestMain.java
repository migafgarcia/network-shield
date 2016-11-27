import utils.BitUtils;

import java.util.Arrays;

public class TestMain {

    public static void main(String[] args) {

        byte test2 = 13;

        boolean[] booleans = BitUtils.byteToBits(test2);
        System.out.println(Arrays.toString(booleans));
        boolean[] test = {true, true, false, true};
        System.out.println(BitUtils.bitsToByte(test));



    }
}
