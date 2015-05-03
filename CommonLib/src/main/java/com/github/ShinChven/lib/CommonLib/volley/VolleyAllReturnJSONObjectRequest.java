package com.github.ShinChven.lib.CommonLib.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ShinChven.lib.CommonLib.BuildConfig;
import com.github.ShinChven.lib.CommonLib.Constants;
import com.github.ShinChven.lib.CommonLib.utils.LogUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by ShinChven on 15/4/28.
 */
public class VolleyAllReturnJSONObjectRequest extends JsonObjectRequest {


    public static final String AUTO_CONSTRUCTED_JSON_OBJECT = "AUTO";
    public static final String TAG_REQUEST = "VolleyRequest";
    public static final String RESPONSE = "\nResponse:\n";

    public VolleyAllReturnJSONObjectRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public VolleyAllReturnJSONObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public VolleyAllReturnJSONObjectRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public VolleyAllReturnJSONObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public VolleyAllReturnJSONObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String origin;
        String je;
        // 在转换结果时检查请求是否出错，请求出错直接响应ERROR
        try {
            // 原始响应字符串
            origin = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
            // 清理过得漂亮字符串
            je = getResult(origin);
        } catch (UnsupportedEncodingException var3) {
            return Response.error(new ParseError(var3));
        }

        // 将原始响应字符串转成JSONObject
        try {
            return Response.success(new JSONObject(origin), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) { //转换失败跳过
            LogUtil.printStackTrace(e);
        }

        // 将漂亮的字符串转成JSONObject
        try {
            Response<JSONObject> success = Response.success(new JSONObject(je), HttpHeaderParser.parseCacheHeaders(response));
            LogUtil.i("request", "响应内容为标准JSON。");
            return success;
        } catch (JSONException e) {  // 转换失败跳过
            LogUtil.printStackTrace(e);
        }

        // 自动构建JSONObject
        try {
            JSONObject jo = new JSONObject();
            try { // 判断响应结果 是不是一个JSONArray
                jo.put(AUTO_CONSTRUCTED_JSON_OBJECT, new JSONArray(je)); // 是则将其添加进JSONObject
            } catch (JSONException e) {
                jo.put(AUTO_CONSTRUCTED_JSON_OBJECT, je); // 不是则将其字符串作为属性传入
                LogUtil.i("request", "响应内容为非标准JSON，做字符串处理。");
            }
            return Response.success(jo, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException var4) {
            var4.printStackTrace();
            return Response.error(new ParseError(var4));
        }

    }

    /**
     * 用于将丑陋的JSON String转成OK的JSON String
     *
     * @param responseBody
     * @return 漂亮的JSON，Good luck.
     */
    public static String getResult(String responseBody) {
        String result = null;
        try {
            result = responseBody;
            StringBuilder sb = new StringBuilder(result);
            LogUtil.i("处理前：" + result);
            if (sb.toString().substring(0, 2).equals("\"{")) {
                sb.deleteCharAt(0);
                sb.deleteCharAt(sb.length() - 1);
            }

            for (int i = 0; i < sb.length(); i++) {
                char c = sb.charAt(i);
                if (c == '\\') {
                    sb.deleteCharAt(i);
                }
                if (c == ':' && sb.charAt(i + 1) == ',') {
                    sb.insert(i + 1, 0);
                }
            }
            result = sb.toString();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
        String responseString = result == null ? Constants.EMPTY : result;
        LogUtil.i(TAG_REQUEST, RESPONSE + responseString);
        return responseString;
    }
}
