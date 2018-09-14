package com.csjscm.core.framework.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商商品包装规格实体
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-09-14 10:26:29
 */

 public class SkuPartnerUom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Integer id;

    /**
    * 
    */
    private String partnerUuid;

    /**
    * 包装编码
    */
    private String packNo;

    /**
    * 库存单位名 最小库存单位
    */
    private String invUnitName;

    /**
    * 包装单位名
    */
    private String packUnitName;

    /**
    * 包装含量 相对于(最小库存单位)
    */
    private BigDecimal packRate;

    /**
    * 包装要求
    */
    private String packStandard;

    /**
    * 标准质押价
    */
    private BigDecimal stdZyPrice;

    /**
    * 单位包装长(mm)
    */
    private BigDecimal pcsLength;

    /**
    * 单位包装宽(mm)
    */
    private BigDecimal pcsWidth;

    /**
    * 单位包装高(mm)
    */
    private BigDecimal pcsHeight;

    /**
    * 净重
    */
    private BigDecimal netWeight;

    /**
    * 毛重
    */
    private BigDecimal grossWeight;

    /**
    * 货格大小区分
    */
    private String sizeType;

    /**
    * 
    */
    private String createUserId;

    /**
    * 
    */
    private Date createTime;

    /**
    * 
    */
    private String editUserId;

    /**
    * 
    */
    private Date editTime;

    /**
    * 
    */
    private String udf1;

    /**
    * 
    */
    private String udf2;

    /**
    * 
    */
    private String udf3;

    /**
    * 
    */
    private String udf4;

    /**
    * 
    */
    private String udf5;

    /**
    * 
    */
    private String udf6;

    /**
    * 
    */
    private String udf7;

    /**
    * 
    */
    private String udf8;

    /**
    * 
    */
    private String udf9;

    /**
    * 
    */
    private String udf10;

    /**
    * 
    */
    private String udf11;

    /**
    * 
    */
    private String udf12;

    /**
    * 
    */
    private String udf13;

    /**
    * 
    */
    private String udf14;

    /**
    * 
    */
    private String udf15;

    /**
    * 
    */
    private String udf16;

    /**
    * 
    */
    private String udf17;

    /**
    * 
    */
    private String udf18;

    /**
    * 
    */
    private String udf19;

    /**
    * 
    */
    private String udf20;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Integer getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param
    */
    public void setId(Integer id){
        this.id = id;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getPartnerUuid(){
        return partnerUuid;
    }

    /**
    * 设置
    * 
    * @param partnerUuid 要设置的
    */
    public void setPartnerUuid(String partnerUuid){
        this.partnerUuid = partnerUuid;
    }

    /**
    * 获取包装编码
    *
    * @return 包装编码
    */
    public String getPackNo(){
        return packNo;
    }

    /**
    * 设置包装编码
    * 
    * @param packNo 要设置的包装编码
    */
    public void setPackNo(String packNo){
        this.packNo = packNo;
    }

    /**
    * 获取库存单位名 最小库存单位
    *
    * @return 库存单位名 最小库存单位
    */
    public String getInvUnitName(){
        return invUnitName;
    }

    /**
    * 设置库存单位名 最小库存单位
    * 
    * @param invUnitName 要设置的库存单位名 最小库存单位
    */
    public void setInvUnitName(String invUnitName){
        this.invUnitName = invUnitName;
    }

    /**
    * 获取包装单位名
    *
    * @return 包装单位名
    */
    public String getPackUnitName(){
        return packUnitName;
    }

    /**
    * 设置包装单位名
    * 
    * @param packUnitName 要设置的包装单位名
    */
    public void setPackUnitName(String packUnitName){
        this.packUnitName = packUnitName;
    }

    /**
    * 获取包装含量 相对于(最小库存单位)
    *
    * @return 包装含量 相对于(最小库存单位)
    */
    public BigDecimal getPackRate(){
        return packRate;
    }

    /**
    * 设置包装含量 相对于(最小库存单位)
    * 
    * @param packRate 要设置的包装含量 相对于(最小库存单位)
    */
    public void setPackRate(BigDecimal packRate){
        this.packRate = packRate;
    }

    /**
    * 获取包装要求
    *
    * @return 包装要求
    */
    public String getPackStandard(){
        return packStandard;
    }

    /**
    * 设置包装要求
    * 
    * @param packStandard 要设置的包装要求
    */
    public void setPackStandard(String packStandard){
        this.packStandard = packStandard;
    }

    /**
    * 获取标准质押价
    *
    * @return 标准质押价
    */
    public BigDecimal getStdZyPrice(){
        return stdZyPrice;
    }

    /**
    * 设置标准质押价
    * 
    * @param stdZyPrice 要设置的标准质押价
    */
    public void setStdZyPrice(BigDecimal stdZyPrice){
        this.stdZyPrice = stdZyPrice;
    }

    /**
    * 获取单位包装长(mm)
    *
    * @return 单位包装长(mm)
    */
    public BigDecimal getPcsLength(){
        return pcsLength;
    }

    /**
    * 设置单位包装长(mm)
    * 
    * @param pcsLength 要设置的单位包装长(mm)
    */
    public void setPcsLength(BigDecimal pcsLength){
        this.pcsLength = pcsLength;
    }

    /**
    * 获取单位包装宽(mm)
    *
    * @return 单位包装宽(mm)
    */
    public BigDecimal getPcsWidth(){
        return pcsWidth;
    }

    /**
    * 设置单位包装宽(mm)
    * 
    * @param pcsWidth 要设置的单位包装宽(mm)
    */
    public void setPcsWidth(BigDecimal pcsWidth){
        this.pcsWidth = pcsWidth;
    }

    /**
    * 获取单位包装高(mm)
    *
    * @return 单位包装高(mm)
    */
    public BigDecimal getPcsHeight(){
        return pcsHeight;
    }

    /**
    * 设置单位包装高(mm)
    * 
    * @param pcsHeight 要设置的单位包装高(mm)
    */
    public void setPcsHeight(BigDecimal pcsHeight){
        this.pcsHeight = pcsHeight;
    }

    /**
    * 获取净重
    *
    * @return 净重
    */
    public BigDecimal getNetWeight(){
        return netWeight;
    }

    /**
    * 设置净重
    * 
    * @param netWeight 要设置的净重
    */
    public void setNetWeight(BigDecimal netWeight){
        this.netWeight = netWeight;
    }

    /**
    * 获取毛重
    *
    * @return 毛重
    */
    public BigDecimal getGrossWeight(){
        return grossWeight;
    }

    /**
    * 设置毛重
    * 
    * @param grossWeight 要设置的毛重
    */
    public void setGrossWeight(BigDecimal grossWeight){
        this.grossWeight = grossWeight;
    }

    /**
    * 获取货格大小区分
    *
    * @return 货格大小区分
    */
    public String getSizeType(){
        return sizeType;
    }

    /**
    * 设置货格大小区分
    * 
    * @param sizeType 要设置的货格大小区分
    */
    public void setSizeType(String sizeType){
        this.sizeType = sizeType;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getCreateUserId(){
        return createUserId;
    }

    /**
    * 设置
    * 
    * @param createUserId 要设置的
    */
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }



    /**
    * 获取
    *
    * @return 
    */
    public String getEditUserId(){
        return editUserId;
    }

    /**
    * 设置
    * 
    * @param editUserId 要设置的
    */
    public void setEditUserId(String editUserId){
        this.editUserId = editUserId;
    }

    /**
    * 获取
    *
    * @return 
    */
   public Date getCreateTime() {
      return createTime;
   }

   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }

   public Date getEditTime() {
      return editTime;
   }

   public void setEditTime(Date editTime) {
      this.editTime = editTime;
   }

   /**
    * 获取
    *
    * @return 
    */
    public String getUdf1(){
        return udf1;
    }

    /**
    * 设置
    * 
    * @param udf1 要设置的
    */
    public void setUdf1(String udf1){
        this.udf1 = udf1;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf2(){
        return udf2;
    }

    /**
    * 设置
    * 
    * @param udf2 要设置的
    */
    public void setUdf2(String udf2){
        this.udf2 = udf2;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf3(){
        return udf3;
    }

    /**
    * 设置
    * 
    * @param udf3 要设置的
    */
    public void setUdf3(String udf3){
        this.udf3 = udf3;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf4(){
        return udf4;
    }

    /**
    * 设置
    * 
    * @param udf4 要设置的
    */
    public void setUdf4(String udf4){
        this.udf4 = udf4;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf5(){
        return udf5;
    }

    /**
    * 设置
    * 
    * @param udf5 要设置的
    */
    public void setUdf5(String udf5){
        this.udf5 = udf5;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf6(){
        return udf6;
    }

    /**
    * 设置
    * 
    * @param udf6 要设置的
    */
    public void setUdf6(String udf6){
        this.udf6 = udf6;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf7(){
        return udf7;
    }

    /**
    * 设置
    * 
    * @param udf7 要设置的
    */
    public void setUdf7(String udf7){
        this.udf7 = udf7;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf8(){
        return udf8;
    }

    /**
    * 设置
    * 
    * @param udf8 要设置的
    */
    public void setUdf8(String udf8){
        this.udf8 = udf8;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf9(){
        return udf9;
    }

    /**
    * 设置
    * 
    * @param udf9 要设置的
    */
    public void setUdf9(String udf9){
        this.udf9 = udf9;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf10(){
        return udf10;
    }

    /**
    * 设置
    * 
    * @param udf10 要设置的
    */
    public void setUdf10(String udf10){
        this.udf10 = udf10;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf11(){
        return udf11;
    }

    /**
    * 设置
    * 
    * @param udf11 要设置的
    */
    public void setUdf11(String udf11){
        this.udf11 = udf11;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf12(){
        return udf12;
    }

    /**
    * 设置
    * 
    * @param udf12 要设置的
    */
    public void setUdf12(String udf12){
        this.udf12 = udf12;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf13(){
        return udf13;
    }

    /**
    * 设置
    * 
    * @param udf13 要设置的
    */
    public void setUdf13(String udf13){
        this.udf13 = udf13;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf14(){
        return udf14;
    }

    /**
    * 设置
    * 
    * @param udf14 要设置的
    */
    public void setUdf14(String udf14){
        this.udf14 = udf14;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf15(){
        return udf15;
    }

    /**
    * 设置
    * 
    * @param udf15 要设置的
    */
    public void setUdf15(String udf15){
        this.udf15 = udf15;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf16(){
        return udf16;
    }

    /**
    * 设置
    * 
    * @param udf16 要设置的
    */
    public void setUdf16(String udf16){
        this.udf16 = udf16;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf17(){
        return udf17;
    }

    /**
    * 设置
    * 
    * @param udf17 要设置的
    */
    public void setUdf17(String udf17){
        this.udf17 = udf17;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf18(){
        return udf18;
    }

    /**
    * 设置
    * 
    * @param udf18 要设置的
    */
    public void setUdf18(String udf18){
        this.udf18 = udf18;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf19(){
        return udf19;
    }

    /**
    * 设置
    * 
    * @param udf19 要设置的
    */
    public void setUdf19(String udf19){
        this.udf19 = udf19;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getUdf20(){
        return udf20;
    }

    /**
    * 设置
    * 
    * @param udf20 要设置的
    */
    public void setUdf20(String udf20){
        this.udf20 = udf20;
    }

}