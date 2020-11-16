package com.example.demo.src.mahjong;

/**
 * @author zhangjiaru
 * @date: 2020/11/11
 */
public class HuMain {
    
    //private int[][] allPai = {{6, 1, 4, 1}, {3, 1, 1, 1}, {0}, {5, 2, 3}};
    
//    public HuMain() {
//        if (win(allPai)) {
//            System.out.println("Hu!\n");
//        } else {
//            System.out.println("Not Hu!\n");
//        }
//    }
    
    public boolean win(int[][] allPai) {
        int jiangPos = 0;//"将"的位置
        int yuShu;
        Boolean jiangExisted = false;
        int i, j;
        for (i = 0; i < 4; i++) {
            yuShu = allPai[i][0] % 3;
            if (yuShu == 1) {
                return false;
            }
            if (yuShu == 2) {
                if (jiangExisted) {
                    return false;
                }
                jiangPos = i;
                jiangExisted = true;
            }
        }
        //不含将处理
        for (i = 0; i < 4; i++) {
            if (i != jiangPos) {
                if (!Analyze(allPai[i], i == 3)) {
                    return false;
                }
            }
        }
        Boolean success = false;
        for (j = 1; j < 10; j++) {
            if (allPai[jiangPos][j] >= 2) {
                //除去这两张将牌
                allPai[jiangPos][j] -= 2;
                allPai[jiangPos][0] -= 2;
                if (Analyze(allPai[jiangPos], jiangPos == 3)) {
                    success = true;
                }
                allPai[jiangPos][j] += 2;
                allPai[jiangPos][0] += 2;
                if (success) {
                    break;
                }
            }
        }
        return success;
    }
    
    //分解成"刻"、"顺"组合
    private boolean Analyze(int[] aKindPai, boolean ziPai) {
        int i, j;
        if (aKindPai[0] == 0) {
            return true;
        }
        for (j = 0; j < 10; j++) {
            if (aKindPai[j] != 0) {
                break;
            }
        }
        Boolean result;
        if (aKindPai[j] >= 3) {
            //除去这三张刻牌
            aKindPai[j] -= 3;
            aKindPai[0] -= 3;
            result = Analyze(aKindPai, ziPai);
            //还原这三张刻牌
            aKindPai[j] += 3;
            aKindPai[0] += 3;
            return result;
        }
        //作为顺牌
        if (!ziPai && j < 8 && aKindPai[j + 1] > 0 && aKindPai[j + 2] > 0) {
            //除去三张顺牌
            aKindPai[j]--;
            aKindPai[j + 1]--;
            aKindPai[j + 2]--;
            aKindPai[0] -= 3;
            result = Analyze(aKindPai, ziPai);
            //还原这三张牌
            aKindPai[j]++;
            aKindPai[j + 1]++;
            aKindPai[j + 2]++;
            aKindPai[0] += 3;
            return result;
        }
        return false;
    }
    
    
}
