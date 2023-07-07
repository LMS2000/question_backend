package com.lms.question.test_subjective;

import com.lms.question.MainApplication;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainApplication.class)
public class TestSubjectTest {





    @Test
    public  void test2(){
        String userAnswer="import tensorflow as tf\n" +
                "from tensorflow.keras.models import Model\n" +
                "from tensorflow.keras.layers import Input, Conv2D, Add, Activation\n" +
                "\n" +
                "def residual_block(inputs):\n" +
                "    x = Conv2D(64, kernel_size=(3, 3), padding='same')(inputs)\n" +
                "    x = Activation('relu')(x)\n" +
                "    x = Conv2D(64, kernel_size=(3, 3), padding='same')(x)\n" +
                "    x = Add()([inputs, x])\n" +
                "    x = Activation('relu')(x)\n" +
                "    return x\n" +
                "\n" +
                "\n" +
                "for _ in range(7):\n" +
                "    x = residual_block(x)\n" +
                "\n" +
                "output = Conv2D(10, kernel_size=(3, 3), activation='softmax', padding='same')(x)\n" +
                "\n" +
                "model = Model(inputs=input_layer, outputs=output";


        String answer="import tensorflow as tf\n" +
                "from tensorflow.keras.models import Model\n" +
                "from tensorflow.keras.layers import Input, Conv2D, Add, Activation\n" +
                "\n" +
                "def residual_block(inputs):\n" +
                "    x = Conv2D(64, kernel_size=(3, 3), padding='same')(inputs)\n" +
                "    x = Activation('relu')(x)\n" +
                "    x = Conv2D(64, kernel_size=(3, 3), padding='same')(x)\n" +
                "    x = Add()([inputs, x])\n" +
                "    x = Activation('relu')(x)\n" +
                "    return x\n" +
                "\n" +
                "input_layer = Input(shape=(32, 32, 3))\n" +
                "x = Conv2D(64, kernel_size=(3, 3), padding='same')(input_layer)\n" +
                "\n" +
                "for _ in range(7):\n" +
                "    x = residual_block(x)\n" +
                "\n" +
                "output = Conv2D(10, kernel_size=(3, 3), activation='softmax', padding='same')(x)\n" +
                "\n" +
                "model = Model(inputs=input_layer, outputs=output";

        System.out.println(calculateSimilarity(userAnswer, answer));
    }



    public static double calculateSimilarity(String str1, String str2) {
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();

        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= str2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
                }
            }
        }

        double similarity = 1 - (double) dp[str1.length()][str2.length()] / Math.max(str1.length(), str2.length());
        return similarity;
    }
}
