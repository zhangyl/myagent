package com.zyl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
 
public class DynamicExcelHeaderTest {
    public static void main(String[] args) throws Exception {
    	
    	createDynamicColumExcel();
//    	hiddenColum();
        System.out.println("~~~~OK~~~~~");
    }

	public static void createDynamicColumExcel() throws IOException, FileNotFoundException {
		String jsonStr = "["
    			//数据1
    			+ "{'name':'lili', 'sex':'女'"
    			+ ",'custom':[{'title':'toy', 'key':'toy','value':'car'},{'title':'toy', 'key':'toy','value':'train'},{'title':'animal', 'key':'animal','value':'dog'}]"
    			+ "},"
    			//数据2
    			+ "{'name':'zhangsan', 'sex':'男'"
    			+ ",'custom':[{'title':'选项A', 'key':'CF01', 'value':'A'},{'title':'选项B', 'key':'CF02', 'value':'B'}]"
    			+ "}"
    			
    			+ "]";
    	
    	String jsonStr2 = "["
    			//数据1
    			+ "{'name':'mali', 'sex':'女'"
    			+ ",'custom':[{'title':'toy', 'key':'toy','value':'babi'},{'title':'toy', 'key':'toy','value':'bear'},{'title':'animal', 'key':'animal','value':'cat'}]"
    			+ "},"
    			//数据2
    			+ "{'name':'李四', 'sex':'男'"
    			+ ",'custom':[{'title':'选项A', 'key':'CF01', 'value':'C'},{'title':'选项B', 'key':'CF02', 'value':'B'}]"
    			+ "}"
    			
    			+ "]";
    	
    	JSONArray jsonArrayPage1 = JSON.parseArray(jsonStr);
    	
    	JSONArray jsonArrayPage2 = JSON.parseArray(jsonStr2);
    	//固定的header
    	final List<String> headerList = new LinkedList<>();
    	{
    		headerList.add("name");
    		headerList.add("sex");
    	}
    	//固定header的index
        final Map<String, Integer> headerIdxMap = new HashMap<>();
        {
        	headerIdxMap.put("name", 0);
        	headerIdxMap.put("sex", 1);
        }
        
        SXSSFWorkbook workbook = new SXSSFWorkbook(1);
        Sheet sheet = workbook.createSheet();
        //表头提前预留，最后写入
        sheet.createRow(0);
        
        //控制总记录行数的index，从1开始，0留给表头了
        final AtomicInteger rowIndex = new AtomicInteger(1);
        
        //此处可以分页导出，模拟分页第一页
        for (int i=0;i<jsonArrayPage1.size();i++) {
        	JSONObject data = jsonArrayPage1.getJSONObject(i);
        	dealOneData(data, headerList, headerIdxMap, sheet, rowIndex);
        }
        
        //模拟分页第二页
        for (int i=0;i<jsonArrayPage2.size();i++) {
        	JSONObject data = jsonArrayPage2.getJSONObject(i);
        	dealOneData(data, headerList, headerIdxMap, sheet, rowIndex);
        }

        workbook.write(new FileOutputStream("1.xlsx"));
        workbook.close();
        
        //写完数据后，最后写入header
        FileInputStream inputStream = new FileInputStream("1.xlsx");
        XSSFWorkbook xsbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet = xsbook.getSheetAt(0);
        XSSFRow xssHeaderRow = xssfSheet.getRow(0);
        
        //写入header
        for (int j = 0; j < headerList.size(); j++) {
            Cell cell = xssHeaderRow.createCell(j);
            XSSFRichTextString text = new XSSFRichTextString(headerList.get(j));
            cell.setCellValue(text);
        }
        xsbook.write(new FileOutputStream("1.xlsx"));
        xsbook.close();
	}

	private static void dealOneData(JSONObject data, List<String> headerList, Map<String, Integer> headerIdxMap,
			Sheet sheet, AtomicInteger rowIndex) {
		
		AtomicInteger cellIndex = new AtomicInteger(0);
		
		Row currentRow = sheet.createRow(rowIndex.get());
		//合并单元格开始的行index（包含）
		final int currentRowIndex = rowIndex.get();
		
		Cell cell1 = currentRow.createCell(headerIdxMap.get("name"));
		HSSFRichTextString text1 = new HSSFRichTextString(data.getString("name"));
		cell1.setCellValue(text1);
		cellIndex.getAndAdd(1);
		
		Cell cell2 = currentRow.createCell(headerIdxMap.get("sex"));
		HSSFRichTextString text2 = new HSSFRichTextString(data.getString("sex"));
		cell2.setCellValue(text2);
		cellIndex.getAndAdd(1);
		
		JSONArray customArray = data.getJSONArray("custom");
		//动态数据不存在
		if(customArray == null || customArray.size() == 0) {
			return;
		}
		//合并单元格结束的列index（包含）,固定的列
		final int lastCol = cellIndex.get()-1;

		//动态行，第一行和原来的行共享
		for(int j=0; j<customArray.size(); j++) {
			JSONObject customData = customArray.getJSONObject(j);
			String headerTitleKey = customData.getString("key");
			if(headerTitleKey == null || headerTitleKey.trim().length() == 0) {
				continue;
			}
			int idx = headerList.size();
			if(headerList.contains(headerTitleKey)) {
				idx = headerIdxMap.get(headerTitleKey);
			} else {
				headerList.add(headerTitleKey);
				headerIdxMap.put(headerTitleKey, idx);
			}
			Row toyListRow = currentRow;
			//第一行和前面的共享
			if(j>0) {
				toyListRow = sheet.createRow(rowIndex.addAndGet(1));
			} 
			
			String value = customData.getString("value");
	        //创建cell内容
	        Cell textCell = toyListRow.createCell(idx);
	        HSSFRichTextString textCellString = new HSSFRichTextString(value);
	        textCell.setCellValue(textCellString);
//	        cellIndex.addAndGet(1);
		}

		//合并单元格最后行index（包含）
		final int lastRowIndex = rowIndex.get();

		for(int idxCol=0;idxCol<=lastCol;idxCol++) {
			CellRangeAddress mergeAddress = new CellRangeAddress(currentRowIndex, lastRowIndex, idxCol, idxCol);
			sheet.addMergedRegion(mergeAddress);
		}
		//处理完成后，下一行的index
		rowIndex.addAndGet(1);
	}
	
	public static void hiddenColum() throws Exception {

		
        
//        byte[] byteArray = new byte[2048];
//        fileInputStream.read(byteArray);
//        String s = new String(byteArray);
//        System.out.println(s);
//        fileInputStream.close();
        /*
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        fileInputStream.close();
        Sheet sheet = workbook.getSheetAt(0);
        sheet.setColumnHidden(1, true);
        
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/zhangyl/Desktop/zzz.xlsx"));
        workbook.write(fileOutputStream);
        workbook.close();
        
        fileOutputStream.close();
        */
		FileInputStream fileInputStream = new FileInputStream(new File("/Users/zhangyl/Desktop/zzz.xlsx"));
//		FileInputStream fileInputStream = new FileInputStream(new File("1.xlsx"));
        XSSFWorkbook xbook = new XSSFWorkbook(fileInputStream);
        SXSSFWorkbook sbook = new SXSSFWorkbook(xbook);
        XSSFWorkbook xssfWorkbook = sbook.getXSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(1);

        XSSFCell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue());
        sbook.close();
        fileInputStream.close();
//        XSSFSheet xssfSheet = xsbook.getSheetAt(0);
//        XSSFRow xssHeaderRow = xssfSheet.getRow(0);
//        int firstRowIndex = 0;
//        int lastRowIndex = xssfSheet.getLastRowNum();
//        xssfSheet.shiftColumns(6, 6, 3);

//        xssfSheet.setColumnHidden(1, true);
//        CellRangeAddress mergeAddress = new CellRangeAddress(firstRowIndex, lastRowIndex, 6, 7);
//        xssfSheet.addMergedRegion(mergeAddress);
        
//        xsbook.write(new FileOutputStream("/Users/zhangyl/Desktop/zzz.xlsx"));
//        xsbook.close();
	}
}

