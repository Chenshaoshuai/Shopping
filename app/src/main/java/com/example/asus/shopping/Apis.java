package com.example.asus.shopping;

public class Apis {
  //注册
  public static final String DATA_REGISTER="user/v1/register";
  //登录
  public static final String DATA_LOG="user/v1/login";

  public static final String HOMEPAGE_LIST="commodity/v1/commodityList";
  public static final String HOMEPAGE_BANNER="commodity/v1/bannerShow";
  //圈子
  public static final String CIRCLE_LIST="circle/v1/findCircleList?page=1&count=5";
  //详情
  public static final String DETAIL_PAGE="commodity/v1/findCommodityDetailsById?commodityId=%s";
  //查询购物车
  public static final String QUERY_CAR="order/verify/v1/findShoppingCart?userId=%s&sessionId=%s";
  //同步购物车
  public static final String ADD_CAR = "order/verify/v1/syncShoppingCart";


}
