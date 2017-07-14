package com.lyh.ccqutil.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

	private static Logger logger = Logger.getLogger(ExcelUtil.class);
	private final static String xls = "xls";
	private final static String xlsx = "xlsx";

	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static Map<String, List<String[]>> readExcel(MultipartFile file) throws IOException {
		// 检查文件
		checkFile(file);
		return readExcel(file.getOriginalFilename(), file.getInputStream());
	}

	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static Map<String, List<String[]>> readExcel(String filePath) throws IOException {
		File file = new File(filePath);
		String fileName = file.getName();
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
			logger.error(filePath + "不是excel文件");
			throw new IOException(filePath + "不是excel文件");
		}
		InputStream is = new FileInputStream(file);
		return readExcel(fileName, is);
	}

	/**
	 * 读入excel文件，解析后返回
	 * 
	 * @param fileName
	 *            文件名
	 * @param is
	 *            文件流
	 * @throws IOException
	 */
	public static Map<String, List<String[]>> readExcel(String fileName, InputStream is) throws IOException {
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(fileName, is);
		Map<String, List<String[]>> result = new HashMap<String, List<String[]>>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
				List<String[]> list = new ArrayList<String[]>();
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				// 获得当前sheet的开始行
				int firstRowNum = sheet.getFirstRowNum();
				// 获得当前sheet的结束行
				int lastRowNum = sheet.getLastRowNum();
				// 循环除了第一行的所有行
				for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
					// 获得当前行
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					// 获得当前行的开始列
					int firstCellNum = row.getFirstCellNum();
					// 获得当前行的列数
					int lastCellNum = row.getPhysicalNumberOfCells();
					String[] cells = new String[row.getPhysicalNumberOfCells() + 1];
					cells[0] = rowNum + "";
					// 循环当前行
					for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
						Cell cell = row.getCell(cellNum);
						cells[cellNum + 1] = getCellValue(cell);
					}
					list.add(cells);
				}
				result.put("sheet" + (sheetNum + 1), list);
			}
			workbook.close();
		}
		return result;
	}

	public static void checkFile(MultipartFile file) throws IOException {
		// 判断文件是否存在
		if (null == file) {
			logger.error("文件不存在！");
			throw new FileNotFoundException("文件不存在！");
		}
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 判断文件是否是excel文件
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
			logger.error(fileName + "不是excel文件");
			throw new IOException(fileName + "不是excel文件");
		}
	}

	public static Workbook getWorkBook(String fileName, InputStream is) {
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls)) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return workbook;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;
		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}

	public static void main(String[] args) throws IOException {
		Map<String, List<String[]>> readExcel = readExcel("C:/Users/lyhcc/Desktop/工作簿1.xlsx");
		for (Entry<String, List<String[]>> entry : readExcel.entrySet()) {
			String sheet = entry.getKey();
			List<String[]> list = entry.getValue();
			System.out.println(sheet + ":");
			for (String[] row : list) {
				for (int i = 0; i < row.length; i++) {
					if (i < row.length - 1) {
						System.out.print(row[i] + ",");
					} else {
						System.out.println(row[i]);
					}
				}
			}
		}
	}
}
