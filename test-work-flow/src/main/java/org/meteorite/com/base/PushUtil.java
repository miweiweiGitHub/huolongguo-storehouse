package org.meteorite.com.base;

/**
 * @author EX_052100260
 * @title: PushUtil
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-10-22 10:24
 */
public class PushUtil {

// 系统推送- push队列优化
// 1.1 当前1分钟内调用极光API达到600次后，当前的这1分钟暂停发送等待到下一分钟开始后再进行发送；
// 1.2 排到API任务时如果 当前时间-触发push时间 超过N小时则自动放弃push。(通过配置文件来取)

    public static void main(String[] args) {

        final String watchkeys = "watchkeys";




    }

}
