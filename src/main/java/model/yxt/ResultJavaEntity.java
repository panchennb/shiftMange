package model.yxt;

/**
 * 调用接口统一返回格式
 */
public class ResultJavaEntity {

    /**
     * 记录总条数
     */
    private int result;

    /**
     * 返回提示
     */
    private String msg;

    /**
     * 成功执行后返回的数据(Json格式字符串)
     */
    private String data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultJavaEntity [result=" + result + ", msg=" + msg + ", data=" + data + "]";
    }

}
