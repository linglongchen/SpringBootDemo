package com.chunqiu.mrjuly.common.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.List;

/**
 * @Description: 极光推送工具类
 * @Date: 2018/1/18
 * @Author: wcf
 */
public class JPushUtil {
    protected static final String APP_KEY ="f68bc1056fb9931fceb03590";
    protected static final String MASTER_SECRET = "2f4eb8b7fb01f3525d6a1261";
    protected static final String ALIAS_PREFIX_DRIVER = "WL_";
    protected static final String ALIAS_PREFIX_MANAGER = "GL_";
    protected static final String ALIAS_PREFIX_DEPARTMENT = "ZERO_";
    protected static final String ALIAS_PREFIX_Server = "AL_";
    private static JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);

    //是否是生产环境，true:生产环境，false:测试环境
    private static boolean isApnsProduction = false;

    public static void main(String[] args) {
        sendNotifyClient("ceshi2","测试","测试");

    }


    /**
     * @description 对用户发送通知-单个
     * @param
     * @author wcf
     * @date 2018/1/18
     * @return
     */
    public static boolean sendNotify(Integer userId, String title, String content, Integer type){
        try{
            String[] user = null;
            if(type.equals(0)){
                user = new String[]{ALIAS_PREFIX_DRIVER + userId};
            }else{
                user = new String[]{ALIAS_PREFIX_MANAGER + userId};
            }
            PushPayload pushPayload = buildPushObject_android_and_ios(user, title, content);
            PushResult pushResult = jpushClient.sendPush(pushPayload);
            if(pushResult.getResponseCode() == 200){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //发送消息给管理员
    public static boolean sendNotifyToManager(List<Integer> ids, String title, String content){
        return sendNotify(ids, title, content, 1);
    }
    //发送消息给司机，多人
    public static boolean sendNotifyToDriver(List<Integer> ids, String title, String content){
        return sendNotify(ids, title, content, 0);
    }
    //发送消息给司机，单人
    public static boolean sendNotifyToDriver(Integer userId, String title, String content){
        return sendNotify(userId, title, content, 0);
    }
    /**
     * @description 对用户发送通知-多个
     * @param type 0：对司机发送，1：对管理员发送
     * @author wcf
     * @date 2018/1/18
     * @return
     */
    public static boolean sendNotify(List<Integer> ids, String title, String content, Integer type){
        try{
            String[] alias = new String[ids.size()];
            for(int i = 0; i < ids.size(); i++){
                if(type.equals(0)){
                    alias[i] = ALIAS_PREFIX_DRIVER + ids.get(i);
                }else {
                    alias[i] = ALIAS_PREFIX_MANAGER + ids.get(i);
                }
            }
            PushPayload pushPayload = buildPushObject_android_and_ios(alias, title, content);
            PushResult pushResult = jpushClient.sendPush(pushPayload);
            if(pushResult.getResponseCode() == 200){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    //给客户端发送推送
    public static boolean sendNotifyClient(String uniqueEncoding, String title, String content){
        try{
            StringBuffer orderIdsBuffer = new StringBuffer();

            String[] uniqueEncodings = null;
            uniqueEncodings = new String[]{ALIAS_PREFIX_DEPARTMENT + uniqueEncoding};
            PushPayload pushPayload = buildPushObject_android_and_ios(uniqueEncodings, title, content);
            PushResult pushResult = jpushClient.sendPush(pushPayload);
            if(pushResult.getResponseCode() == 200){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //给服务端发送推送
    public static boolean sendNotifyServer(String uniqueEncoding, String title, String content){
        try{
            StringBuffer orderIdsBuffer = new StringBuffer();

            String[] uniqueEncodings = null;
            uniqueEncodings = new String[]{ALIAS_PREFIX_Server + uniqueEncoding};
            PushPayload pushPayload = buildPushObject_android_and_ios(uniqueEncodings, title, content);
            PushResult pushResult = jpushClient.sendPush(pushPayload);
            if(pushResult.getResponseCode() == 200){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @description 通过别名发送通知
     * @param
     * @author wcf
     * @date 2018/1/18
     * @return
     */
    public static PushPayload buildPushObject_android_and_ios(String[] alias, String title, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                //使用通知，不用消息
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()   //android
                                .setTitle(title)        //标题
                                .setAlert(content)      //通知内容
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()       //ios
                                .setAlert(title + " : " + content)      //通知内容
                                .incrBadge(1)   //应用角标+1
                                .setSound("sound.caf")
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(isApnsProduction)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .build();
    }

}
