package com.fh.util;

public class SystemConstant {

    public final static String TEMPLATE_ROOT_PATH= "/template";
    public final static String TEMPLATE_EXCEL_PATH= "excel-template.xml";
    public final static String TEMPLATE_WORD_PATH= "word-template.xml";
    public final static String TEMPLATE_PDF_PATH= "pdf-template.html";
    public final static String SESSION_KEY= "user";
    public final static String CART_KEY= "cart:";
    public final static String LOGIN_COOLOE_KEY= "login_cookie";
    public final static int COOKIE_EXPIRY_TIME= 7*24*60*60;
    public final static int REDIS_EXPIRY_TIME= 5*60;
    public final static String TOKEN_KEY= "token:";
    public final static int TOKEN_EXPIRE_TIME = 15 * 60 * 1000;
    public final static int ORDER_STATIS_WAIT = 1;
    public final static int ORDER_STATIS_FINISH = 2;

}
