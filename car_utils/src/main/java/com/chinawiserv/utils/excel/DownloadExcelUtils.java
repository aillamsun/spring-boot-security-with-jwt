package com.chinawiserv.utils.excel;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Number;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wangt
 * Date: 17-3-31
 * Time: 下午5:51
 * To change this template use File | Settings | File Templates.
 */
public class DownloadExcelUtils {

    public static void download(OutputStream os, List<Map<String,Object>> results, Map<String,String> titlesMap) throws Exception {
        //创建工作簿
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(os);
            writeExcel(titlesMap,"列表",0,wwb,results);

            //写入工作薄
            wwb.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            wwb.write();
            if (wwb != null) {
                //关闭文件
                wwb.close();
                wwb = null;
            }
        }
    }

    public static void writeExcel(Map<String,String> map, String sheetName, int k, WritableWorkbook wwb, List<Map<String,Object>> results) throws Exception {
        //创建工作表
        WritableSheet sheet = wwb.createSheet(sheetName, k);
        SheetSettings settings = sheet.getSettings();
        settings.setVerticalFreeze(1);//冻结表头
        //标题样式
        WritableCellFormat wcf_title = setTitleStyle();
        //内容样式
        WritableCellFormat wcf_left = setContentStyle();
        //标题
        Set<String> fieldTitle = map.keySet();
        Iterator iterator = fieldTitle.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Label label = new Label(index, 0, iterator.next().toString(), wcf_title);
            sheet.addCell(label);
            sheet.setColumnView(index, 20);//设置列宽
            index++;
        }
        int rowIndex = 1;//行索引：第1行是标题,内容从第2行开始
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //写到excel文件中
        for( Map<String,Object> row : results ) {
            Collection list = map.values();
            Iterator valueIterator = list.iterator();
            int colIndex = 0;//列索引：从第1列开始
            while(valueIterator.hasNext()){
                Object val = row.get(valueIterator.next());
                if(val instanceof Double){
                    sheet.addCell(new Number(colIndex++, rowIndex, Double.parseDouble(val.toString()), wcf_left));
                }else if(val instanceof Integer){
                    sheet.addCell(new Number(colIndex++, rowIndex, Integer.parseInt(val.toString()), wcf_left));
                }else if(val instanceof Date){
                    String date = sdf.format((Date)val);
                    sheet.addCell(new Label(colIndex++, rowIndex, date, wcf_left));
                }else{
                    sheet.addCell(new Label(colIndex++, rowIndex, val.toString(), wcf_left));
                }
            }
            rowIndex++;
        }
    }

    /**
     * 设置标题样式
     * @return
     */
    private static WritableCellFormat setTitleStyle(){
        WritableCellFormat wcf_center = null;
        try {
            //设置单元格字体:字体 大小 下划线 斜体 粗体 颜色
            WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD,false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            //设置样式：用于标题居中
            wcf_center = new WritableCellFormat(BoldFont);
            wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); //边框
            wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); //垂直对齐
            wcf_center.setAlignment(Alignment.CENTRE); //水平对齐
            wcf_center.setWrap(false); //是否换行
        }catch (Exception e){
            e.printStackTrace();
        }
        return wcf_center;
    }
    /**
     * 设置内容样式
     * @return
     */
    private static WritableCellFormat setContentStyle(){
        WritableCellFormat wcf_left = null;
        try {
            //设置单元格字体:字体 大小 下划线 斜体 粗体 颜色
            WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
            //设置样式：用于正文居左
            wcf_left = new WritableCellFormat(NormalFont);
            wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); //边框
            wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); //垂直对齐
            wcf_left.setAlignment(Alignment.LEFT); //水平对齐
            wcf_left.setWrap(false); //是否换行
        }catch (Exception e){
            e.printStackTrace();
        }
        return wcf_left;
    }

    public static String ObjToStrTool(Object obj){
        String str = "";
        if (null==obj){
            return str;
        }else {
            str = obj.toString();
        }
        return str;
    }
}
