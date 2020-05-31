package utils;

import model.yxt.ResultJavaEntity;
import model.yxt.UserInfoJavaModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class SyncDataJavaUtil {


    /**
     * 同步用户
     *
     * @param islink    是否同步用户基本信息
     * @param users
     * @param apikey
     * @param secretkey
     * @param baseUrl
     * @return
     * @throws IOException
     */
    public static ResultJavaEntity users(int islink, List<UserInfoJavaModel> users, String apikey, String secretkey, String baseUrl) throws IOException {
        JSONArray array = JSONArray.fromObject(users);
        JSONObject params = getPublicParam(apikey, secretkey);
        params.put("islink", islink);
        params.put("datas", array);
        String url = baseUrl + "udp/sy/users";
        return getResult(url, params.toString());
    }

    /**
     * 获取企业大学接口数据
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static ResultJavaEntity getResult(String url, String params) throws IOException {
        String result = null;
        try {
            result = OKHttp2Utils.syncJavaSendPost(url, params);
        } catch (IOException e) {
            try {
                // 发生异常重新请求一次
                result = OKHttp2Utils.syncJavaSendPost(url, params);
            } catch (IOException e1) {
                throw new IOException("获取企业大学接口数据失败!", e1);
            }
        }
        // 统一将返回值封装为实体
        JSONObject jsonObject = JSONObject.fromObject(result);
        return (ResultJavaEntity) JSONObject.toBean(jsonObject, ResultJavaEntity.class);
    }

    /**
     * 初始化公共参数
     *
     * @param apikey
     * @param secretkey
     * @return
     */
    public static JSONObject getPublicParam(String apikey, String secretkey) {
        JSONObject params = new JSONObject();
        params.put("apikey", apikey);
        params.put("salt", new Random().nextInt(10000));
        params.put("signature", QidaSHA256.SHA256Encrypt(secretkey + params.get("salt")));
        return params;
    }

    public static ResultJavaEntity arrayOp(List<String> strings, String url, String apikey, String secretkey)
            throws IOException {
        JSONObject params = getPublicParam(apikey, secretkey);
        JSONArray array = JSONArray.fromObject(strings);
        params.put("datas", array.toString());
        return SyncDataJavaUtil.getResult(url, params.toString());
    }
}
