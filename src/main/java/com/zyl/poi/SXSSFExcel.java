package com.zyl.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SXSSFExcel {
	private static Logger log = LoggerFactory.getLogger(SXSSFExcel.class);

	private SXSSFWorkbook workbook;// 工作薄

	private SXSSFSheet sheet;// 表格

	private CellStyle headStyle;// 表头单元格样式

	private CellStyle rowStyle;// 数据单元格样式

	// 初始化代码行
	public void initPageExcelExport(String sheetName, int rowAccessWindowSize) throws Exception {
		log.debug("开始初始化分页导出Excel数据文档, sheetName ：{}", sheetName);
		// 声明一个工作薄
		workbook = new SXSSFWorkbook(rowAccessWindowSize);
		// 生成一个表格
		sheet = (SXSSFSheet) workbook.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20); // 统一设置列宽
		try {
			/// excel样式
			CellStyle centerStyle = workbook.createCellStyle();
			CellStyle cellStyleCenter = workbook.createCellStyle(); // 头部布局
			CellStyle cellStyleLeft = workbook.createCellStyle();
			cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
//			cellStyleCenter.setAlignment(HSSFCellStyle.); // 水平布局：居中
			cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			cellStyleCenter.setWrapText(true);
//			cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平布局：居左
//			cellStyleLeft.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyleLeft.setWrapText(true);

			Font font = workbook.createFont();
			font.setColor(Font.COLOR_NORMAL); // 字体颜色
			font.setFontName("黑体"); // 字体
//			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度

			// 设置标题单元格类型
			centerStyle.setFont(font);
//			centerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			centerStyle.setWrapText(true);
//			centerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
//			centerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置前景填充样式
//			centerStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);// 前景填充色
//			CreationHelper createHelper = workbook.getCreationHelper();
			// 设置标题边框
//			centerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//			centerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//			centerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//			centerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	// 构建标题行
	public void buildExcelModelHead(String[] columnName) {
		// 设置表格标题行
		Row row = sheet.createRow(0);
		for (int i = 0; i < columnName.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnName[i]);
			cell.setCellStyle(headStyle);
		}
		log.debug("excel模板表头信息构建完成 , 数据列总数：{}", columnName.length);
	}

	// 写入数据
	public void buildExcelData(List<Map<String, Object>> dataList, String[] columnName, String[] valueName) {
		int currentPage = 1;
		// 设置表格标题行
		int lastRow = 0;
		if (currentPage == 1) {
			lastRow = 1;
		} else {
			lastRow = (currentPage - 1) * workbook.getRandomAccessWindowSize();
		}
		Row row = null;
		// int lastRow = sheet.getLastRowNum();
		if (dataList != null) {
			// 遍历集合数据，设置数据行
			for (int i = 0; i < dataList.size(); i++) {
				row = sheet.createRow(++lastRow);
				// 为数据内容设置特点新单元格样式1 自动换行 上下居中
				Cell datacell = null;
				Map<String, Object> map = dataList.get(i);
				for (int j = 0; j < valueName.length; j++) {
					datacell = row.createCell(j);
					datacell.setCellValue(String.valueOf(map.get((String) valueName[j])).equals("null") ? ""
							: String.valueOf(map.get((String) valueName[j])));
					datacell.setCellStyle(rowStyle);
				}
				map.clear();
			}
			try {
				sheet.flushRows();
			} catch (IOException e) {
				e.printStackTrace();
			}
//				currentPage = currentPage + 1;
//				page.
//				page.setCurrent(currentPage);
			// 每创建完成一个sheet页就把数据刷新到磁盘
//				sheet.flushRows();
//				dataList.clear();
		}

	}

	// 导入到excel
	public void commintDataToExcel(OutputStream out) throws IOException {
		try {
			// 调用导出方法生成最终的 poi-sxssf-template.xlsx 临时文件，并且此方法包含删除此临时文件的方法
			workbook.write(out);
			// 此方法能够删除导出过程中生成的xml临时文件
			workbook.dispose();
		} catch (IOException e) {
			log.error("导入excel异常!");
			// throw new IOException();
		} finally {
			out.close();
		}
	}
	public static void main(String[] args) throws Exception {
		String[] columListHeader = new String[] {"columa","columb","columc","columd"};
		String[] columListValueProperty = new String[] {"columa-value","columb-value","columc-value","columd-value"};
		
		Path tempFile = Files.createTempFile("/path/tmp",".tmp", null);
		SXSSFExcel excel = new SXSSFExcel();
		excel.initPageExcelExport("excel sheetName", 500);
		excel.buildExcelModelHead(columListHeader);
		

	}
}