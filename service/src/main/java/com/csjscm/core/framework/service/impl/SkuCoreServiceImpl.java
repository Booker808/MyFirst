package com.csjscm.core.framework.service.impl;

import com.csjscm.core.framework.common.util.BussinessException;
import com.csjscm.core.framework.common.util.ExcelUtil;
import com.csjscm.core.framework.dao.SkuCoreMapper;
import com.csjscm.core.framework.service.SkuCoreService;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品核心表ServiceImpl
 * 
 * @author yinzy
 * @version 1.0.0
 * @date 2018-08-31 15:43:10
 */
 
@Service
public class SkuCoreServiceImpl implements SkuCoreService {
	
    private static final Logger logger = LoggerFactory.getLogger(SkuCoreServiceImpl.class);
   
    @Autowired
    private SkuCoreMapper skuCoreMapper;
    /**
     * 取读excel 默认的开始读取的行位置为第几行
     */
    private final static int READ_START_POS = 1;
    /**
     * 最少字段
     */
    private final static int MIX_CELL = 11;
    /**
     * 最多字段
     */
    private final static int MAX_CELL = 21;

    @Override
    public void importSkuCoreExcel(MultipartFile file) throws BussinessException {
        //成功条数
        int successCount=0;
        //失败条数
        int failCount=0;
        //总条数
        int total=0;

        List<String> failList=new ArrayList<>();

        ExcelUtil excelUtil=new ExcelUtil();

        List<Row> rows = excelUtil.readExcel(file);
        total=rows.size()-READ_START_POS;
        String failMsg="";
        int failRow=0;
        int failCell=0;
        for(int i=READ_START_POS;i<rows.size();i++){
            Row row = rows.get(i);
            failRow=i;
            if(row.getLastCellNum()<MIX_CELL || row.getLastCellNum()>MAX_CELL){
                Map<String,Object> map=new HashMap<>();
                failRow=i;
                failMsg="excel列不在此区间["+MIX_CELL+","+MAX_CELL+"]";
                failList.add(getFailMsg(failRow,failCell,failMsg));
                continue;
          }
            //获取普通字段
            String productNo = excelUtil.getCellValue(row.getCell(0));
            String productName = excelUtil.getCellValue(row.getCell(1));
            String brandId = excelUtil.getCellValue(row.getCell(2));
            String minUint = excelUtil.getCellValue(row.getCell(3));
            String rule = excelUtil.getCellValue(row.getCell(4));
            String size = excelUtil.getCellValue(row.getCell(5));
            String ean13Code = excelUtil.getCellValue(row.getCell(6));
            String mnemonicCode = excelUtil.getCellValue(row.getCell(7));
            String invoiceType = excelUtil.getCellValue(row.getCell(8));
            String txtCode = excelUtil.getCellValue(row.getCell(9));
            String categoryNo = excelUtil.getCellValue(row.getCell(10));

        }
    }
    private String getFailMsg(int failRow,int failCell, String failMsg){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("失败：行=》 ").append(failRow).append(",").append("列=》").append(failCell).append(",原因=》").append(failMsg);
        return  stringBuffer.toString();
    }
}