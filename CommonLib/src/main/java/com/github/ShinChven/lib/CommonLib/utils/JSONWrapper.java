package com.github.ShinChven.lib.CommonLib.utils;

import com.github.ShinChven.lib.CommonLib.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 安全地取出json，并可以设置默认值
 * Created by ShinChven on 2014/9/19.
 */
public class JSONWrapper {
    private final JSONObject jo;

    public static JSONWrapper wrap(JSONObject jo) {
        JSONWrapper wrapper = new JSONWrapper(jo);
        return wrapper;
    }

    private JSONWrapper(JSONObject jo) {
        this.jo = jo;
    }

    public String getString(String key) {
        try {
            return this.jo.getString(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return Constants.EMPTY;
        }
    }

    public String getString(String key, String defaultVal) {
        try {
            return this.jo.getString(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public int getInt(String key) {
        try {
            return this.jo.getInt(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    public int getInt(String key, int defaultVal) {
        try {
            return this.jo.getInt(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public boolean getBoolean(String key, boolean defaultVal) {
        try {
            return this.jo.getBoolean(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public double getDouble(String key) {
        try {
            return this.jo.getDouble(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    public double getDouble(String key, double defaultVal) {
        try {
            return this.jo.getDouble(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public long getLong(String key) {
        try {
            return this.jo.getLong(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return 0;
        }
    }

    public long getLong(String key, long defaultVal) {
        try {
            return this.jo.getLong(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return defaultVal;
        }
    }

    public JSONArray getJSONArray(String key){
        try {
            return this.jo.getJSONArray(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }
}
