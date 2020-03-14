package com.chunqiu.mrjuly.common.easemob.server.example.api.impl;


import com.chunqiu.mrjuly.common.easemob.server.example.comm.TokenUtil;
import com.chunqiu.mrjuly.common.easemob.server.example.api.AuthTokenAPI;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
