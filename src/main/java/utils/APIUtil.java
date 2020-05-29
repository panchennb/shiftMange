package utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APIUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIUtil.class);
    private static final Pattern DEFAULT_TIME_PATTERN = Pattern.compile("^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$");
    private static final Pattern DEFAULT_UUID_PATTERN = Pattern.compile("^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$");
    private static final String PID = "pid";
    private static final String USEBODY = "usebody";
    private static final String ONE = "1";
    private static final String TRUE = "true";
    private static final String SCHEME_POSTFIX = "://";
    private static final String SLASH = "/";
    private static final String COLON = ":";
    private static final String BLANK = "";

    /**
     * Hide Utility Class Constructor
     */
    private APIUtil() {
    }

    /**
     * generateBaseUrl
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String generateBaseUrl(HttpServletRequest request) {
        int port = request.getServerPort();
        String reqUri = request.getRequestURI();
        return request.getScheme() + SCHEME_POSTFIX + request.getServerName() + (port == 80 ? BLANK : COLON + port)
                + (reqUri.endsWith(SLASH) ? reqUri : reqUri + SLASH);
    }

    /**
     * getUUID
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * str2Int
     *
     * @param str     String
     * @param nullVal int
     * @return int
     */
    public static int str2Int(String str, int nullVal) {
        int result = nullVal;
        try {
            if (str != null && StringUtils.isNumeric(str)) {
                result = Integer.parseInt(str);
            }
        } catch (Exception e) {
            LOGGER.info("Integer.parseInt() str is not a number:" + str);
        }
        return result;
    }

    /**
     * str2Long
     *
     * @param str     String
     * @param nullVal long
     * @return long
     */
    public static long str2Long(String str, long nullVal) {
        long result = nullVal;
        try {
            if (str != null && StringUtils.isNumeric(str)) {
                result = Long.parseLong(str);
            }
        } catch (Exception e) {
            LOGGER.info("Long.parseLong() str is not a number:" + str);
        }
        return result;
    }

    /**
     * str2Double
     *
     * @param str     String
     * @param nullVal double
     * @return double
     */
    public static double str2Double(String str, double nullVal) {
        double result;
        try {
            if (StringUtils.isBlank(str)) {
                return nullVal;
            }
            result = Double.parseDouble(str);
        } catch (Exception e) {
            result = nullVal;
            LOGGER.info("Double.parseDouble() error:" + e.getMessage());
        }
        return result;
    }

    /**
     * str2Float
     *
     * @param str     String
     * @param nullVal float
     * @return double
     */
    public static float str2Float(String str, float nullVal) {
        float result;
        try {
            if (StringUtils.isBlank(str)) {
                return nullVal;
            }
            result = Float.parseFloat(str);
        } catch (Exception e) {
            result = nullVal;
            LOGGER.info("Float.parseFloat() error:" + e.getMessage());
        }
        return result;
    }

    /**
     * now
     *
     * @return String
     */
    public static String now() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(new Date());
    }

    public static String now4Oracle() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return f.format(new Date());
    }

    /**
     * createdResponse
     *
     * @param body String
     * @return ResponseEntity
     */
    public static ResponseEntity<String> createdErrorResponse(String body) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * handelMethodArgumentNotValidException
     *
     * @param ex        MethodArgumentNotValidException
     * @param msgSource MessageSource
     * @return ResponseEntity
     */
    public static ResponseEntity<String> handelMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, MessageSource msgSource) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        String key;
        if (errors == null || errors.isEmpty()) {
            key = "global.ServiceInternalError";
        } else {
            key = errors.get(0).getDefaultMessage();
        }
        String msg = msgSource.getMessage(key, null, null);
        return generateResponse(key, msg);
    }

    /**
     * handelGeneralException
     *
     * @param ex        Exception
     * @param msgSource MessageSource
     * @return ResponseEntity
     */
    public static ResponseEntity<String> handelGeneralException(Exception ex, MessageSource msgSource) {
        LOGGER.error("Unhandled exception:", ex);
        String key = "global.ServiceInternalError";
        String msg = msgSource.getMessage(key, null, null);
        return generateResponse(key, msg);
    }

    private static ResponseEntity<String> generateResponse(String key, String msg) {
        String[] msgs = msg.split(";");
        if (msgs.length < 2) {
            msgs = new String[]{"400", "Unknow error!"};
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("message", msgs[1]);
        JSONObject error = new JSONObject();
        error.put("error", jsonObject);
        return new ResponseEntity<String>(error.toString(), HttpStatus.valueOf(str2Int(msgs[0], 400)));
    }

    /**
     * getClientIp
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    /**
     * encode
     *
     * @param str String
     * @return String
     */
    public static String encode(String str) {
        String result;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            result = str;
            LOGGER.error("URLEncoder.encode() failed", e);
        }
        return result;
    }

    /**
     * decode
     *
     * @param encodeStr String
     * @return String
     */
    public static String decode(String encodeStr) {
        String result;
        try {
            result = URLDecoder.decode(encodeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            result = encodeStr;
            LOGGER.error("URLDecoder.decode() failed", e);
        }
        return result;
    }

    /**
     * 方法过期 有注入风险
     *
     * @param pageable
     * @return
     */
    @Deprecated
    public static String createOrderSql(Pageable pageable) {
        String orderBy = "createTime";
        String direction = "DESC";
        Sort sort = pageable.getSort();
        if (sort != null) {
            for (Order order : sort) {
                orderBy = order.getProperty();
                if (order.getDirection() == Direction.DESC) {
                    direction = "DESC";
                } else {
                    direction = "ASC";
                }
            }
        }
        return "ORDER BY " + orderBy + " " + direction;
    }


    public static String createOrderSql(Pageable pageable, Class entityClass) {
        String orderBy = "createTime";
        String direction = "DESC";
        Sort sort = pageable.getSort();
        if (sort != null) {
            for (Order order : sort) {
                orderBy = order.getProperty();
                //校验orderby 参数是否合法，同时防止注入
                verifyOrderbyWithLog(orderBy, entityClass);
                if (order.getDirection() == Direction.DESC) {
                    direction = "DESC";
                } else {
                    direction = "ASC";
                }
            }
        }
        return "ORDER BY " + orderBy + " " + direction;
    }

    /**
     * 方法过期，有注入风险
     *
     * @param pageable
     * @param tableName
     * @return
     */
    @Deprecated
    public static String createOrderSql(Pageable pageable, String tableName) {
        String orderBy = "createDate";
        String direction = "DESC";
        Sort sort = pageable.getSort();
        if (sort != null) {
            for (Order order : sort) {
                orderBy = order.getProperty();
                if (order.getDirection() == Direction.DESC) {
                    direction = "DESC";
                } else {
                    direction = "ASC";
                }
            }
        }
        return "ORDER BY " + tableName + "." + orderBy + " " + direction;
    }

    public static String createOrderSql(Pageable pageable, String tableName, Class entityClass) {
        String orderBy = "createDate";
        String direction = "DESC";
        Sort sort = pageable.getSort();
        if (sort != null) {
            for (Order order : sort) {
                orderBy = order.getProperty();
                verifyOrderbyWithLog(orderBy, entityClass);
                if (order.getDirection() == Direction.DESC) {
                    direction = "DESC";
                } else {
                    direction = "ASC";
                }
            }
        }
        return "ORDER BY " + tableName + "." + orderBy + " " + direction;
    }

    /**
     * verifyOrderby
     *
     * @param orderBy     String
     * @param entityClass Class
     */
    public static void verifyOrderbyWithLog(String orderBy, Class entityClass) {
        if (!StringUtils.isBlank(orderBy)) {
            Field[] fields = entityClass.getDeclaredFields();
            boolean valid = false;
            for (Field field : fields) {
                String orderByName = orderBy;
                if (orderBy.indexOf("_") != -1) {
                    orderByName = orderBy.replace("_", "");
                }
                if (orderByName.equalsIgnoreCase(field.getName())) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                LOGGER.error("sql order by error,orderby is " + orderBy + " entityName is " + entityClass.getName());
            }
        }
    }

    public static long time2Long(String time, long def) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dfs.parse(time).getTime();
        } catch (ParseException e) {
            return def;
        }
    }

    public static long time2Long(String time, long def, String format) {
        SimpleDateFormat dfs = new SimpleDateFormat(format);
        try {
            return dfs.parse(time).getTime();
        } catch (ParseException e) {
            return def;
        }
    }

    public static String time2String(Date time, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dfs = new SimpleDateFormat(format);
        return dfs.format(time);
    }

    /**
     * getCurPage
     *
     * @param offset   int
     * @param pageSize int
     * @return int
     */
    public static int getCurPage(int offset, int pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (offset <= 0) {
            offset = 0;
        }
        return offset / pageSize;
    }

    /**
     * maskedEmail
     *
     * @param email String
     * @return String
     */
    public static String maskedEmail(String email) {
        String result;
        if (email != null && email.length() >= 5 && email.contains("@")) {
            int length = email.length();
            int index = email.indexOf("@");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                if (i < 1 || i >= index) {
                    sb.append(email.charAt(i));
                }
            }
            result = sb.toString();
            result = result.replaceFirst("@", "***@");
        } else {
            result = "***@***.***";
        }
        return result;
    }

    /**
     * maskedMobile
     *
     * @param mobile String
     * @return String
     */
    public static String maskedMobile(String mobile) {
        String result;
        if (mobile != null && mobile.matches("^[1]\\d{10}$")) {
            int length = mobile.length();
            StringBuilder sb = new StringBuilder(11);
            for (int i = 0; i < length; i++) {
                if (i > 2 && i < 8) {
                    sb.append('*');
                } else {
                    sb.append(mobile.charAt(i));
                }
            }
            result = sb.toString();
        } else {
            result = "1**********";
        }
        return result;
    }

    public static boolean isValidTimeString(String timeStr) {
        Matcher m = DEFAULT_TIME_PATTERN.matcher(timeStr);
        return m.matches();
    }

    public static long str2time(String date, String format, long defaute) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        try {
            return f.parse(date).getTime();
        } catch (ParseException e) {
            return defaute;
        }
    }

    public static Date str2Date(String date, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        try {
            return f.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void debugSQL(String sql, Object... params) {
        //String duration =  getDuration(date1, date2);
        String s = sql;
        for (Object o : params) {
            if (o != null) {
                String v = o.toString();
                if (o instanceof String) {
                    v = "'" + o.toString() + "'";
                }
                s = s.replaceFirst("\\?", v);
            } else {
                s = s.replaceFirst("\\?", "null");
            }

        }


        LOGGER.debug("-------- Running SQL:" + s);
    }

    public static void printErrorSQL(String sql, Object... params) {
        //String duration =  getDuration(date1, date2);
        String s = sql;
        for (Object o : params) {
            if (o != null) {
                String v = o.toString();
                if (o instanceof String) {
                    v = "'" + o.toString() + "'";
                }
                s = s.replaceFirst("\\?", v);
            } else {
                s = s.replaceFirst("\\?", "null");
            }
        }


        LOGGER.error("-------- Running SQL:" + s);
    }

    /**
     * createLinkString
     *
     * @param params  Map
     * @param signKey String
     * @return String
     */
    public static String createLinkString(Map<String, String> params, String signKey) {
        String prestr = createLinkStringWithoutSignkey(params);
        prestr += "&key=" + signKey;
        LOGGER.debug("link string:" + prestr);
        return prestr;
    }

    /**
     * createLinkStringWithoutSignkey
     *
     * @param params Map
     * @return String
     */
    public static String createLinkStringWithoutSignkey(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        int size = keys.size();
        for (int i = 0; i < size; i++) {
            String key = keys.get(i);
            if (!"sign".equalsIgnoreCase(key)) {
                if (i < size - 1) {
                    prestr += key + "=" + params.get(key) + "&";
                } else {
                    prestr += key + "=" + params.get(key);
                }
            }
        }
        return prestr;
    }

    /**
     * Get Map Data from Json String, only support one level values.
     *
     * @param jsonString String
     */
    public static Map<String, String> getDataMapFromJson(String jsonString) {
        Map<String, String> parameters = null;
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if (jsonObject != null && !jsonObject.isEmpty()) {
            parameters = new HashMap<String, String>();
            Iterator<?> it = jsonObject.keys();
            while (it != null && it.hasNext()) {
                String key = (String) it.next();
                String value = jsonObject.getString(key);
                if (StringUtils.isNotEmpty(value) && !"null".equalsIgnoreCase(value)) {
                    parameters.put(key, value);
                }
            }
        }
        return parameters;
    }

    /**
     * formatAmount
     *
     * @param amount
     * @param scale
     * @author:tangli Date:2016-5-12
     */
    public static double formatAmount(double amount, int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(amount);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String filterNullValue(String input, boolean isLast) {
        String output;
        if (input == null) {
            output = "";
        } else {
            output = replaceTab(input);
        }
        if (!isLast) {
            output += "\t";
        }
        return output;
    }

    private static String replaceTab(String input) {
        return input.replaceAll("\t", " ");
    }


}
