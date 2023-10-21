package com.zyl.poi;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * 
 * @Title: ExcelExportUtils.java 
 * @Description: 导出Excel数据文档
 * @Author ouyangli
 * @Date 2019年4月7日 15:05:57
 * @Version 1.0.1
 */
public class ExcelExportUtil<T> {

	private static Logger log = LoggerFactory.getLogger(ExcelExportUtil.class);

	private HSSFWorkbook workbook;// 工作薄

	private HSSFSheet sheet;// 表格

	private HSSFCellStyle headStyle;// 表头单元格样式

	private HSSFCellStyle rowStyle;// 数据单元格样式

	/**
	 * 
	 * Title: initPageExcelExport Description: 初始化分页导出excel对象信息
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:06:24
	 * @Version 1.0.1
	 * @param sheetName
	 */
	public void initPageExcelExport(String sheetName) {
		log.debug("开始初始化分页导出Excel数据文档, sheetName ：{}", sheetName);
		// 声明一个工作薄
		workbook = new HSSFWorkbook();
		// 生成一个表格
		sheet = workbook.createSheet(sheetName);
		try {
			// 设置表格默认列宽度为20个字节
			sheet.setDefaultColumnWidth((short) 14);
			// 设置表头单元格样式
			headStyle = workbook.createCellStyle();
			headStyle.setFillForegroundColor((short) 40);// HSSFColor.SKY_BLUE.index
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headStyle.setAlignment(HorizontalAlignment.LEFT);
			// 设置表头的字体样式
			HSSFFont headFont = workbook.createFont();
			headFont.setColor((short) 8);// HSSFColor.BLACK.index
			headFont.setFontHeightInPoints((short) 12);
			headFont.setBold(true);
			headStyle.setFont(headFont);

			// 设置数据单元格样式
			rowStyle = workbook.createCellStyle();
			rowStyle.setAlignment(HorizontalAlignment.LEFT);
			rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// 设置数据单元格字体样式
			HSSFFont rowFont = workbook.createFont();
			rowFont.setBold(false);
			rowStyle.setFont(rowFont);
		} catch (Exception e) {
			throw new RuntimeException("初始化分页导出Excel文档失败");
		}
	}

	/**
	 * 
	 * Title: buildPageDataToExcel Description: 构建一页数据到excel对象中
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:08:28
	 * @Version 1.0.1
	 * @param dataSet
	 * @param pattern
	 * @throws BusinessException
	 */
	public void buildPageDataToExcel(List<T> dataSet,List<String> extraColumnList, int startIndex, String pattern) {
		if (dataSet == null || dataSet.isEmpty())
			throw new RuntimeException("导出数据集合对象为空");
		if(extraColumnList == null)
			extraColumnList = new ArrayList<>(0);
		if (sheet != null && headStyle != null && rowStyle != null)
			buildPageExcel(dataSet, extraColumnList, startIndex, pattern);
	}

	/**
	 * 
	 * Title: commintDataToExcel Description: 提交分页数据到excel输出流中
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:08:33
	 * @Version 1.0.1
	 * @param out
	 * @throws BusinessException
	 * @throws IOException
	 */
	public void commintDataToExcel(OutputStream out) throws IOException {
		try {
			workbook.write(out);
		} catch (IOException e) {
			throw new RuntimeException("workbook.write exception : " + e.getMessage());
		} finally {
			workbook.close();
		}
	}

	/**
	 * 
	 * Title: exportExcel Description: 一次性导出所有数据到excel文件(适用少量数据导出)
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:08:42
	 * @Version 1.0.1
	 * @param sheetName
	 *            sheet页名称
	 * @param dataSet
	 *            导出数据集对象
	 * @param out
	 *            文件输出流
	 * @param pattern
	 *            时间类型格式
	 * @throws Exception
	 */
	public static <T> void exportExcel(String sheetName, List<T> dataSet, List<String> extraColumnList, OutputStream out, String pattern){
		if (dataSet == null || dataSet.isEmpty())
			throw new RuntimeException("导出数据集合对象为空");
		if(extraColumnList == null)
			extraColumnList = new ArrayList<String>(0);
		log.debug("开始导出Excel数据文档, sheetName ：{}", sheetName);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		try {
			// 设置表格默认列宽度为20个字节
			sheet.setDefaultColumnWidth((short) 22);
			// 设置表头单元格样式
			HSSFCellStyle headStyle = workbook.createCellStyle();
			headStyle.setFillForegroundColor((short) 42);// HSSFColor.LIGHT_GREEN
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headStyle.setAlignment(HorizontalAlignment.LEFT);
			// 设置表头的字体样式
			HSSFFont headFont = workbook.createFont();
			headFont.setColor((short) 8);// HSSFColor.BLACK.index
			headFont.setFontHeightInPoints((short) 11);
			headFont.setBold(true);
			headStyle.setFont(headFont);

			// 设置数据单元格样式
			HSSFCellStyle rowStyle = workbook.createCellStyle();
			rowStyle.setAlignment(HorizontalAlignment.LEFT);
			rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			// 设置数据单元格字体样式
			HSSFFont rowFont = workbook.createFont();
			rowFont.setBold(false);
			rowStyle.setFont(rowFont);
			
			// 加载excel数据信息
			buildExcelData(sheet, headStyle, rowStyle, dataSet,extraColumnList, pattern);
			workbook.write(out);
		} catch (Exception e) {
			throw new RuntimeException("导出Excel文档失败");
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("workbook.close exception : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * @Description: 导出Excel模板文件
	 * @author ouyangli
	 * @date 2019年4月7日 15:08:57
	 * @param sheetName 工作表名
	 * @param clazz 头部对象
	 * @param out 输出流
	 */
	public static <T> void exportExcelModel(String sheetName, T clazz, OutputStream out) {
		log.debug("开始导出Excel模板文件, sheetName ：{}", sheetName);
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(sheetName);
		try {
			// 设置表格默认列宽度为20个字节
			sheet.setDefaultColumnWidth((short) 12);
			// 设置表头单元格样式
			HSSFCellStyle headStyle = workbook.createCellStyle();
			headStyle.setFillForegroundColor((short) 42);// HSSFColor.LIGHT_GREEN
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headStyle.setAlignment(HorizontalAlignment.CENTER);
			// 设置表头的字体样式
			HSSFFont headFont = workbook.createFont();
			headFont.setColor((short) 8);// HSSFColor.BLACK.index
			headFont.setFontHeightInPoints((short) 11);
			headFont.setBold(true);
			headStyle.setFont(headFont);

			// 加载excel模板头部信息
			buildExcelModelHead(sheet, headStyle, clazz);
			workbook.write(out);
		} catch (Exception e) {
			log.error("exportExcelModel() error 导出Excel模板文件异常", e);
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				log.error("exportExcelModel() --> workbook.close exception : " + e.getMessage(), e);
			}
		}
	}

	private static <T> void buildExcelModelHead(HSSFSheet sheet, HSSFCellStyle headStyle, T clazz) {
		// 设置表格标题行
		HSSFRow row = sheet.createRow(0);
		Field[] heads = clazz.getClass().getDeclaredFields();
		List<String> headList = new ArrayList<>();
		// 获取字段注解的表头
		for (int i = 0; i < heads.length; i++) {
			// 过滤掉没加注解的字段 + 排除在外的列
			if (heads[i].getAnnotations().length == 0 )
				continue;
			Excel exAnno = (Excel) heads[i].getAnnotations()[0];
			if(exAnno.isDefault())
				headList.add(exAnno.head());
		}

		for (int i = 0; i < headList.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(headList.get(i));
			cell.setCellValue(text);
		}
		log.debug("excel模板表头信息构建完成 , 数据列总数：{}", headList.size());
	}


	/**
	 * 
	 * Title: buildExcelData Description: 构建sheet数据表格内容
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:12:10
	 * @Version 1.0.1
	 * @param sheet
	 * @param headStyle
	 * @param rowStyle
	 * @param dataSet
	 * @param pattern
	 */
	private static <T> void buildExcelData(HSSFSheet sheet, HSSFCellStyle headStyle, HSSFCellStyle rowStyle,
			List<T> dataSet,List<String> extraColumnList, String pattern) {
		// 设置表格标题行
		HSSFRow row = sheet.createRow(0);
		buildSheetTableHead(dataSet, extraColumnList, headStyle, row);
		// 遍历集合数据，设置数据行
		Iterator<T> it = dataSet.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T tObj = (T) it.next();
			Field[] fields = tObj.getClass().getDeclaredFields();
			List<Field> fieldsList = new ArrayList<Field>();
			for (Field field : fields) {
				if (field.getAnnotations().length == 0 )
					continue;
				Excel exAnno = (Excel) field.getAnnotations()[0];
				if(exAnno.isDefault() || (!exAnno.isDefault() && extraColumnList.contains(field.getName())) )
					fieldsList.add(field);
			}
			buildRowData(fieldsList, rowStyle, row, tObj, pattern);
		}
	}
	
	private static <T> void buildRowData(List<Field> fieldsList, HSSFCellStyle rowStyle,HSSFRow row,T tObj, String pattern) {
		Class<? extends Object> tCls = tObj.getClass();
		for (Field field : fieldsList) {
			HSSFCell cell = row.createCell(fieldsList.indexOf(field));
			cell.setCellStyle(rowStyle);
			String fieldName = field.getName();
			String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Object value = null;
			try {
				Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
				value = getMethod.invoke(tObj, new Object[] {});
				buildSheetDataTypeFormat(cell, value, pattern);
			} catch (Exception e) {
				log.error(" invoke method exception : " + e.getMessage(), e);
				throw new RuntimeException(" invoke method get value exception ： " + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * Title: buildPageExcel Description: 构建分页导出excel的列表数据
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:12:18
	 * @Version 1.0.1
	 * @param dataSet
	 * @param startIndex
	 * @param pattern
	 */
	private void buildPageExcel(List<T> dataSet,List<String> extraColumnList, int startIndex, String pattern) {
		if (startIndex == 0) {
			// 设置表格标题行
			HSSFRow row = sheet.createRow(0);
			buildSheetTableHead(dataSet, extraColumnList, headStyle, row);
		}
		// 遍历集合数据，设置数据行
		Iterator<T> it = dataSet.iterator();
		HSSFRow row = null;
		int index = startIndex;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T tObj = (T) it.next();
			Field[] fields = tObj.getClass().getDeclaredFields();
			List<Field> fieldsList = new ArrayList<>();
			for (Field field : fields) {
				// 过滤掉没加注解的字段 + 排除在外的列
				if (field.getAnnotations().length == 0 )
					continue;
				Excel exAnno = (Excel) field.getAnnotations()[0];
				if(exAnno.isDefault() || (!exAnno.isDefault() && extraColumnList.contains(field.getName())) )
					fieldsList.add(field);
			}
			buildRowData(fieldsList, row, tObj, pattern);
		}
	}
	
	private void buildRowData(List<Field> fieldsList,HSSFRow row,T tObj, String pattern) {
		for (Field field : fieldsList) {
			HSSFCell cell = row.createCell(fieldsList.indexOf(field));
			cell.setCellStyle(rowStyle);
			String fieldName = field.getName();
			String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Class<? extends Object> tCls = tObj.getClass();
			Object value = null;
			try {
				Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
				value = getMethod.invoke(tObj, new Object[] {});
				buildSheetDataTypeFormat(cell, value, pattern);
			} catch (Exception e) {
				log.error(" invoke method exception : " + e.getMessage(), e);
				throw new RuntimeException(" invoke method get value exception ： " + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * Title: buildSheetTableHead Description: 构建sheet表格的表头属性
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:12:27
	 * @Version 1.0.1
	 * @param dataSet
	 * @param headStyle
	 * @param row
	 */
	private static <T> void buildSheetTableHead(List<T> dataSet, List<String> extraColumnList, HSSFCellStyle headStyle,
			HSSFRow row) {
		T tempT = dataSet.get(0);
		Field[] heads = tempT.getClass().getDeclaredFields();
		List<String> headList = new ArrayList<>();
		// 获取字段注解的表头
		for (int i = 0; i < heads.length; i++) {
			// 过滤掉没加注解的字段 + 排除在外的列
			if (heads[i].getAnnotations().length == 0 )
				continue;
			Excel exAnno = (Excel) heads[i].getAnnotations()[0];
			if(exAnno.isDefault() || (!exAnno.isDefault() && extraColumnList.contains(heads[i].getName())) )
				headList.add(exAnno.head());
		}

		for (int i = 0; i < headList.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(headList.get(i));
			cell.setCellValue(text);
		}
		log.debug("excel数据表格表头信息构建完成 , 数据列总数：{}", headList.size());
	}

	/**
	 * 
	 * Title: sheetDataTypeFormat Description: 判断值的类型后进行强制类型转换
	 * 
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:12:37
	 * @Version 1.0.1
	 * @param cell
	 * @param value
	 * @param pattern
	 */
	private static void buildSheetDataTypeFormat(HSSFCell cell, Object value, String pattern) {
		String dataType = null;
		if (value instanceof Integer) {
			dataType = "Integer";
			int intValue = (Integer) value;
			cell.setCellValue(intValue);
		} else if (value instanceof Float) {
			dataType = "Float";
			float fValue = (Float) value;
			cell.setCellValue(fValue);
		} else if (value instanceof Double) {
			dataType = "Double";
			double dValue = (Double) value;
			cell.setCellValue(dValue);
		} else if (value instanceof Long) {
			dataType = "Long";
			long longValue = (Long) value;
			cell.setCellValue(longValue);
		} else if (value instanceof Date) {
			dataType = "Date";
			Date date = (Date) value;
			if (pattern == null || "".equals(pattern))
				pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			cell.setCellValue(sdf.format(date));
		} else {
			// 其它数据类型都当作字符串简单处理
			String textValue = value == null ? "" : value.toString();
			cell.setCellValue(textValue);
		}
		log.debug("excel数据表数据类型格式化完成: dataType:[{}], value:[{}]", dataType, value);
	}

	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

}
