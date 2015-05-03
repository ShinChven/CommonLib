package com.github.ShinChven.lib.CommonLib.tool;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ShinChven on 15/5/3.
 */
public class VolleyRequest {

    /**
     * 使用volley 框架进行json 请求和响应
     * <p/>
     * 如果你在相应中获得得了一个包含唯一属性“AUTO” 的json。
     *
     * @param url        请尔地址
     * @param methodName 请求方法名
     * @param jsonString 你的jsonString
     * @param handler    将Volley 的两个响应监听合成一个
     */
    public static void volleyPostSimple(String url, String methodName, String jsonString, final JSONResponseHandler handler) {

        JsonObjectRequest request = new VolleyAllReturnJSONObjectRequest(Request.Method.POST, url, jsonString,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            LogTool.i(VolleyAllReturnJSONObjectRequest.TAG_REQUEST,
                                    VolleyAllReturnJSONObjectRequest.RESPONSE +
                                            jsonObject.toString(4));
                        } catch (JSONException e) {
                            LogTool.printStackTrace(e);
                        }
                        handler.onSuccess(jsonObject);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        handler.onFailed(volleyError);
                    }
                }
        );
        // todo please modify your Application extension to VolleyAppContext to use the tool
        VolleyAppContext.getInstance().addToRequestQueue(request, methodName);

    }

    /**
     * Created by ShinChven on 15/5/3.
     * 将两个Volley 的相应监听合并成一个接口
     */
    public interface JSONResponseHandler {
        void onSuccess(JSONObject jsonObject);

        void onFailed(VolleyError volleyError);
    }
}
