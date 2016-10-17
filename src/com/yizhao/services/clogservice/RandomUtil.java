package com.yizhao.services.clogservice;

import java.util.Random;

/**
 * Created by yzhao on 10/17/16.
 */
public class RandomUtil {

    private static Random random = new Random();

    public static int randomIntegerInRange( int min, int max ){
        int diff = max - min;
        return min + Math.round( random.nextFloat() * diff );
    }

    public static int randomIntegerInRange( Pair<Integer,Integer> range ){
        int min = range.getFirst();
        int max = range.getSecond();
        return randomIntegerInRange( min,max );
    }

    public static long randomLongInRange( long min, long max ){
        long diff = max - min;
        return min + Math.round( random.nextDouble() * diff );
    }

    public static long randomLongInRange( Pair<Long,Long> range ){
        long min = range.getFirst();
        long max = range.getSecond();
        return randomLongInRange( min, max );
    }

    /**
     * gets a random boolean value, where probability of the value being 'true' = pTrue
     * @param pTrue
     * @return
     */
    public static boolean randomBooleanByProbability(double pTrue) {
        return random.nextDouble() >= (1.0 - pTrue);
    }

}
