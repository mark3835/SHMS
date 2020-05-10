package tcb.shms.core.service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

	private final static Logger log = LogManager.getLogger(ExcelService.class);

	/**
	* 建立Excel
	* 
	* @param list
	*      資料
	*/
	private static void createExcel(List<String> titleList,List<String> dataList) {
		// 建立一個Excel檔案
		HSSFWorkbook  workbook = new HSSFWorkbook();
		// 建立一個工作表
		HSSFSheet sheet = workbook.createSheet("學生表一");
		// 新增表頭行
		HSSFRow hssfRow = sheet.createRow(0);
		// 設定單元格格式居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		// 新增表頭內容
		HSSFCell headCell = hssfRow.createCell(0);
		headCell.setCellValue("姓名");
		headCell.setCellStyle(cellStyle);
		headCell = hssfRow.createCell(1);
		headCell.setCellValue("年齡");
		headCell.setCellStyle(cellStyle);
		headCell = hssfRow.createCell(2);
		headCell.setCellValue("年級");
		headCell.setCellStyle(cellStyle);
//		// 新增資料內容
//		if(titleList != null && titleList.) {
//			
//		}
//		for (int i = 0; i < list.size(); i++  ) {
//			hssfRow = sheet.createRow((int) i);
//			Student student = list.get(i);
//			// 建立單元格，並設定值
//			HSSFCell cell = hssfRow.createCell(0);
//			cell.setCellValue(student.getName());
//			cell.setCellStyle(cellStyle);
//			cell = hssfRow.createCell(1);
//			cell.setCellValue(student.getAge());
//			cell.setCellStyle(cellStyle);
//			cell = hssfRow.createCell(2);
//			cell.setCellValue(student.getGrade());
//			cell.setCellStyle(cellStyle);
//		}
		// 儲存Excel檔案
		try {
			OutputStream outputStream = new FileOutputStream("D:/students.xls");
			workbook.write(outputStream);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
