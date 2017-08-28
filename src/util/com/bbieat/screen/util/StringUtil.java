package com.bbieat.screen.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 字符串处理工具方法
 */
public class StringUtil {

    /**
     * 根据分隔符，返回字符串数组
     * 
     * @param srcStr 源字符串
     * @param delimiters 分隔符
     * @return 字符串数组
     */
    public static String[] getStrArrayBySpit(String srcStr, String delimiters) {
        String[] result = null;
        int index = 0;
        if (srcStr != null) {
            StringTokenizer stk = new StringTokenizer(srcStr, delimiters);

            if (stk.hasMoreTokens()) result = new String[stk.countTokens()];
            while (stk.hasMoreTokens()) {
                result[index] = stk.nextToken();
                index++;
            }
        }
        return result;
    }

    /**
     * 替换字符串
     * 
     * @param line --- 原字符串
     * @param oldString 需要替换的字符串
     * @param newString 替换进去的字符串
     * @return string
     */
    public static final String replace(String line, String oldString, String newString) {
        if (line == null) {
            return null;
        }
        int i = 0;
        i = line.indexOf(oldString, i);
        if (i >= 0) {
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 判断字符串参数是否为空
     * 
     * @param s
     * @return boolean
     */
    public static final boolean isEs(String s) {
        return s == null || s.trim().equals("") || s.equals("null") || s.equals("NULL") || s.trim().equals("undefined")
               || "\"null\"".equals(s) || "[]".equals(s);
    }

    public static final boolean isNes(String s) {
        return !isEs(s);
    }

    /**
     * 把字符串参数转换为utf-8格式
     * 
     * @param src
     * @return byte
     */
    public static byte[] getUTF8Bytes(String src) {
        try {
            return src.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }

    /**
     * 字符串转义
     * 
     * @param src
     * @return string
     */
    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16) tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    /**
     * 字符串反转义
     * 
     * @param src
     * @return string
     */
    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 数字格式化函数加上百分位
     * 
     * @param number : 格式化前的数字;
     * @param decimalDigits : 小数位数;
     * @return: 三位一组以逗号分割的字符串;
     */
    public static String doubleFormat(double number, int decimalDigits) {
        if (number == 0d) {
            number = 0d;
        }

        boolean flag = false;
        if (decimalDigits < 0) {
            // 小数位数不能小于0.
            return "";
        }

        String pattern = "##################";
        if (decimalDigits > 0) {
            flag = true;
            pattern += ".";
            for (int i = 0; i < decimalDigits; i++) {
                pattern += "0";
            }
        }

        DecimalFormat df = new DecimalFormat(pattern);
        if (number <= -1d) {
            return df.format(number);
        } else if (number > -1d && number < 0d) {
            return "-0" + df.format(number).substring(1);
        } else if (number >= 0d && number < 1d) {
            if (flag) {
                return "0" + df.format(number);
            } else {
                return df.format(number);
            }
        } else {
            return df.format(number);
        }
    }

    public static byte[] zipString(String s) {
        if (s == null) return null;
        BufferedInputStream in = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream out = null;
        try {
            byte[] bytes = s.getBytes("GBK");
            in = new BufferedInputStream(new ByteArrayInputStream(bytes));
            baos = new ByteArrayOutputStream();
            out = new BufferedOutputStream(new GZIPOutputStream(baos));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 解压String
     */
    public static String unzipString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        BufferedInputStream in = null;
        ByteArrayOutputStream baos = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(bytes)));
            baos = new ByteArrayOutputStream();
            out = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bytes != null) {
            try {
                return new String(bytes, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    /**
     * 将字符串格式化成HTML格式
     * 
     * @param s
     * @return
     */
    public static String stringToHtmlFormat(String s) {
        if (s == null) return null;
        String temp = replace(s, "\n", "<br>");
        temp = replace(temp, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        return replace(temp, " ", "&nbsp;");
    }

    /**
     * 去除左边的'0'字符
     * 
     * @param str
     * @return
     */
    public static String leftTrimZero(String str) {
        int len = str.length();
        int st = 0;
        char[] val = str.toCharArray();
        while ((st < len) && (val[st] == '0')) {
            st++;
        }
        return (st < len + 1) ? str.substring(st) : str;
    }

    /**
     * 数字字符去除左边的'0'字符
     */
    public static String leftTrimZeroS(String str) {
        if ("0".equals(str) || isEs(str)) return str;
        String result = leftTrimZero(str);
        if (result.length() == 0 || result.charAt(0) == '.') result = "0" + result;
        return result;
    }

    /**
     * 根据头尾标志获取子字符串
     * 
     * @param srcStr 源字符串
     * @param headToken 头字符串标志
     * @param tailToken 尾字符串标志
     * @return 子字符串
     */
    public static String getSubStrByToken(String srcStr, String headToken, String tailToken) {
        String result = "";
        int startPos = srcStr.indexOf(headToken);
        int endPos = srcStr.indexOf(tailToken);
        if (startPos >= 0 && endPos > 0 && startPos < endPos) {
            result = srcStr.substring(startPos + headToken.length(), endPos);
        }
        return result;

    }

    /**
     * 十进制的IP地址转化为十六进制的IP
     * 
     * @author zhangyb
     * @param ip
     * @return
     */
    public static String iPToHexStr(String ip) {
        String hexStr = null;
        String[] ips = getStrArrayBySpit(ip, ".");
        for (int i = 0; i < ips.length; i++) {
            hexStr = hexStr.concat(str2Hex(ips[i]));
        }
        return hexStr;
    }

    /**
     * 十六进制的IP地址转化为十进制的IP
     * 
     * @param hexStr
     * @return
     */
    public static String hexStrToIP(String hexStr) {
        String ip = null;
        String subIP = "";
        if (hexStr.length() == 8) {
            for (int i = 0; i < 4; i++) {
                subIP = hexStr.substring(i * 2, (i + 1) * 2);
                if (i == 0) ip = String.valueOf(Integer.parseInt(subIP, 16));
                else ip = ip + "." + hex2Str(subIP);
            }
        }
        return ip;
    }

    /**
     * 判断字符串是否存在给定的字符串数组里边
     * 
     * @param str
     * @param aStr
     * @return
     */
    public static boolean isSub(String str, String[] aStr) {
        boolean isSub = false;
        if (aStr != null) {
            for (int i = 0; i < aStr.length; i++) {
                if (str.equalsIgnoreCase(aStr[i])) {
                    isSub = true;
                    break;
                }
            }
        }
        return isSub;
    }

    /**
     * 由字符串数组转化为字符串,以","为分割
     * 
     * @param array
     * @return
     */
    public static String array2String(String[] strArray) {
        String result = "";
        if (strArray != null && strArray.length > 0) {
            for (int i = 0; i < strArray.length; i++) {
                if (i > 0) result = result + "," + strArray[i];
                else result = strArray[i];
            }

        }
        return result;
    }

    /**
     * 加”’“
     * 
     * @param s
     * @return
     */
    public static String quoted(String s) {
        if (s != null) return "'" + s + "'";
        else return null;
    }

    /**
     * 产生随机数(scale为位数)
     * 
     * @param scale
     * @return
     */
    public static String randomNumber(int scale) {
        int result;
        int max = new Double(Math.pow(10, scale)).intValue();
        int min = new Double(Math.pow(10, scale - 1)).intValue();
        Random r = new Random();
        while (true) {
            result = r.nextInt(max) % max;
            if (result > min) {
                return String.valueOf(result);
            }
        }
    }

    /** 从某字串删除指定字串 */
    public static String removeSubstring(String main, String sub) {
        int pos = main.indexOf(sub);

        return pos != -1 ? main.substring(0, pos) + main.substring(pos + sub.length()) : main;
    }

    /**
     * SQL jdbc map 结果转字符串
     * 
     * @param obj
     * @return
     */
    public static String getValue(Object obj) {
        if (obj == null) return "";
        if (obj instanceof BigDecimal) {
            return String.valueOf(obj.toString());
        }
        if (obj instanceof Integer) {
            return String.valueOf(obj.toString());
        }
        if (obj instanceof Double) {
            return String.valueOf(obj.toString());
        }
        if (obj instanceof String) {
            if (isEs(obj.toString())) {
                return "";
            } else {
                return obj.toString();
            }
        }
        return "";
    }

    /**
     * 十六进制转化为十进制
     * 
     * @param hexStr
     * @return
     */
    public static String hex2Str(String hexStr) {
        String str = null;
        str = String.valueOf(Integer.parseInt(hexStr, 16));
        return str;
    }

    /**
     * 十进制转化为十六进制
     * 
     * @param str
     * @return
     */
    public static String str2Hex(String str) {
        String hexStr = "";
        hexStr = Integer.toHexString(Integer.parseInt(str));
        if (hexStr.length() < 2) hexStr = "0" + hexStr;
        return hexStr;
    }

    /**
     * 2进制转16进制
     * 
     * @param str
     * @param len
     * @return
     */
    public static String bin2Hex(String str, int len) {
        long i = Long.valueOf(str, 2);
        return leftZero(Long.toHexString(i).toUpperCase(), len);
    }

    /**
     * 16进制转2进制
     * 
     * @param hex
     * @return
     */
    public static String hex2Bin(String hex, int len) {
        long i = Long.valueOf(hex, 16);
        String instr = Long.toBinaryString(i);
        if (instr.length() < len) {
            for (int j = instr.length(); j < len; j++) {
                instr = "0" + instr;
            }
        }
        return instr;
    }

    /**
     * 左补零
     * 
     * @param instr
     * @param len
     * @return
     */
    public static String leftZero(String instr, int len) {
        String str = "";
        if (StringUtil.isEs(instr)) {
            for (int j = 0; j < len; j++) {
                str = "0" + str;
            }
            return str;
        }
        if (instr.length() < len) {
            for (int j = instr.length(); j < len; j++) {
                instr = "0" + instr;
            }
        }
        return instr;
    }

    /**
     * 右补零
     * 
     * @param instr
     * @param len
     * @return
     */
    public static String rightZero(String instr, int len) {
        String str = "";
        if (StringUtil.isEs(instr)) {
            for (int j = 0; j < len; j++) {
                str += "0";
            }
            return str;
        }
        if (instr.length() < len) {
            for (int j = instr.length(); j < len; j++) {
                instr += "0";
            }
        }
        return instr;
    }

    public static String bigDecimal2String(BigDecimal bg) {
        String result = "";
        if (bg != null) result = bg.toString();
        return result;
    }

    /**
     * 对2进制字符串转10进制字符串
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String binStrToStr(String str, int start, int end) {
        return Integer.toString(Integer.parseInt(str.substring(start, end), 2));
    }

    /**
     * 字符串变成二进制后左边补零 instr 输入字符串 len补全位数
     */
    public static String strToBinStr(String instr, int len) {
        String str = "";
        if (StringUtil.isEs(instr)) {
            for (int j = 0; j < len; j++) {
                str = "0" + str;
            }
            return str;
        }
        int i = Integer.parseInt(instr);
        str = Integer.toBinaryString(i);
        if (str.length() < len) {
            for (int j = str.length(); j < len; j++) {
                str = "0" + str;
            }
        }
        return str;
    }

    /**
     * String 倒置 987654321 to 123456789
     * 
     * @param str
     * @return
     */
    public static String convertStr(String str) {
        String res = "";
        int i = str.length() - 1;
        while (i > -1) {
            res += str.charAt(i);
            i--;
        }
        return res;
    }

    /**
     * String类型数组转换int类型数组
     * 
     * @param strArr String类型数组
     * @return
     */
    public static int[] strToIntArray(String[] strArr) {
        if (null == strArr || 0 == strArr.length) return null;
        if (StringUtil.isEs(strArr[0])) return null;
        int[] intArr = null;
        try {
            intArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                intArr[i] = Integer.parseInt(strArr[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intArr;
    }

    /**
     * 得到一个数组里可以匹配的值在数组中的下标
     * 
     * @param strArray
     * @param compString
     * @return
     */
    public static int indexOfArray(String[] strArray, String compString) {
        int result = -1;

        for (int i = 0; strArray != null && i < strArray.length; i++) {
            if (!isEs(strArray[i]) && strArray[i].equals(compString)) {
                result = i;
                break;
            }
        }

        return result;
    }

    /**
     * jsp传到Action时字符编码转换
     * 
     * @param s
     * @return
     * @throws Exception
     */
    public static String ConvertUTF(String s) throws Exception {
        String result;
        byte[] temp;
        temp = s.getBytes("iso-8859-1");
        result = new String(temp, "UTF-8");

        return result;
    }

    /**
     * 删除最后n个字符 默认1
     * 
     * @param str
     * @return
     */
    public static String removeEndChar(String str, int n) {
        return isEs(str) ? str : str.substring(0, str.length() - n);
    }

    /**
     * 得到日的间隔点 num:多少个点（24，48，96）
     */
    public static String[] getInterval(int num) {
        String[] sjrq = new String[num];
        for (int i = 0; i < num; i++) {
            if (i < 10) {
                sjrq[i] = "0" + String.valueOf(i);
            } else {
                sjrq[i] = String.valueOf(i);
            }
        }
        return sjrq;
    }

    /**
     * 获取异常的完整信息
     * 
     * @param ex
     * @return
     */
    public static String getExceptionDetailInfo(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 按字节倒置
     * 
     * @param ex
     * @return
     */
    public static String ReverseStringByByte(String str) {
        String sOutput = "";
        try {
            if (str.length() % 2 == 0) {
                for (int i = 0; i < str.length() / 2; i++) {
                    sOutput = sOutput + str.substring((str.length() - (i + 1) * 2), (str.length() - i * 2));
                }
            }
        } catch (Exception e) {
        }
        return sOutput;
    }

    /**
     * 将字符串分割成几段
     * 
     * @param str
     * @param maxSize
     * @return
     */
    public static List<String> getStrArray(String str, String SPL, int maxSize) {
        int start = 0;
        int index = 0;
        int end = 0;
        List<String> ls = new ArrayList<String>();
        boolean bye = false;
        while (true) {
            while (index < maxSize) {
                int e = str.indexOf(SPL, end);
                if (e < 0) {
                    bye = true;
                    break;
                } else {
                    end = e + SPL.length();
                }
                index++;
            }
            if (bye) {
                String sub = str.substring(start);
                if (sub.length() > 0) ls.add(sub);
                break;
            }
            String sub = str.substring(start, end - SPL.length());
            if (sub.length() > 0) ls.add(sub);
            start = end;
            index = 0;
        }
        return ls;
    }

    public static boolean isChinese(String c) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(c);
        return m.find();
    }

    /**
     * 比较两字符串内容是否相同
     * 
     * @param con 常量值
     * @param var 变量值
     * @return
     */
    public static boolean eq(String con, String var) {
        return (var != null && con.equals(var));
    }

    /**
     * 两字符串内容是否相同
     * 
     * @param target 目标字符串
     * @param source 标准字符串
     * @return
     */
    public static boolean isEqual(Object target, String source) {
        if ((target == null) || !(target instanceof String)) return false;
        if (isEs(target.toString())) return false;
        if (source.equals(target)) return true;
        return false;
    }

    /**
     * 从短信内容中提取关键字
     * 
     * @param content
     * @return
     */
    public static List<String> getKeyWords(String content, String rex) {

        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(content);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            String key = matcher.group(2);
            if (!list.contains(key)) {
                list.add(key);
            }
        }
        return list;
    }

    /**
     * 分批加载左边树里的内容 当加载数据>size的时候 count 左边树加载分割成多少块 counts 每次往LIst加载的个数 size 每块分割有多少数据 控制类不导出Excel的时候用
     */
    public static String[] strDateToGrid(String zdjh, int count, int size) {
        List<String> result = new ArrayList<String>();

        String[] zdjhs = zdjh.split(",");
        int counts = 0;
        for (int i = count * size; i < zdjhs.length; i++) {
            counts++;
            if (counts > size) {
                break;
            }
            result.add(0, zdjhs[i]);
        }

        String[] newString = new String[result.size()];
        for (int j = 0; j < result.size(); j++) {
            newString[j] = (String) result.get(j);
        }

        return newString;
    }

    public static String[] strDateToGridForQz(String[] zdjhs, int count, int size) {
        List<String> result = new ArrayList<String>();
        int counts = 0;
        for (int i = count * size; i < zdjhs.length; i++) {
            counts++;
            if (counts > size) {
                break;
            }
            result.add(0, zdjhs[i]);
        }

        String[] newString = new String[result.size()];
        for (int j = 0; j < result.size(); j++) {
            newString[j] = (String) result.get(j);
        }
        return newString;
    }

    /**
     * 得到日的间隔点 24点
     */
    public static String[] get24D() {
        String[] sjrq = new String[24];
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                sjrq[i] = "0" + String.valueOf(i);
            } else {
                sjrq[i] = String.valueOf(i);
            }
        }
        return sjrq;
    }

    /**
     * 判空返回""
     * 
     * @param str
     * @return
     */
    public static String checkNull(String str) {
        if (str == null || str.length() == 0) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 特殊处理字符串
     * 
     * @param str 逗号隔开的字符串转化为'a','b'.....格式的字符串 ----sql用
     * @return string
     */
    public static String dealString(String str) {
        String[] array = str.split(",");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buffer.append(",'").append(array[i]).append("'");
        }
        String result = buffer.toString();
        if (result.length() > 1) result = result.substring(1);
        return result;
    }

    public static String getNumValue(Object obj) {
        String num = getValue(obj);
        if (isEs(num)) {
            return "0";
        }
        return num.trim();
    }

}
