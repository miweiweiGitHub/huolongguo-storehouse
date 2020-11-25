package org.meteorite.com.controller;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.TagAliasResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.meteorite.com.base.CoralProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author EX_052100260
 * @title: AuroraController
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-25 11:21
 */
@RestController
@RequestMapping("/aurora")
@Slf4j
public class AuroraController {

    @Resource
    CoralProperties coralProperties;

    /**
     * 测试接口
     * @return
     */
    @GetMapping("/test")
    public String singleSend() {
        return coralProperties.getKey();
    }

    /**
     * 根据 rid 获取设备相关信息
     * @param id
     * @return
     */
    @PostMapping("/info/{id}")
    public String getDeviceInfo(@PathVariable String id) {
        log.info("request :{}",id);
        try {
            TagAliasResult deviceTagAlias = getClientConfig().getDeviceTagAlias(id);
            log.info("获取设备信息：{}",deviceTagAlias.toString());
            return deviceTagAlias.toString();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return "获取设备信息";
    }



    /**
     * 根据 rid 设置设备别名
     * @param id
     * @return
     */
    @PostMapping("/add/{id}")
    public String setDeviceTagAlias(@PathVariable String id) {
        log.info("request :{}",id);
        try {
            DefaultResult defaultResult = getClientConfig().updateDeviceTagAlias(id, "火龙果", null, null);
            log.info("更新设备别名：{}",defaultResult.toString());
            return defaultResult.toString();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        return "更新设备别名";
    }

    /**
     * 根据 rid 获取设备相关信息
     * @param id
     * @return
     */
    @PostMapping("/push/alias/{target}")
    public String pushAliasInfo(@PathVariable String target) {
        log.info("request :{}",target);
        try {
            PushPayload payload = getPushPayload(target);
            PushResult pushResult = getClientConfig().sendPush(payload);
            return pushResult.toString();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }

        return "给设备发消息";
    }

    /**
     * 获取 push client
     * @return
     */
    private JPushClient getClientConfig() {

        ClientConfig instance = ClientConfig.getInstance();
        instance.setTimeToLive(Long.parseLong(coralProperties.getLiveTime()));
        instance.setApnsProduction(coralProperties.isApnsProduction());
        JPushClient jPushClient = new JPushClient(coralProperties.getSecret(),coralProperties.getKey(),null,instance);
        return jPushClient;
    }

    /**
     * 获取 push 内容
     * @return
     */
    private PushPayload getPushPayload(String target) {
        Map<String, String> map = new HashMap<>();
        map.put("title", "火龙果的测试数据");
        map.put("content", "测试demo，恭喜你测试通过");
        map.put("msgId", UUID.randomUUID().toString().replaceAll("-",""));
        return PushPayload.newBuilder().setPlatform(Platform.android_ios())
                .setAudience(getAudience(target))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert("测试demo，恭喜你测试通过")
                                .setTitle("火龙果的测试数据")
                                .setLargeIcon("")
                                .addExtras(map).build())
                        .build())
                .build();

    }

    private Audience getAudience(String target) {
//        if (CollectionUtils.isEmpty(alias) && CollectionUtils.isEmpty(tags) && CollectionUtils.isEmpty(tagsAnd)) {
//            return Audience.all();
//        }
//
//        Audience.Builder audienceBuilder = Audience.newBuilder();
//        if (!CollectionUtils.isEmpty(alias)) {
//            audienceBuilder.addAudienceTarget(AudienceTarget.alias(alias));
//        }
//        if (!CollectionUtils.isEmpty(tags)) {
//            audienceBuilder.addAudienceTarget(AudienceTarget.tag(tags));
//        }
//        if (!CollectionUtils.isEmpty(tagsAnd)) {
//            audienceBuilder.addAudienceTarget(AudienceTarget.tag_and(tagsAnd));
//        }
        return Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(target)).build();
    }


}
