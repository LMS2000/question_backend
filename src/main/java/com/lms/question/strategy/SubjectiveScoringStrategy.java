package com.lms.question.strategy;

import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;

import java.text.DecimalFormat;
import java.util.Map;
//主观题判题
public class SubjectiveScoringStrategy implements ScoringStrategy{

    @Override
    public float scoring(RecordVo recordVo, Map<Integer, Question> questionMap) {
        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String answer = question.getAnswer().trim();
        String userAnswer = recordVo.getUserAnswer().trim();


        double v = calculateSimilarity(userAnswer, answer);

        float userScore= (float) v*questionScore;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedNumber = decimalFormat.format(userScore);

        float finalScore = Float.parseFloat(formattedNumber);
        recordVo.setScore(finalScore);
        return finalScore;
    }



    //相似度算法,   保留两位小数  0.88
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
