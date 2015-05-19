package com.rizomm.ram.libeery.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Robin on 10/04/2015.
 */
public class Constant {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final int ADD_BEER_REQUEST = 1234;
    public static final int TAKE_PICTURE_REQUEST = 5678;

    public static final int RESULT_CODE_OK = 1;
    public static final int RESULT_CODE_KO = 0;

    public static final String INTENT_DETAIL_DATA_1 = "com.rizomm.ram.libeery.fragment.currentBeer";
    public static final String ADD_BEER_RESULT = "com.rizomm.ram.libeery.activity.addedbeer";

    public Constant() {
    }
}
