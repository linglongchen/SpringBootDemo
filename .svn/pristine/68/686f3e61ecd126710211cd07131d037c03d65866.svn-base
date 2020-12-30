package com.chunqiu.mrjuly.common.enums;

public enum HotelTypeEnum {
	BUSINESS(1, "商务型"), 
	VACATION(2, "度假型"),
	ECONOMICS(3, "经济型");

	private int type;
	private String name;
	
	public static String getTypeNameByType(int type){
		String name = null;
		switch (type) {
		case 1:
			name = BUSINESS.name;
			break;
		case 2:
			name = VACATION.name;
			break;
		case 3:
			name = ECONOMICS.name;
			break;
		default:
			name = BUSINESS.name;
			break;
		}
		return name;
	}

	private HotelTypeEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
