package com.csjscm.core.framework.common.constant;
/**
 * 公用常量类定义
 *
 * @author yinzy
 * @version 1.0
 */
public class Constant {
    /**
     * redis中最大商品编码的key的前缀 后面拼接最小商品分类编码
     */
    public static final String REDIS_KEY_PRODUCT_NO = "category_";
    /**
     * redis中最大商城商品编码的key的前缀 后面拼接最小商品分类编码
     */
    public static final String REDIS_KEY_SP_PRODUCT_NO = "sp_category_";

    public static final String REDIS_KEY_SP_SPU_NO="spu_sp_category_";
    /**
     * redis中最大商城商品编码的key的前缀 后面拼接最小商品分类编码
     */
    public static final String REDIS_KEY_PRODUCT_SPNO = "spCategory_";
    /**
     * redis中最小单位的key
     */
    public static final String REDIS_KEY_UNIT = "unitList";
    /**
     * redis中 商品分类json数据的 key
     */
    public static final String REDIS_KEY_JSON_CATEGORY = "categoryJsonStr";
    /**
     * redis中 SP商品分类json数据的 key
     */
    public static final String REDIS_KEY_JSON_SP_CATEGORY = "spCategoryJsonStr";
    /**
     * 企业编码前缀
     */
    public static final String  ENTNUMBER_INDEX = "EP";
    /**
     * 字典表中银行的code
     */
    public static final String  DICT_CODE_BANK = "bankName";
    /**
     * 品牌数据 redis 中的key
     */
    public static final String  REDIS_KEY_JSONSTR_BRAND = "brandMasterJsonStr";
    /**
     * 供应商审核接口 域名的配置文件名称
     */
    public static final String  RNTERPRISE_CHECK_OA_DOMAIN= "sweet.framework.enterprise.check.oa-domain";
    /**
     * 权限域名的配置文件名称
     */
    public static final String  SHIRO_DOMAIN= "sweet.oauth.server.address";

    /**
     * 我的代办接口地址
     */
    public static final String  ENTERPRISE_CHECK_OA_MYTODODEALWITH_URL = "/api/SupplierAdmittance/myTodoDealWith";
    /**
     * 开始供应商准入流程API
     */
    public static final String  ENTERPRISE_CHECK_OA_START_URL = "/api/SupplierAdmittance/startSupplierAdmittanceWorkFlow";
    /**
     * 我的完成待办API
     */
    public static final String  ENTERPRISE_CHECK_OA_COMPLETE_URL = "/api/SupplierAdmittance/myTodoComplete";
    /**
     * 获取历史记录详情api
     */
    public static final String  ENTERPRISE_CHECK_OA_FLOW_INFO_URL = "/api/activiti/getMyWorkFlowInfoHisDetail";
    /**
     * 流程定义关键字
     */
    public static final String  ENTERPRISE_CHECK_OA_PROCESS = "SupplierAdmittanceProcess2.2";


    /**
     * 采购模板开始审核接口api地址
     */
    public static final String ENTERPRISE_CHECK_PURCHASE_TEMPLATE_START_URL = "/api/templateApproval/startTemplateApprovalWorkFlow";

    /**
     * 采购模板我的完成待办API
     */
    public static final String  ENTERPRISE_CHECK_PURCHASE_TEMPLATE_COMPLETE_URL = "/api/templateApproval/getTATodoComplete";
    /**
     * 采购模板我的代办api
     */
    public static final String  ENTERPRISE_CHECK_PURCHASE_TEMPLATE_MYTODODEALWITH_URL = "/api/templateApproval/getTATodoDealWith";

    /**
     * 查询历史任务记录
     */
    public static final String ENTERPRISE_CHECK_OA_FLOW_TASK_INFO_URL="/api/activiti/getMyWorkFlowInfoHisTask";

    /**
     * 查询最後任务记录
     */
    public static final String ENTERPRISE_CHECK_OA_FLOW_LAST_INFO_URL="/api/activiti/getMyWorkFlowEndTask";
    /**
     * 根据当前租户code获取组织第一级 api
     */
    public static final String  ENTERPRISE_SHIRO_GETORG="/api/shiro/getOrgByTcode";
}
