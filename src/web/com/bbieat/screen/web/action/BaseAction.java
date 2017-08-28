package com.bbieat.screen.web.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.holley.eemas.util.CommonUtil;
import com.holley.eemas.util.Constants;
import com.holley.eemas.util.DateUtil;
import com.holley.eemas.util.FileUtil;
import com.holley.eemas.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    private static final long  serialVersionUID = 7939919808653183874L;

    public static Logger       log              = Logger.getLogger(BaseAction.class);

    public HttpServletRequest  request;

    public HttpServletResponse response;

    public HttpSession         session;

    public void setServletRequest(HttpServletRequest req) {
        this.request = req;
        this.session = req.getSession();
    }

    public void setServletResponse(HttpServletResponse rep) {
        this.response = rep;
    }

    public String init() {
        return SUCCESS;
    }

    /** ExtGrid 参数 **/
    public String start, limit, sort, dir, isExcel, excelHeads, yhlx, excelName;
    /** 节点参数 **/
    protected String nodeId, nodeType, nodeText, nodeDwdm;
    /** 统计参数 **/
    protected String statDwdm, statJb, sfxz;
    /** 报表参数 **/
    protected String reportDwdm, reportJb;

    public String    jsessionid;

    /**
     * 返回 字符串
     * 
     * @param text
     * @param utf8
     */
    protected String responseText(String text, boolean utf8) {
        response.setContentType("text/html; charset=UTF-8");
        try {
            if (utf8) {
                response.getOutputStream().write(StringUtil.getUTF8Bytes(text));
            } else {
                response.getWriter().write(text);
            }
            log.debug("txt:" + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回json 供Ext使用
     * 
     * @param obj
     * @param isStore
     */
    protected String responseJson(Object obj, boolean isStore) {
        try {
            response.setContentType("text/json; charset=UTF-8");
            JSONArray jsonarray = JSONArray.fromObject(obj);
            String json = jsonarray.toString();
            if (isStore) {
                json = json.substring(1, json.length() - 1);
            }
            log.debug("json:" + json);
            response.getOutputStream().write(StringUtil.getUTF8Bytes(json.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ExtGrid 返回查询
     * 
     * @param result
     * @param isExcel
     */
    protected String responseGrid(Map<String, Object> result) {
        if (StringUtil.isEs(isExcel)) {
            responseJson(result, true);
        } else {
            responseFile(Constants.TEMPFILE, (String) result.get("fileName"), "xls", true);
        }
        return null;
    }

    protected String responseFile(String path, String fileName, String suffix, boolean del) {
        try {
            if ("xls".equals(suffix) || "csv".equals(suffix)) {
                response.setContentType("application/excel");
            } else if ("doc".equals(suffix)) {
                response.setContentType("application/msword");
            } else {
                response.setContentType("application/octet-streem");
            }
            this.response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"");
            response.setCharacterEncoding("UTF-8");
            FileUtil.downFile(path, fileName, response.getOutputStream(), del);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 统计返回
     * 
     * @param result
     * @param xlsName
     * @return
     */
    protected String responseStat(Map<String, Object> result, String xlsName, String re, String moudle) {
        if (StringUtil.isEs(isExcel)) {
            // request.setAttribute("result", result.get("result")); // 结果
            // request.setAttribute("xml", result.get("xml")); // 图形
            for (String key : result.keySet()) { // 存在返回多个列表和多个图形的需求
                request.setAttribute(key, result.get(key));
            }
            return re;
        } else {
            return null;
        }
    }

    // 统计过滤
    protected Map<String, Object> statFilter() {
        Map<String, Object> obj = CommonUtil.getParams(this);
        if (StringUtil.isEs(statDwdm)) {
            statDwdm = "0";
        }

        if (StringUtil.isEs(statJb)) {
            statJb = "1";
        }
        obj.put("statJb", statJb);
        obj.put("statDwdm", statDwdm);
        return obj;
    }

    // 根据起止时间、数据表类型（分年表、分月表）在参数集合中加入表名集合
    protected void putDateParams(Map<String, Object> obj) {
        if (obj != null && obj.containsKey("date1") && obj.containsKey("date2")) {
            String dateStr1 = (String) obj.get("date1");
            String dateStr2 = (String) obj.get("date2");

            Date date1 = DateUtil.string2Calendar(dateStr1).getTime();
            Date date2 = DateUtil.string2Calendar(dateStr2).getTime();
            List<String> yeartableNameList = DateUtil.getYearBetween(date1, date2, "yy");
            obj.put("yearTableNameList", yeartableNameList);
            obj.put("yearTableSize", yeartableNameList.size());
            List<String> monthTableNameList = DateUtil.getMonthBetween(date1, date2, "yyMM");
            obj.put("monthTableNameList", monthTableNameList);
            obj.put("monthTableSize", monthTableNameList.size());

            obj.put("date1", date1);
            obj.put("date2", date2);
        }
    }

    protected Map<String, Object> getRequestParams() {
        Map<String, Object> obj = new HashMap<String, Object>();
        Enumeration<String> enumer = this.request.getParameterNames();
        while (enumer.hasMoreElements()) {
            String paramName = enumer.nextElement();
            String paramValue = request.getParameter(paramName);
            if (StringUtil.isNes(paramValue)) {
                obj.put(paramName, paramValue);
            }
        }
        putDateParams(obj);
        return obj;
    }

    protected Map<String, Object> getParams() {
        Map<String, Object> obj = CommonUtil.getParams(this);

        return obj;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getIsExcel() {
        return isExcel;
    }

    public void setIsExcel(String isExcel) {
        this.isExcel = isExcel;
    }

    public String getExcelHeads() {
        return excelHeads;
    }

    public void setExcelHeads(String excelHeads) {
        this.excelHeads = excelHeads;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeText() {
        return nodeText;
    }

    public void setNodeText(String nodeText) {
        this.nodeText = nodeText;
    }

    public String getNodeDwdm() {
        return nodeDwdm;
    }

    public void setNodeDwdm(String nodeDwdm) {
        this.nodeDwdm = nodeDwdm;
    }

    public String getStatDwdm() {
        return statDwdm;
    }

    public void setStatDwdm(String statDwdm) {
        this.statDwdm = statDwdm;
    }

    public String getStatJb() {
        return statJb;
    }

    public void setStatJb(String statJb) {
        this.statJb = statJb;
    }

    public String getSfxz() {
        return sfxz;
    }

    public void setSfxz(String sfxz) {
        this.sfxz = sfxz;
    }

    public String getYhlx() {
        return yhlx;
    }

    public void setYhlx(String yhlx) {
        this.yhlx = yhlx;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getReportDwdm() {
        return reportDwdm;
    }

    public void setReportDwdm(String reportDwdm) {
        this.reportDwdm = reportDwdm;
    }

    public String getReportJb() {
        return reportJb;
    }

    public void setReportJb(String reportJb) {
        this.reportJb = reportJb;
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

}
