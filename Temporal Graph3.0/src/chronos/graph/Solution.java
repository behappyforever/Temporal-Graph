package chronos.graph;

public class Solution {
    public int NumberOf1(int n) {

        int rtn=0;

        int flag=1;
        while (flag!=0){
            if((n&flag)!=0)
                rtn++;
            flag<<=1;
        }

        return rtn;
    }
}
