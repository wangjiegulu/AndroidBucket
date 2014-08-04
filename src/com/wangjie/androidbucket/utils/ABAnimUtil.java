package com.wangjie.androidbucket.utils;

import java.util.Random;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 8/4/14.
 */
public class ABAnimUtil {
    private static Random random;

    /**
     * 生成一个振动频率
     * @param count
     * @param scope
     * @return
     */
    public static float[] generateShakeRate(int count, int scope){
        if(scope < 0){
            scope = -scope;
        }
        if(scope == 0){
            scope = 10;
        }
        if(null == random){
            random = new Random();
        }

        float[] rate = new float[count];
        for(int i = 0; i < count; i++){
            if(i == 0 || i == count - 1){ // 头尾都为0
                rate[i] = 0;
                continue;
            }
            if(i % 2 == 0){
                rate[i] = random.nextInt(scope);
            }else{
                rate[i] = random.nextInt(scope) - scope;
            }
        }
        return rate;
    }



}
