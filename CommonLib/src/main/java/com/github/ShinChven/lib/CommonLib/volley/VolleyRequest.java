package com.github.ShinChven.lib.CommonLib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ShinChven.lib.CommonLib.utils.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by ShinChven on 15/5/3.
 */
public class VolleyRequest {

    private static final String BASE_URL = "your_base_url";

    /**
     * 使用volley 框架进行json 请求和响应
     * <p/>
     * 如果你在相应中获得得了一个包含唯一属性“AUTO” 的json。
     *
     * @param methodName 请求方法名
     * @param jsonString 你的jsonString
     * @param handler    将Volley 的两个响应监听合成一个
     */
    public static void volleyPostJSON(String methodName, String jsonString, final JSONResponseHandler handler) {

        JsonObjectRequest request = new VolleyAllReturnJSONObjectRequest(
                Request.Method.POST,
                new StringBuilder(BASE_URL).append(methodName).toString(),
                jsonString,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            LogUtil.i(VolleyAllReturnJSONObjectRequest.TAG_REQUEST,
                                    VolleyAllReturnJSONObjectRequest.RESPONSE +
                                            jsonObject.toString(4));
                        } catch (JSONException e) {
                            LogUtil.printStackTrace(e);
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

    public static void postString(String methodName, final Map<String, String> params, final StringResponseHandler handler) {

        StringUTF8Request request = new StringUTF8Request(
                Request.Method.POST,
                new StringBuilder(BASE_URL).append(methodName).toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handler.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.onFailed();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };


        // todo please modify your Application extension to VolleyAppContext to use the tool
        VolleyAppContext.getInstance().addToRequestQueue(request, methodName);

    }

    public static void getString(String methodName, final Map<String, String> params, final StringResponseHandler handler) {
        StringBuilder sb = new StringBuilder(BASE_URL);
        sb.append(methodName).append("?");
        for (String key : params.keySet()) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }

        StringUTF8Request request = new StringUTF8Request(Request.Method.GET, sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handler.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handler.onFailed();
            }
        });


        // todo please modify your Application extension to VolleyAppContext to use the tool
        VolleyAppContext.getInstance().addToRequestQueue(request, methodName);

    }

    public interface StringResponseHandler {
        void onSuccess(String response);

        void onFailed();
    }
}
