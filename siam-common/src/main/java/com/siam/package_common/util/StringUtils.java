package com.siam.package_common.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

    private static final Pattern PATTERN_ISEMAIL = Pattern.compile("^[\\w\\.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,4}$");

    private static final Pattern PATTERN_ISURL = Pattern.compile("^(http://|https://)?([a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]{1,4}(/.*)?");

    private static final Pattern PATTERN_TRIM = Pattern.compile("^\\s+|\\s+$");

    private static final Pattern PATTERN_TRIM_LEFT = Pattern.compile("^\\s+");

    private static final Pattern PATTERN_TRIM_RIGHT = Pattern.compile("\\s+$");

    private static final Pattern PATTERN_ISNUMERIC = Pattern.compile("^\\d+(\\.\\d+)?$");

    private static final Pattern PATTERN_ISLOGINNAME = Pattern.compile("^[0-9a-zA-Z_@.-]+$");

    private static final Pattern PATTERN_ISNAME = Pattern.compile("^([a-zA-Z\\x20]+|[\\u4e00-\\u9fa5]+)$");

    private static final Pattern PATTERN_ISALPHAMERIC = Pattern.compile("^[a-zA-Z0-9]+$");

    public static final String[] NULL_STRING_ARRAY = new String[0];

    public static final String CRLF = System.getProperty("line.separator");

    /**
     * 要把输入内容组合成 sql语句时, 使用该函数过滤特殊字符以防SQL注入攻击
     * 1. 如果期待数字型输入, 使用isNumeric方法判断是否数字
     * 2. 如果期待字符型输入, 只要没有输入中没有单引号, 即可保证自己的sql语句不被破坏 Java中通常使用 PreparedStatement ,
     * SQL注入漏洞几乎可以避免
     */
    public static String sqlParamFilter(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        if ("update".equalsIgnoreCase(s) || "delete".equalsIgnoreCase(s) || "insert".equalsIgnoreCase(s)) {
            return s;
        }
        s = s.replaceAll("'", "''");
        s = s.replaceAll("--", "- -");
        s = s.replaceAll("%", "％");
        s = s.replaceAll("(?i)select", "ｓelect");
        s = s.replaceAll("(?i)exec", "ｅxec");
        s = s.replaceAll("(?i)declare", "ｄeclare");
        s = s.replaceAll("(?i)delete", "ｄelete");
        s = s.replaceAll("(?i)insert", "ｉnsert");
        s = s.replaceAll("(?i)update", "ｕpdate");
        return s.trim();
    }

    /**
     * 判断一个字符串是否是数字串
     */
    public static boolean isNumeric(String s) {
        if (StringUtils.hasText(s)) {
            return StringUtils.PATTERN_ISNUMERIC.matcher(s).matches();
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否只包含字母和数字
     */
    public static boolean isAlphameric(final String s) {
        if (StringUtils.hasText(s)) {
            return StringUtils.PATTERN_ISALPHAMERIC.matcher(s).matches();
        } else {
            return false;
        }
    }

    /**
     * 判断指定字符串是否符合登陆用户名的规则(仅包含字母数字下划线等)
     */
    public static boolean isLoginName(final String s) {
        return StringUtils.PATTERN_ISLOGINNAME.matcher(s).matches();
    }

    /**
     * 判断指定字符串是否符合中文姓名或者英文姓名的规则
     */
    public static boolean isName(final String s) {
        return StringUtils.PATTERN_ISNAME.matcher(s).matches();
    }

    /**
     * 检查一个字符串的长度（字节数）是否在指定范围之内
     *
     * @param s      字符串
     * @param minlen 0 表示不限制最小长度
     * @param maxlen 0 表示不限制最大长度
     * @return 在范围内返回true，否则返回false
     */
    public static boolean checkLength(final String s, final int minlen, final int maxlen) {
        if (StringUtils.hasText(s)) {
            int len = s.getBytes().length;
            return len >= minlen && (len <= maxlen || maxlen <= 0);
        } else {
            return minlen <= 0;
        }
    }

    /**
     * 检查一个字符串是否非空（未去除空格）
     *
     * @param s 字符串
     * @return 字符串长度大于0则返回true；否则返回false
     */
    public static boolean hasText(final String s) {
        if (s == null) {
            return false;
        }
        if (s.length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 检查一个字符串是否符合Email地址的格式
     *
     * @param s 字符串
     * @return 符合Email格式则返回true，否则返回false
     */
    public static boolean isEmail(final String s) {
        if (s == null) {
            return false;
        }
        return StringUtils.PATTERN_ISEMAIL.matcher(s).matches();
    }

    /**
     * 检查一个字符串是否符合URL地址格式
     *
     * @param s 字符串
     * @return 符合url格式则返回true；否则返回false
     */
    public static boolean isURL(final String s) {
        return StringUtils.PATTERN_ISURL.matcher(s).matches();
    }

    /**
     * 根据字节长度截取字符串，截掉超过 len 个字节的部分
     *
     * @param s   字符串
     * @param len 最大长度
     * @return 该字符串前len个字节的字串；字符串为null则返回空字符串
     */
    public static String left(String s, int len) {
        if (s == null) return "";
        int j = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 255) {
                j += 2;
            } else {
                j++;
            }
            if (j <= len) {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 从指定位置si开始取得指定字符串中长度为len的子字符串
     *
     * @param s   字符串
     * @param si  开始位置（0开始）
     * @param len 长度
     * @return 子字符串
     */
    public static String substr(final String s, final int si, final int len) {
        if (StringUtils.hasText(s)) {
            int length = s.length();
            int ei = si + len;
            if (length >= ei) {
                return s.substring(si, ei);
            } else {
                return s;
            }
        } else {
            return "";
        }
    }

    /**
     * 取正整数
     *
     * @param s
     * @return
     */
    public static int intPositiveValue(final String s) {
        int retInt = StringUtils.intValue(s, 10);
        if (retInt < 0) {
            retInt = -retInt;
        }
        return retInt;
    }

    /**
     * 按十进制返回一个字符串所表示的整数
     *
     * @param s 字符串
     * @return 字符串字面值所表示的整数值，不能解释为整数则返回0
     */
    public static int intValue(final String s) {
        return StringUtils.intValue(s, 10);
    }

    /**
     * 将字符串数组转换为整型数组，将数组中的每一个字符串分别进行转换
     *
     * @param ss 字符串数组
     * @return 整形数组
     */
    public static int[] intValues(String[] ss) {
        if (ss == null || ss.length < 1) return new int[0];
        int[] is = new int[ss.length];
        for (int i = 0; i < ss.length; i++) {
            is[i] = intValue(ss[i]);
        }
        return is;
    }

    /**
     * 按指定基数（进制）返回字符串表示的整数
     *
     * @param s 字符串
     * @param r 基数（进制）
     * @return 整数；不能按指定基数解释为整数的返回0
     */
    public static int intValue(final String s, final int r) {
        if (s == null) {
            return 0;
        }
        int i = 0;
        try {
            i = Integer.parseInt(s, r);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }


    /**
     * 转换为float
     * @param s
     * @return
     */
    public  static  float floatValue(final  String s){
        if (s == null) {
            return 0f;
        }
        float i = 0f;
        try {
            i = Float.parseFloat(s);
        } catch (Exception e) {
            i = 0f;
        }
        return i;
    }


    public static Integer intValueNull(final String s) {
        if (s == null) {
            return null;
        }
        Integer i = null;
        try {
            i = Integer.parseInt(s, 10);
        } catch (Exception e) {
            //i = 0;
            e.printStackTrace();
        }
        return i;
    }

    public static long longValue(final String s) {
        return StringUtils.longValue(s, 10);
    }

    public static long longValue(final String s, final int r) {
        if (s == null) {
            return 0;
        }
        long i = 0;
        try {
            i = Long.parseLong(s, r);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }


    public static String nvl(final String s) {
        return s == null ? "" : s.toString();
    }

    /**
     * 如果一个字符串是 null，则返回 空字符串
     */
    public static String nvl(final Object s) {
        return s == null ? "" : s.toString();
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 如果一个字符串是 null ，则返回指定字符串
     */
    public static String nvl(final String s, final String r) {
        return s == null ? r : s;
    }

    /**
     * 去除字符串的首尾空白字符
     */
    public static String trim(final String s) {
        if (!StringUtils.hasText(s)) {
            return "";
        }
        return StringUtils.PATTERN_TRIM.matcher(s).replaceAll("");
    }

    /**
     * 去除字符串的首部空白字符
     */
    public static String trimLeft(final String s) {
        if (!StringUtils.hasText(s)) {
            return "";
        }
        return StringUtils.PATTERN_TRIM_LEFT.matcher(s).replaceAll("");
    }

    /**
     * 去除字符串的尾部空白字符
     */
    public static String trimRight(final String s) {
        if (!StringUtils.hasText(s)) {
            return "";
        }
        return StringUtils.PATTERN_TRIM_RIGHT.matcher(s).replaceAll("");
    }

    /**
     * 处理字符串，将其中的 html 字符（&amp; , &lt; , &gt; , &quot）分别编码为（&amp;amp; &amp;lt; &amp;gt; &amp;quot;）；回车换行符替换为空格
     *
     * @return 处理后的结果
     */
    public static String htmlEncode(final String s) {
        return StringUtils.htmlEncode(s, false);
    }

    /**
     * 处理字符串，将其中的 html 字符（& , < , > , "）分别编码为（&amp;amp; &amp;lt; &amp;gt; &amp;quot;）
     *
     * @param s 字符串
     * @param v 回车换行的处理方式，如果v为true，将换行符转换为&lt;br/&gt;，将空格转换为&amp;nbsp;；否则将回车替换为空格
     * @return 处理后的结果
     */
    public static String htmlEncode(String s, final boolean v) {
        if (s == null) {
            return "";
        }
        s = s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\\\"", "&quot;");
        if (v) {
            s = s.replaceAll("\\n|(\\n\\r)|(\\r\\n)", "<br/>");
            s = s.replaceAll("\\s", "&nbsp;");
        } else {
            s = s.replaceAll("\\s*(\\n+|(\\n\\r)+|(\\r\\n)+)\\s*", " ");
        }
        return s;
    }

    /**
     * 处理字符串。
     * 如果第二个参数为false，则返回 StringUtils.htmlEncode(s, true) 的结果；否则只将回车替换为&lt;br&gt; 然后返回
     */
    public static String textareaFilter(String s, boolean html) {
        if (!html) {
            return StringUtils.htmlEncode(s, true);
        } else {
            if (s == null) {
                return "";
            }
            s = s.replaceAll("\\n|(\\n\\r)|(\\r\\n)", "<br/>");
            return s;
        }
    }

    public static String getString(String content) {
        if (content == null || content.trim().equals("")) {
            return "";
        }
        content = content.replaceAll("</?[^>]+>", "");   //剔出了<html>的标签
        content = content.replace("&nbsp;", "");
        content = content.replace(".", "．");
        content = content.replace("\"", "‘");
        content = content.replace("'", "‘");
        content = content.replaceAll("\\s*|\t|\r|\n", "");//去除字符串中的空格,回车,换行符,制表符
        return content;
    }

    /**
     * 将字符串编码为适合在JavaScript中使用的字符串；将其中的换行符替换为空格
     *
     * @param s
     * @return
     */
    public static String jsEncode(String s) {
        if (s == null) {
            return "";
        }
        s = s.replace("\\", "\\\\");
        s = s.replace("\"", "\\\"");
        s = s.replace("'", "\\'");
        s = s.replaceAll("\\n+|(\\n\\r)+|(\\r\\n)+|\\r+", " ");
        return s;
    }

    /**
     * 使用 GBK 编码机制将字符串转换为 application/x-www-form-urlencoded 格式
     */
    public static String urlEncode(final String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, "GBK");
        } catch (Exception e) {
            return s;
        }
    }

    /**
     * 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded 格式
     */
    public static String urlEncode(final String s, final String charset) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, charset);
        } catch (Exception e) {
            return s;
        }
    }

    /**
     * 获取指定日期的整数表示。如 2010年2月12日 表示为整数 20100212
     */
    public static int intDate(final Date date) {
        if (date == null) {
            return 0;
        }
        int r = 0;
        r = (date.getYear() + 1900) * 10000 + (date.getMonth() + 1) * 100 + date.getDate();
        return r;
    }

    /**
     * 获取指定日期时间的整数表示。如某日的下午14：33：22 表示为 143322
     *
     * @param date
     * @return
     */
    public static int intTime(final Date date) {
        if (date == null) {
            return 0;
        }
        int r = 0;
        r = date.getHours() * 10000 + date.getMinutes() * 100 + date.getSeconds();
        return r;
    }

    /**
     * 按照 yyyy-MM-dd 格式 讲 日期字符串转换为 Date对象
     *
     * @param dstr 字符串
     * @return Date对象；如果指定字符串不能解释为yyyy-MM-dd格式日期，返回null
     */
    public static Date parseDate(final String dstr) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(dstr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 把整数转换成日期
     *
     * @param dint 日期的整数表示
     * @param tint 时间的整数表示
     * @return Date对象
     */
    public static Date dateInt(int dint, int tint) {
        if (dint < 19000000 || tint < 0) {
            return null;
        }
        int dd = dint % 100;
        dint = dint / 100;
        int mm = dint % 100 - 1;
        dint = dint / 100;
        int yy = dint;

        int ss = tint % 100;
        tint = tint / 100;
        int mi = tint % 100;
        tint = tint / 100;
        int hh = tint;
        return new GregorianCalendar(yy, mm, dd, hh, mi, ss).getTime();
    }

    /**
     * 格式化日期 为： yyyy-MM-dd 格式字符串
     *
     * @param date Date对象
     * @return yyyy-MM-dd格式字符串；如果date为null，返回 “未知日期”
     */
    public static String formatDate(final Date date) {
        if (date == null) {
            return "未知日期";
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 格式化日期 为： yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date Date对象
     * @return yyyy-MM-dd HH:mm:ss 格式字符串；如果date为null，返回 “未知日期”
     */
    public static String formatDatetime(final Date date) {
        if (date == null) {
            return "未知日期";
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    /**
     * 转换IP地址，将后二段显示为 *.*
     *
     * @param ip 给定IP字符串
     */
    public static String ipView(String ip) {
        if (StringUtils.hasText(ip)) {
            String[] ss = ip.split("\\.");
            if (ss.length > 3) {
                ip = ss[0] + "." + ss[1] + ".*.*";
            }

        } else {
            ip = "";
        }
        return ip;
    }

    /**
     * 将字符串编码为字节数组，使用GBK编码；如果字符串为null，则返回0长度数组
     */
    public static byte[] getBytes(final String s) {
        if (s == null) {
            return new byte[0];
        }
        if (s.length() < 1) {
            return new byte[0];
        }
        try {
            return s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            return s.getBytes();
        }
    }

    /**
     * 将字节数组编码为字符串，使用GBK编码
     */
    public static String toString(final byte[] bs) {
        try {
            return new String(bs, "GBK");
        } catch (UnsupportedEncodingException e) {
            return new String(bs);
        }
    }

    /**
     * 将字符串数组使用指定连接符号连接成一个字符串
     *
     * @param ss        字符串数组
     * @param connector 连接符，可以是任意字符串
     * @return 结果字符串
     */
    public static String concat(final String[] ss, final String connector) {
        if (ss == null) {
            return "";
        } else if (ss.length < 1) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(ss[0]);
            for (int i = 1; i < ss.length; i++) {
                sb.append(connector).append(ss[i]);
            }
            return sb.toString();
        }
    }

    @SuppressWarnings("unchecked")
    public static String concat(final Enumeration ss, final String connector) {
        if (ss == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isfirst = true;
            while (ss.hasMoreElements()) {
                Object o = ss.nextElement();
                if (!isfirst) {
                    sb.append(connector);
                } else {
                    isfirst = false;
                }
                sb.append(o);
            }
            return sb.toString();
        }
    }

    public static String getHostFromUrl(String url) {
        if (url == null) return "";
        int i = url.indexOf("://");
        if ("http".equalsIgnoreCase(url.substring(0, 4)) && i >= 4 && i <= 5) {
            url = url.substring(i + 3);
        }
        i = url.indexOf("/");
        if (i > 0) {
            url = url.substring(0, i);
        }
        return url;
    }

    /**
     * 将浮点数格式化为 小数点后保留2位的格式
     *
     * @param f
     * @return
     */
    public static String formatFloat(final float f) {
        DecimalFormat dnf = new DecimalFormat("#0.00");
        return dnf.format(f);
    }

    /**
     * 将字符串以指定子字符串分割为多个子字符串，返回分割后的数组
     */
    public static String[] split(final String str, final String connector) {
        if (!StringUtils.hasText(str)) {
            return new String[0];
        }
        ArrayList<String> ss = new ArrayList<String>();
        int i = 0, j = 0;
        while ((j = str.indexOf(connector, i)) > -1) {
            ss.add(str.substring(i, j));
            i = j + 1;
        }
        ss.add(str.substring(i));
        return ss.toArray(new String[0]);
    }

    /**
     * 将指定字符串进行md5加密
     */
    public static String md5(final String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0").append(Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        return hexString.toString();
    }

    /**
     * 将一个Map输出为 一个 json 字符串；这是一个不完整的方法。仅适用于 Map 中只包含简单字符串的情况。
     * 请不要将此方法用于更多用途。
     */
    public static String getJSON(Map map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean commaFlag = false;
        for (Object key : map.keySet()) {
            if (commaFlag) {
                sb.append(",");
            } else {
                commaFlag = true;
            }
            sb.append(key).append(":\"");
            sb.append(jsEncode(map.get(key).toString()));
            sb.append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 取得一个字符的16进制编码
     */
    public static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase();
    }

    /**
     * 将指定字符串进行编码。
     * 将字符串中的字符转换为Unicode编码的格式
     *
     * @param string
     * @return
     */
    public static String encodeString(String string) {
        if (string == null) return "null";
        StringBuilder sbr = new StringBuilder(string.length() * 4);
        sbr.append("'");
        for (int i = 0, sz = string.length(); i < sz; i++) {
            char ch = string.charAt(i);
            // handle unicode
            if (ch > 0xfff) {
                sbr.append("\\u");
                sbr.append(hex(ch));
            } else if (ch > 0xff) {
                sbr.append("\\u0");
                sbr.append(hex(ch));
            } else if (ch > 0x7f) {
                sbr.append("\\u00");
                sbr.append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        sbr.append('\\');
                        sbr.append('b');
                        break;
                    case '\n':
                        sbr.append('\\');
                        sbr.append('n');
                        break;
                    case '\t':
                        sbr.append('\\');
                        sbr.append('t');
                        break;
                    case '\f':
                        sbr.append('\\');
                        sbr.append('f');
                        break;
                    case '\r':
                        sbr.append('\\');
                        sbr.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            sbr.append("\\u00");
                            sbr.append(hex(ch));
                        } else {
                            sbr.append("\\u000");
                            sbr.append(hex(ch));
                        }
                        break;
                }
            } else {
                // line.
                switch (ch) {
                    case '\'':
                        sbr.append('\\');
                        sbr.append('\'');
                        break;
                    case '"':
                        sbr.append('\\');
                        sbr.append('"');
                        break;
                    case '\\':
                        sbr.append('\\');
                        sbr.append('\\');
                        break;
                    default:
                        sbr.append(ch);
                        break;
                }
            }
        }
        sbr.append("'");
        return sbr.toString();
    }

    /**
     * 将日期格式化为 MM-dd 格式
     */
    public static String formatMmdd(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 返回字符串的长度
     */
    public static int length(String s) {
        return (s == null ? 0 : s.length());
    }

    public static String randomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int t = random.nextInt(3);
            if (i == 0 || t == 1) {
                sb.append((char) (random.nextInt(26) + 65));
            } else if (t == 0) {
                sb.append((char) (random.nextInt(10) + 48));
            } else {
                sb.append((char) (random.nextInt(26) + 97));
            }
        }
        return sb.toString();
    }

    /**
     * 判断一个输入的：yyyy-MM-dd格式的日期是否已经过期
     *
     * @param DATE1
     * @return
     */
    public static String compare_date(String DATE1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = new Date();
            if (dt1.getTime() < dt2.getTime()) {//已经过期
                return "-";
            } else if (dt1.getTime() > dt2.getTime()) {//未过期
                return "";
            } else {
                return "";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "";
    }

    /**
     * 得到客户端IP
     *
     * @param request
     * @return
     */
    public final static String getIp(HttpServletRequest request) {
//      HttpServletRequest request = new HttpServletRequest();
//		HttpServletRequest request = ServletContext.getRequest();
//		javax.servlet.ServletContext context =  javax.servlet.ServletContext;
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public final static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    /**
     * 是否找到相关信息
     *
     * @param inv 被找的字符串
     * @param reg 要找的字符串（ 正则)
     * @return
     */
    public final static boolean exist(String inv, String reg) {
        if (inv == null || inv.trim().equals("") || reg == null || reg.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(inv);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否包含电话 0571-85120488,85120499
     *
     * @param inv
     * @return
     */
    public static boolean exitTel(String inv) {
        return exist(inv, "(\\d{4}|\\d{3})-(\\d{7,8}|\\d{7,8})");
    }

    /**
     * 判断是否包含手机 133571285477
     *
     * @param inv
     * @return
     */
    public static boolean exitMobile(String inv) {
        return exist(inv, "(1)(\\d{10})");
    }

    /**
     * 判断是否包含 email
     *
     * @param inv
     * @return
     */
    public static boolean exitEmail(String inv) {
        return exist(inv, "[\\w\\.-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,4}");
    }

    /**
     * 判断是否包含 域名
     *
     * @param inv
     * @return
     */
    public static boolean exitUrl(String inv) {
//		return exist(inv, "([-a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{1,4}");
        return exist(inv, "([-a-zA-Z0-9]+\\.)+(com|net|org|mobi|info|biz|cc|tv|asia|me|travel|tel|name|co|so|cn|gov|hk){1}");
    }

    /**
     * 寻找第一个图片地址
     *
     * @param inv
     * @return
     **/
    public static String findFirstImgUrl(String inv) {
        if (inv == null || inv.trim().equals("")) {
            return "";
        }
//		inv = inv.replaceAll("\n", "").replaceAll("\r", "");
        String retStr = "";
        //String reg = "src=(.+?.(jpg|gif|png|bmp|jpeg))";
        String reg = "(?i)<img([^>]+?)src=(.+?.(\\.jpg|\\.gif|\\.png|\\.bmp|\\.jpeg))(.+?)>";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(inv);
        while (m.find()) {
            int index = m.start(2);
            int end = m.end(3);
            retStr = inv.substring(index, end);
            retStr = retStr.replace("\"", "").replace("'", "").trim();
            return retStr;
        }
        return retStr;
    }

    /**
     * 寻找第一个视频地址
     *
     * @param inv
     * @return
     **/
    public static String findFirstVideoUrl(String inv) {
        if (inv == null || inv.trim().equals("")) {
            return "";
        }
        String retStr = "";
        //String reg = "src=(.+?.(\\.swf))";
//		String reg = "(?i)<embed (.+?)src=(.+?.(\\.swf))(.+?)>";
        String reg = "(?i)<embed ([^>]*?)src=(.+?.(\\.swf))(.+?)>";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(inv);
        while (m.find()) {
            int index = m.start(2);
            int end = m.end(3);
            retStr = inv.substring(index, end);
            retStr = retStr.replace("\"", "").replace("'", "").trim();
            return retStr;
        }
        return retStr;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String oldCharset, String newCharset) {
        if (str != null) {
            try {
                //用旧的字符编码解码字符串。解码可能会出现异常。
                byte[] bs = str.getBytes(oldCharset);
                //用新的字符编码生成字符串
                return new String(bs, newCharset);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return str;
    }

    public static long percentTotal(long nowNum, long total) {
        if (nowNum <= 0 || total <= 0){
            return 0;
        }
        if (nowNum == total){
            return 100;
        }
        return Math.round((Double.valueOf(nowNum) * 100 / Double.valueOf(total)));
    }

    /**
     * 判断是否在一个时间范围内
     *
     * @param now
     * @param start
     * @param end
     * @return
     */
    public static boolean isBetweenTime(String now, String start, String end) {
        if (now == null || start == null || end == null){
            return false;
        }
        Date nowD = DateUtilsPlus.strToDate(now);
        Date startD = DateUtilsPlus.strToDate(start);
        Date endD = DateUtilsPlus.strToDate(end);
        if (nowD == null || startD == null || endD == null){
            return false;
        }
        if (nowD.getTime() >= startD.getTime() && nowD.getTime() <= endD.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 以今天的0点为准
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isBetweenNowTime(String start, String end) {
        return isBetweenTime(StringUtils.formatDate(new Date()), start, end);
    }

    /**
     * 得到最短的关键词
     *
     * @param keys
     * @return
     */
    public static String findShortKey(String keys) {
        if (keys == null || keys.trim().equals("")) {
            return "";
        }
        keys = keys.replaceAll("，", ",");
        String[] keysArray = StringUtils.split(keys, ",");
        if (keysArray.length <= 1) {
            return StringUtils.nvl(keysArray[0]);
        }
        String minKey = "";
        for (int i = 0; i < keysArray.length; i++) {
            String tempKey = StringUtils.nvl(keysArray[i]).trim();
            if (tempKey.equals("")) {
                continue;
            }
            if (minKey.equals("")) {
                minKey = tempKey;
                continue;
            }
            if (minKey.length() > tempKey.length()) {
                minKey = tempKey;
            }
        }
        return minKey;
    }

    /**
     * 检查字符串是否是空值
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(final String s) {
        return s == null || s.length() < 1 || s.trim().length() < 1;
    }

    /**
     * 检查字符串是否是空值
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(final String s) {
        return !isEmpty(s);
    }

    /**
     * @param string
     * @param sbr
     */
    public static void appendJsEncoded(String string, Appendable sbr) throws IOException {
        sbr.append("'");
        for (int i = 0, sz = string.length(); i < sz; i++) {
            char ch = string.charAt(i);
            // handle unicode
            if (ch >= 0x4e00 && ch <= 0x9fa5) {
                sbr.append(ch);
            } else if (ch > 0xfff) {
                sbr.append("\\u");
                sbr.append(hex(ch));
            } else if (ch > 0xff) {
                sbr.append("\\u0");
                sbr.append(hex(ch));
            } else if (ch > 0x7f) {
                sbr.append("\\u00");
                sbr.append(hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        sbr.append('\\');
                        sbr.append('b');
                        break;
                    case '\n':
                        sbr.append('\\');
                        sbr.append('n');
                        break;
                    case '\t':
                        sbr.append('\\');
                        sbr.append('t');
                        break;
                    case '\f':
                        sbr.append('\\');
                        sbr.append('f');
                        break;
                    case '\r':
                        sbr.append('\\');
                        sbr.append('r');
                        break;
                    default:
                        if (ch > 0xf) {
                            sbr.append("\\u00");
                            sbr.append(hex(ch));
                        } else {
                            sbr.append("\\u000");
                            sbr.append(hex(ch));
                        }
                        break;
                }
            } else {
                // line.
                switch (ch) {
                    case '\'':
                        sbr.append('\\');
                        sbr.append('\'');
                        break;
                    case '"':
                        sbr.append('\\');
                        sbr.append('"');
                        break;
                    case '\\':
                        sbr.append('\\');
                        sbr.append('\\');
                        break;
                    default:
                        sbr.append(ch);
                        break;
                }
            }
        }
        sbr.append("'");
    }


    public static Long[] strArrToLongArr(String[] strings) {
        if (strings == null){
            return null;
        }
        for (String s : strings) {
            if (!isNumeric(s) && !"".equals(s)) {
                throw new RuntimeException("不可转换为Long");
            }
        }
        Long[] longs = new Long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            longs[i] = longValue(strings[i]);
        }
        return longs;
    }

    public static String keywordFilter(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return "";
        }
        keyword = keyword.replaceAll("\\\\", " ");
        keyword = keyword.replaceAll("\\+", " ");
        keyword = keyword.replaceAll("-", " ");
        keyword = keyword.replaceAll("&&", " ");
        keyword = keyword.replaceAll("\\|\\|", " ");
        keyword = keyword.replaceAll("!", " ");
        keyword = keyword.replaceAll("\\(", " ");
        keyword = keyword.replaceAll("\\)", " ");
        keyword = keyword.replaceAll("\\{", " ");
        keyword = keyword.replaceAll("\\}", " ");
        keyword = keyword.replaceAll("\\[", " ");
        keyword = keyword.replaceAll("\\]", " ");
        keyword = keyword.replaceAll("\\^", " ");
        keyword = keyword.replaceAll("\"", " ");
        keyword = keyword.replaceAll("\\~", " ");
        keyword = keyword.replaceAll("\\*", " ");
        keyword = keyword.replaceAll("\\?", " ");
        keyword = keyword.replaceAll("\\:", " ");
        return keyword;
    }

    /**
     *判断一个字符串 是否是小数
     * @param num
     * @return
     */
    public static boolean judgeIsDecimal(String num){
        boolean isdecimal = false;
        if (num.contains(".")) {
            isdecimal=true;
        }
        return isdecimal;
    }

    /**
     * 判断是否是个json
     * @param jsonInString
     * @return
     */
    public final static boolean isJSON(String jsonInString) {
        try {
            Gson gson = new Gson();
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }

    /**
     * 手机号 中间4位使用*替换
     * @param phone
     * @return
     */
    public static String middleReplaceStar(String phone) {
        String result = null;
        if (!StringUtils.isEmpty(phone)) {
            if (phone.length() < 7) {
                result = phone;
            } else {
                String start = phone.substring(0, 3);
                String end = phone.substring(phone.length() - 4, phone.length());
                StringBuilder sb = new StringBuilder();
                sb.append(start).append("****").append(end);
                result = sb.toString();
            }
        }
        return result;
    }

    /**
     * 获取一定长度的随机字符串，范围0-9，a-z
     *
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}