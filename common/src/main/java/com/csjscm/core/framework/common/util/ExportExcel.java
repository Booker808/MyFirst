package com.csjscm.core.framework.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
  
/** 
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！ 
 *  
 * @author leno 
 * @version v1.0 
 * @param <T> 
 *            应用泛型，代表任意一个符合javabean风格的类 
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx() 
 *            byte[]表jpg格式的图片数据 
 */  
public class ExportExcel<T>  
{  

  
    /** 
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
     *  
     * @param title 
     *            表格标题名 
     * @param headers 
     *            表格属性列名数组 
     * @param dataset 
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
     * @param out 
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
     * @param pattern 
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd" 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
    @SuppressWarnings("unchecked")  
    public void exportExcel(String title, String[] headers,  String[] line,
            Collection<T> dataset, OutputStream out, String pattern) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  
    {  
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((int) 15);  
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
  
        // 声明一个画图的顶级管理器  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
        // 定义注释的大小和位置,详见文档  
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,  
                0, 0, 0, (short) 4, 2, (short) 6, 5));  
        // 设置注释内容  
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
        comment.setAuthor("leno");  
  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for (int i = 0; i < headers.length; i++)  
        {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
  
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();  
        int index = 0;  
        while (it.hasNext())  
        {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next();  
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            //Field[] fields = t.getClass().getDeclaredFields();  
            for (int i = 0; i < line.length; i++)  
            {  
                HSSFCell cell = row.createCell(i);  
                cell.setCellStyle(style2);  
                //Field field = fields[i];  
                String fieldName = line[i];  
                String getMethodName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                try  
                {  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName,  
                            new Class[]  
                            {});  
                    Object value = getMethod.invoke(t, new Object[]  
                    {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    if (value instanceof Boolean)  
                    {  
                        boolean bValue = (Boolean) value;  
                        textValue = "男";  
                        if (!bValue)  
                        {  
                            textValue = "女";  
                        }  
                    }  
                    else if (value instanceof Date)  
                    {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    }  
                    else if (value instanceof byte[])  
                    {  
                        // 有图片时，设置行高为60px;  
                        row.setHeightInPoints(60);  
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,  
                                1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        patriarch.createPicture(anchor, workbook.addPicture(  
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));  
                    } 
                    else if (value == null)  
                    {  
                    	textValue = null;
                    } 
                    else  
                    {  
                        // 其它数据类型都当作字符串简单处理  
                        textValue = value.toString();  
                    }  
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
                    if (textValue != null)  
                    {  
                        Pattern p = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches())  
                        {  
                            // 是数字当作double处理  
                        	HSSFFont font3 = workbook.createFont();  
                            font3.setColor(HSSFColor.BLUE.index);
                            HSSFCellStyle cellStyle = workbook.createCellStyle();  
                            cellStyle.setFont(font3);
                            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
                            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
                            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
                            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
                            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
                            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
                            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  
                            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
                            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
                            cell.setCellStyle(cellStyle);   
                            cell.setCellValue(Double.parseDouble(textValue));  
                        }  
                        else  
                        {  
                            HSSFRichTextString richString = new HSSFRichTextString(  
                                    textValue);  
                            HSSFFont font3 = workbook.createFont();  
                            font3.setColor(HSSFColor.BLUE.index);  
                            richString.applyFont(font3);  
                            cell.setCellValue(richString);  
                        }  
                    }  
                }finally  
                {  
                    // 清理资源  
                }  
            }  
        }  
        try  
        {  
            workbook.write(out);  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
     *  
     * @param title 
     *            表格标题名 
     * @param headers 
     *            表格属性列名数组 
     * @param dataset 
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
     * @param out 
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
     * @param pattern 
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd" 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IOException 
     */  
    @SuppressWarnings("unchecked")  
    public void exportExcelBigData(String title, String[] headers,  String[] line,
            Collection<T> dataset, OutputStream out, String pattern) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException  
    {  
        // 声明一个工作薄  
    	SXSSFWorkbook workbook = new SXSSFWorkbook(100);  // 内存中只保持100条数据就向excel刷入
        // 生成一个表格  
    	Sheet sheet = workbook.createSheet(title);  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((int) 15);  
        // 生成一个样式  
        CellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(CellStyle.BORDER_THIN);  
        style.setBorderLeft(CellStyle.BORDER_THIN);  
        style.setBorderRight(CellStyle.BORDER_THIN);  
        style.setBorderTop(CellStyle.BORDER_THIN);  
        style.setAlignment(CellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        Font font = workbook.createFont();  
        font.setColor(HSSFColor.VIOLET.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        CellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(CellStyle.BORDER_THIN);  
        style2.setBorderLeft(CellStyle.BORDER_THIN);  
        style2.setBorderRight(CellStyle.BORDER_THIN);  
        style2.setBorderTop(CellStyle.BORDER_THIN);  
        style2.setAlignment(CellStyle.ALIGN_CENTER);  
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        Font font2 = workbook.createFont();  
        font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
       
        // 声明一个画图的顶级管理器  
        Drawing patriarch = sheet.createDrawingPatriarch();  
//        // 定义注释的大小和位置,详见文档  
//        Comment comment = patriarch.createCellComment(new MyClientAnchor(0,  
//                0, 0, 0, (short) 4, 2, (short) 6, 5));  
//        // 设置注释内容  
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
//        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
//        comment.setAuthor("leno");  
  
        // 产生表格标题行  
        Row row = sheet.createRow(0);  
        for (int i = 0; i < headers.length; i++)  
        {  
            Cell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
  
        // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();  
        int index = 0;  
        while (it.hasNext())  
        {  
            index++;  
            row = sheet.createRow(index);  
            T t = (T) it.next();  
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
            //Field[] fields = t.getClass().getDeclaredFields();  
            for (int i = 0; i < line.length; i++)  
            {  
                Cell cell = row.createCell(i);  
                cell.setCellStyle(style2);  
                //Field field = fields[i];  
                String fieldName = line[i];  
                String getMethodName = "get"  
                        + fieldName.substring(0, 1).toUpperCase()  
                        + fieldName.substring(1);  
                try  
                {  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName,  
                            new Class[]  
                            {});  
                    Object value = getMethod.invoke(t, new Object[]  
                    {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue = null;   
                    
                    if (value instanceof Boolean)  
                    {  
                        boolean bValue = (Boolean) value;  
                        textValue = "男";  
                        if (!bValue)  
                        {  
                            textValue = "女";  
                        }  
                    }  
                    else if (value instanceof Date)  
                    {  
                        Date date = (Date) value;  
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
                        textValue = sdf.format(date);  
                    }  
                    else if (value instanceof byte[])  
                    {  
                        // 有图片时，设置行高为60px;  
                        row.setHeightInPoints(60);  
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算  
                        sheet.setColumnWidth(i, (short) (35.7 * 80));  
                        // sheet.autoSizeColumn(i);  
                        byte[] bsValue = (byte[]) value;  
                        ClientAnchor anchor = new MyClientAnchor(0, 0,  
                                1023, 255, (short) 6, index, (short) 6, index);  
                        anchor.setAnchorType(2);  
                        patriarch.createPicture(anchor, workbook.addPicture(  
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));  
                    } 
                    else if (value == null)  
                    {  
                    	textValue = null;
                    }else if(value.getClass().isEnum()){ 
                    	Method method = MethodUtils.getAccessibleMethod(value.getClass() , "getDesc"); 
                    	textValue=method.invoke(value).toString();
                    }else  
                    {  
                        // 其它数据类型都当作字符串简单处理  
                        textValue = value.toString();  
                    }  
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成  
                    if (textValue != null)  
                    {  
                        Pattern p = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches())  
                        {  
                            // 是数字当作double处理  
                        	Font font3 = workbook.createFont();  
                            font3.setColor(HSSFColor.BLUE.index);
                            CellStyle cellStyle = workbook.createCellStyle();  
                            cellStyle.setFont(font3);
                            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));  
                            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
                            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);  
                            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);  
                            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);  
                            cellStyle.setBorderRight(CellStyle.BORDER_THIN);  
                            cellStyle.setBorderTop(CellStyle.BORDER_THIN);  
                            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
                            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  
                            cell.setCellStyle(cellStyle);   
                            cell.setCellValue(Double.parseDouble(textValue));  
                        }  
                        else  
                        {  
                             cell.setCellValue(String.valueOf(textValue));  
                        }  
                    }  
                }finally  
                {  
                    // 清理资源  
                }  
            }  
        }  
          
            workbook.write(out);  
            workbook.dispose();
        
    }  
  
    
    private class MyClientAnchor implements ClientAnchor{
    	
    	public MyClientAnchor(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2) {
    		setDx1(dx1);
            setDy1(dy1);
            setDx2(dx2);
            setDy2(dy2);
            checkRange(dx1, 0, 1023, "dx1");
            checkRange(dx2, 0, 1023, "dx2");
            checkRange(dy1, 0, 255, "dy1");
            checkRange(dy2, 0, 255, "dy2");
            checkRange(col1, 0, 255, "col1");
            checkRange(col2, 0, 255, "col2");
            checkRange(row1, 0, 255 * 256, "row1");
            checkRange(row2, 0, 255 * 256, "row2");

            setCol1((short) Math.min(col1, col2));
            setCol2((short) Math.max(col1, col2));
            setRow1((short) Math.min(row1, row2));
            setRow2((short) Math.max(row1, row2));

        }
    	
    	private void checkRange(int value, int minRange, int maxRange, String varName) {
            if (value < minRange || value > maxRange)
                throw new IllegalArgumentException(varName + " must be between " + minRange + " and " + maxRange + ", but was: " + value);
        }
    	
		@Override
		public short getCol1() {
			return 0;
		}

		@Override
		public void setCol1(int col1) {
			
		}

		@Override
		public short getCol2() {
			return 0;
		}

		@Override
		public void setCol2(int col2) {
			
		}

		@Override
		public int getRow1() {
			return 0;
		}

		@Override
		public void setRow1(int row1) {
			
		}

		@Override
		public int getRow2() {
			return 0;
		}

		@Override
		public void setRow2(int row2) {
			
		}

		@Override
		public int getDx1() {
			return 0;
		}

		@Override
		public void setDx1(int dx1) {
			
		}

		@Override
		public int getDy1() {
			return 0;
		}

		@Override
		public void setDy1(int dy1) {
			
		}

		@Override
		public int getDy2() {
			return 0;
		}

		@Override
		public void setDy2(int dy2) {
			
		}

		@Override
		public int getDx2() {
			return 0;
		}

		@Override
		public void setDx2(int dx2) {
			
		}

		@Override
		public void setAnchorType(int anchorType) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getAnchorType() {
			// TODO Auto-generated method stub
			return 0;
		}

		

		
    	
    }
    
    
    

}  