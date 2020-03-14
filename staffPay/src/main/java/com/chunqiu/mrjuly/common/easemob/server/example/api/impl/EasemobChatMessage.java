package com.chunqiu.mrjuly.common.easemob.server.example.api.impl;

import com.chunqiu.mrjuly.common.easemob.server.example.comm.EasemobAPI;
import com.chunqiu.mrjuly.common.easemob.server.example.comm.OrgInfo;
import com.chunqiu.mrjuly.common.easemob.server.example.comm.ResponseHandler;
import com.chunqiu.mrjuly.common.easemob.server.example.comm.TokenUtil;
import com.chunqiu.mrjuly.common.easemob.server.example.api.ChatMessageAPI;
import io.swagger.client.ApiException;
import io.swagger.client.api.ChatHistoryApi;


public class EasemobChatMessage  implements ChatMessageAPI {

    private ResponseHandler responseHandler = new ResponseHandler();
    private ChatHistoryApi api = new ChatHistoryApi();

    @Override
    public Object exportChatMessages(final Long limit,final String cursor,final String query) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatmessagesGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(),query,limit+"",cursor);
            }
        });
    }

    @Override
    public Object exportChatMessages(final String timeStr) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatmessagesTimeGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),timeStr);
            }
        });
    }
}
