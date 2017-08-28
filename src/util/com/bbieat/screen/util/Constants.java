package com.bbieat.screen.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 用来存放全局常量
 */
public class Constants {

    public static final String               CTX                        = "/amr";
    public static final String               SSID                       = "SSID";
    public static String                     REALPATH;

    /*-----------------系统会话变量---------------------------*/

    /** 登录操作员ID **/
    public static final String               CURR_STAFFID               = "CURR_STAFFID";
    /** 登录操作厂家权限 **/
    public static final String               CURR_FWCJ                  = "CURR_FWCJ";
    /** 当前文件 **/
    public static final String               CURR_FILE                  = "CURR_FILE";
    /** 当前查询信息 **/
    public static final String               QUERY_INFO                 = "CURR_QUERY";

    /*-----------------常用变量----------------------------*/
    // 用户类型
    public static final String               YHLX_ZB                    = "01";             // 专变
    public static final String               YHLX_GB                    = "02";             // 公变
    public static final String               YHLX_DY                    = "03";             // 低压

    // 规约类型
    public static final String               GYLX_ZG                    = "00";             // 浙规
    public static final String               GYLX_GW                    = "03";             // 国网
    // 规约类型 配变
    public static final String               GYLX_PB                    = "70";
    // 通讯方式 GPRS
    public static final int                  TXFS_GPRS                  = 1;
    // 通讯方式 SMS
    public static final int                  TXFS_SMS                   = 2;
    public static String                     CJJK_URL;                                      // URL$采集接口
    // 系统配置
    public static String                     TEMPFILE;                                      // 零时文件目录
    public static String                     EXCELTEMP;                                     // Excel模板目录
    public static String                     EXCELPATH;                                     // Excel下载目录
    public static String                     LOGGER                     = "C://sxeemas_log"; // 日志目录
    public static String                     OP_LOGGER;                                     // 操作日志记录

    public static String                     SSOURL;                                        // 营销地址
    public static String                     JCSSOURL;                                      // 稽查单点地址
    public static String                     JCFASURL;                                      // 稽查登录地址
    public static String                     FASURL;
    public static String                     FASAPPNAME;                                    // 单点登录
    public static String                     YXZYHYHRZURL;                                  // 营销专业化应用
    public static String                     ZZRWGL;                                        // 主站任务地址
    public static String                     ZJRWGL;                                        // 中继任务接口地址

    public static String                     YXJK_URL;                                      // 营销接口
    public static String                     PIJK_URL;                                      // PI接口
    public static String                     PMSJK_URL;                                     // PMS接口
    public static String                     SJFBJK_URL;                                    // 数据发布接口
    public static String                     TYPTJK_URL;                                    // 统一平台接口

    public static String                     CZSCPATH;                                      // 操作手册(图形软件、.net安装文件....)
    public static String                     FBJHPATH;                                      // 发布计划
    public static String                     WTJLPTWJ;                                      // 问题交流平台文件
    public static String                     ZCWJPATH;                                      // 政策文件目录
    public static String                     ZDSJPATH;                                      // 终端升级文件目录
    public static String                     TCKHZMCL;                                      // 剔除考核证明材料路径
    public static String                     YNWTZSK;                                       // 疑难问题知识库
    public static String                     HCDBPATH;                                      // 核查督办文件目录

    /** 日志类型 */
    // 设参
    public static String                     CZLX_SC                    = "01";
    // 任务
    public static String                     CZLX_RW                    = "02";
    // 终端控制
    public static String                     CZLX_KZ                    = "03";
    // 终端对时
    public static String                     CZLX_DS                    = "04";
    // 终端复位
    public static String                     CZLX_FW                    = "05";
    // 权限和密码管理
    public static String                     CZLX_QX                    = "08";
    // 电表对时
    public static String                     CZLX_DS_DB                 = "09";
    // 线路限额管理
    public static String                     CZLX_YP_XLXE               = "10";
    // 参考负荷设置
    public static String                     CZLX_CKFH                  = "11";

    // 短信发送类型 1－模版，2－订阅，3－自定义，4－自动装接，5－有序用电
    public static final String               DXFS_MB                    = "1";
    public static final String               DXFS_DY                    = "2";
    public static final String               DXFS_ZDY                   = "3";
    public static final String               DXFS_ZJ                    = "4";
    public static final String               DXFS_YXYD                  = "5";
    // 短信业务分类 01采集业务 02有序用电
    public static String                     DX_YWFL_CJ                 = "01";
    public static String                     DX_YWFL_YXYD               = "02";
    // 系统分类型01采集系统02公变监测系统03需求侧系统
    public static String                     DX_XTLX_ZB                 = "01";
    public static String                     DX_XTLX_GB                 = "02";
    public static String                     DX_XTLX_XQC                = "03";
    /** 采集异常运维状态 **/

    // 故障处理状态：01 新故障
    public static final String               GZ_GZCLZT_XGZ              = "01";
    // 故障处理状态：02 现场处理中
    public static final String               GZ_GZCLZT_XCCLZ            = "02";
    // 故障处理状态：03 营销处理中
    public static final String               GZ_GZCLZT_YXCLZ            = "03";
    // 故障处理状态：04 人工挂起(针对暂时处理不了的故障可以人为挂起)
    public static final String               GZ_GZCLZT_RGGQ             = "04";
    // 故障处理状态：05 系统挂起(对于故障升级，可以将老故障先系统挂起)
    public static final String               GZ_GZCLZT_XTGQ             = "05";
    // 故障处理状态：06 待归档
    public static final String               GZ_GZCLZT_DGD              = "06";
    // 故障处理状态：07 归档
    public static final String               GZ_GZCLZT_GD               = "07";
    public static final String               DEFUAL_ONE                 = "1";
    public static final String               DEFUAL_ZERO                = "0";

    // 流程操作类型：01新故障
    public static final String               LC_CZLX_XGZ                = "01";
    // 流程操作类型：02 现场处理中
    public static final String               LC_CZLX_XCCLZ              = "02";
    // 流程操作类型：03 营销处理中
    public static final String               LC_CZLX_YXCLZ              = "03";
    // 流程操作类型：04 人工挂起
    public static final String               LC_CZLX_RGGQ               = "04";
    // 流程操作类型：05 系统挂起
    public static final String               LC_CZLX_XTGQ               = "05";
    // 流程操作类型：06 待归档
    public static final String               LC_CZLX_DGD                = "06";
    // 流程操作类型：07 归档
    public static final String               LC_CZLX_GD                 = "07";
    // 流程操作类型：08 改派
    public static final String               LC_CZLX_GP                 = "08";
    // 流程操作类型：09 反馈
    public static final String               LC_CZLX_FK                 = "09";

    // 故障大类：终端故障
    public static final String               GZDL_ZDGZ                  = "01";

    // 故障大类：电表故障
    public static final String               GZDL_DBGZ                  = "02";

    // 故障类型:终端下电表全无数据
    public static final String               GZLX_ZDXDBQWSJ             = "02";

    public static final String               DEFAULT_TRUE               = "true";

    public static final String               DEFAULT_FALSE              = "false";

    /** 异常流程状态 **/
    public static final String               NEW_EXCEPTION              = "1";              // 新异常 故障
    public static final String               LOCAL_HANDEL               = "2";              // 现场处理
    public static final String               SEND_MARKETING             = "3";              // 营销处理
    public static final String               HOLD_ON                    = "4";              // 挂起
    public static final String               SAVE_RECORD                = "5";              // 待归档
    public static final String               FINAL_SAVE_RECORD          = "6";              // 归档

    public static int                        GPRSBWCD                   = 1000;             // GPRS报文长度
    public static int                        SMSBWCD                    = 200;              // 短信报文长度
    public static int                        MAXACTIVE;                                     // 最大并发数
    public static int                        MAXSIZE;                                       // 部分grid显示最大条数（默认500）

    /*-----------------通讯客户端----------------------------*/
    // 参数保存
    public static final String               SAVE_ONLY                  = "01";
    // 在线等待
    public static final String               WAIT_ONLINE                = "02";
    // 参数待下发
    public static final String               SEND_READY                 = "03";
    // 参数成功
    public static final String               SEND_SUCCESS               = "04";

    // 任务模板
    // public static Map<String, MbCjRwDTO> ZDRWMB = null;

    // 参数类型 终端参数
    public static final int                  CSLX_ZDCS                  = 1;
    // 参数类型 测量点参数
    public static final int                  CSLX_CLDCS                 = 2;

    // 数据召测类型 一类数据
    public static final int                  ZCLX_YLSJ                  = 1;
    // 数据召测类型 二类数据
    public static final int                  ZCLX_ELSJ                  = 2;
    // 数据召测类型 三类数据
    public static final int                  ZCLX_SLSJ                  = 3;

    // 任务数据分类 1类数据
    public static final String               RW_1SJLX                   = "01";
    // 任务数据分类 2类数据
    public static final String               RW_2SJLX                   = "02";

    // 任务操作类型 投入启用
    public static final int                  RWLX_TQ                    = 1;
    // 任务操作类型 投入
    // public static final int RWLX_TR = 1;
    // 任务操作类型 启用
    public static final int                  RWLX_QY                    = 2;
    // 任务操作类型 停用
    public static final int                  RWLX_TY                    = 3;
    // 任务操作类型 召测
    public static final int                  RWLX_ZC                    = 4;

    // 漏点补召阀值（超过该阀值只使用GPRS信道补召）
    public static int                        LEAKER                     = 10000;

    // 国网命令类型
    public static final Map<String, Integer> TypeMap;

    // 操作类型和规约操作FN对应
    static {
        TypeMap = new HashMap<String, Integer>();
        TypeMap.put("CS_XIAFA", new Integer(0x04));
        TypeMap.put("CS_ZHAOCE", new Integer(0x0a));
        TypeMap.put("1LSJ_ZHAOCE", new Integer(0x0c));
        TypeMap.put("2LSJ_ZHAOCE", new Integer(0x0d));
        TypeMap.put("3LSJ_ZHAOCE", new Integer(0x0e));
        TypeMap.put("RW_XIAFA", new Integer(0x04));
        TypeMap.put("RW_ZHAOCE", new Integer(0x0a));
        TypeMap.put("RW_QIYONG", new Integer(0x04));
        TypeMap.put("RW_TINGYONG", new Integer(0x04));
        TypeMap.put("KONGZHI", new Integer(0x05));
        TypeMap.put("SJZF", new Integer(0x10));
        TypeMap.put("QQZDXX", new Integer(0x09));
        TypeMap.put("FW", new Integer(0x01));
    }

    /** 主站操作任务的状态: 计划中 */
    public static final String               STATUS_SCHEDULED           = "-1";
    /** 主站操作命令状态: 未知错误 */
    public static final String               STATUS_UNKNOWN             = "-1";
    /** 主站操作命令状态: 进行中 */
    public static final String               STATUS_RUNNING             = "0";
    /** 主站操作命令状态: 已结束 */
    public static final String               STATUS_SUCCESS             = "1";
    /** 主站操作任务的状态: 执行失败 */
    public static final String               STATUS_FAILED              = "2";
    /** 主站操作命令状态: 通道错误 */
    public static final String               STATUS_COMM_FAILED         = "3";
    /** 主站操作命令状态: 终端超时无应答 */
    public static final String               STATUS_TIMEOUT             = "4";
    /** 主站操作命令状态: 中继命令无应答 */
    public static final String               STATUS_FWD_CMD_NO_RESPONSE = "5";
    /** 主站操作命令状态: 设置内容非法 */
    public static final String               STATUS_PARA_INVALID        = "6";
    /** 主站操作命令状态: 权限不足 */
    public static final String               STATUS_PERMISSION_DENIDE   = "7";
    /** 主站操作命令状态: 无此数据项 */
    public static final String               STATUS_ITEM_INVALID        = "8";
    /** 主站操作命令状态: 命令时间失效 */
    public static final String               STATUS_TIME_OVER           = "9";
    /** 主站操作命令状态: 目标地址不存在 */
    public static final String               STATUS_TARGET_UNREACHABLE  = "10";
    /** 主站操作命令状态: 发送失败 */
    public static final String               STATUS_SEND_FAILURE        = "11";
    /** 主站操作命令状态: 短信帧太长 */
    public static final String               STATUS_SMS_OVERFLOW        = "12";
    /** 主站操作命令状态: 前置机组帧失败 */
    public static final String               STATUS_PARA_ERROR          = "13";
    /** 主站操作命令状态: 前置机解析失败 */
    public static final String               STATUS_PARSE_ERROR         = "14";
    /** 主站操作命令状态: 结果保存入库失败 */
    public static final String               STATUS_TODB_ERROR          = "15";
    /** 主站操作命令状态: 终端与表计无通讯 */
    public static final String               STATUS_RTUAMTCOM_ERROR     = "16";

    public static Map<String, String>        TaskResult                 = null;

    static {
        TaskResult = new HashMap<String, String>();
        TaskResult.put(STATUS_UNKNOWN, "通讯异常");
        TaskResult.put(STATUS_RUNNING, "终端超时无应答");
        TaskResult.put(STATUS_SUCCESS, "返回成功");
        TaskResult.put(STATUS_FAILED, "终端执行失败");
        TaskResult.put(STATUS_COMM_FAILED, "通道错误");
        TaskResult.put(STATUS_TIMEOUT, "终端超时无应答");
        TaskResult.put(STATUS_FWD_CMD_NO_RESPONSE, "中继命令无应答");
        TaskResult.put(STATUS_PARA_INVALID, "设置内容非法");
        TaskResult.put(STATUS_PERMISSION_DENIDE, "权限不足");
        TaskResult.put(STATUS_ITEM_INVALID, "无此数据项");
        TaskResult.put(STATUS_TIME_OVER, "命令时间失效");
        TaskResult.put(STATUS_TARGET_UNREACHABLE, "目标地址不存在");
        TaskResult.put(STATUS_SEND_FAILURE, "发送失败");
        TaskResult.put(STATUS_SMS_OVERFLOW, "短信帧太长");
        TaskResult.put(STATUS_PARA_ERROR, "前置机组帧失败，可能终端信息有误");
        TaskResult.put(STATUS_PARSE_ERROR, "返回报文格式异常");
        TaskResult.put(STATUS_TODB_ERROR, "结果保存入库失败");
        TaskResult.put(STATUS_RTUAMTCOM_ERROR, "终端与表计无通讯");
    }

    public static ArrayBlockingQueue<String> logQueue                   = null;             // 日志记录队列
    public static volatile int               executeTimes;                                  // 任务执行次数
    public static int                        accessTimes;                                   // 菜单及点击次数
}
