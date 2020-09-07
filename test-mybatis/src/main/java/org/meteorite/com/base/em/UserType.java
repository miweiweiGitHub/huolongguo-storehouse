package org.meteorite.com.base.em;

/**
 * @author EX_052100260
 * @title: UserType
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-7 14:55
 */
public enum UserType {

    LOCAL(1),
    THIRD(2),
    UNREGISTERED(4);

    private int val;

    UserType(int val){
        this.val=val;
    }

    /**
     * channel是否包含chl
     *
     * @param channel
     * @param chl
     * @return
     */
    public static boolean contain(int channel, UserType chl) {
        if (chl == null) {
            return false;
        }
        int i = channel >> log2(chl.val);
        return 1 == (1 & i);
    }

    /**
     * 求2的指数函数
     */
    private static int log2(int value) {
        return (int) (Math.log(value) / Math.log(2));
    }

    public static void main(String[] args) {
        System.out.println(contain(2, UserType.THIRD));
    }
}
