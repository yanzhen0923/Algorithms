import java.util.*;

public class Test {

    static class Grocery{
        int amt; int calorie; int gram;
        Grocery(int a, int g, int c){
            amt = a; gram = g; calorie = c;
        }
    }

    private static class SubGrocery extends Grocery{
        int orgIndex;
        SubGrocery(int a, int g, int c, int o) {
            super(a, g, c);
            orgIndex = o;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int maxGrams = sc.nextInt();
            int types = sc.nextInt();
            Grocery[] gList = new Grocery[types];
            for(int i = 0; i < types; ++ i){
                gList[i] = new Grocery(sc.nextInt(), sc.nextInt(), sc.nextInt());
            }

            SubGrocery[] goods = new SubGrocery[10001]; int index = 0;
            for(int i = 0; i < gList.length; ++ i){
                int k = 1; int amount = gList[i].amt;
                while (k < amount){
                    goods[++ index] = new SubGrocery(k, k * gList[i].gram, k * gList[i].calorie, i);
                    amount -= k;
                    k <<= 1;
                }
                if(amount > 0){
                    goods[++ index] = new SubGrocery(amount, amount * gList[i].gram, amount * gList[i].calorie, i);
                }
            }

            int[][] ans = new int[index + 1][maxGrams + 1];
            for(int i = 1; i <= index; ++ i) {
                for (int j = 0; j <= maxGrams; ++ j) {
                    ans[i][j] = ans[i - 1][j];
                    if (j >= goods[i].gram) {
                        ans[i][j] = Math.max(ans[i - 1][j], ans[i - 1][j - goods[i].gram] + goods[i].calorie);
                    }
                }
            }

            int[] output = new int[types];
            int j = maxGrams;
            for(int i = index; i > 0; -- i){
                if(ans[i][j] > ans[i - 1][j]){
                    output[goods[i].orgIndex] += goods[i].amt;
                    j -= goods[i].gram;
                }
            }

            System.out.print("Case #" + x + ": ");
            for(int i = 0 ; i < output.length; ++ i){
                if(output[i] != 0) {
                    for(int k = 0; k < output[i]; ++ k)
                    System.out.print((i + 1) + " ");
                }
            }
            System.out.println();
        }
    }
}

