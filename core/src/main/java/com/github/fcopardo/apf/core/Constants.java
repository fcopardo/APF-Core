package com.github.fcopardo.apf.core;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by FcoPardo on 3/5/17.
 */

public class Constants {


    public static final int STRATEGY_DONT_STORE = 0;
    public static final int STRATEGY_MEMORY = 1;
    public static final int STRATEGY_DATABASE = 2;
    public static final int STRATEGY_WEBSERVICE = 3;
    public static final int STRATEGY_MEMORY_AND_DATABASE = 4;
    public static final int STRATEGY_MEMORY_AND_WEBSERVICE = 5;
    public static final int STRATEGY_DATABASE_AND_WEBSERVICE = 6;
    public static final int STRATEGY_ALL = 7;


    /**
     * Constants for Ormlite. They will represent the name of each field in the database.
     */
    public static final String STORAGE_STRATEGY = "storage_strategy";
    public static final String PATH_TO_FILES = "path_to_files";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STRATEGY_DONT_STORE, STRATEGY_MEMORY, STRATEGY_DATABASE, STRATEGY_WEBSERVICE, STRATEGY_MEMORY_AND_DATABASE,
            STRATEGY_MEMORY_AND_WEBSERVICE, STRATEGY_DATABASE_AND_WEBSERVICE, STRATEGY_ALL})
    public @interface StorageStrategy{}




}
