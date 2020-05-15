package tcb.shms.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import tcb.shms.module.config.SystemConfig;

@Service
public class ExcelService {

	private final static Logger log = LogManager.getLogger(ExcelService.class);

	/**
	* 建立Excel
	* 
	* @param list
	*      資料
	 * @return 
	 * @throws IOException 
	*/
	private static File createExcel(String fileName, String sheetName, List<String> titleList, List<List<String>> dataList) throws IOException {
		// 建立一個Excel檔案
		HSSFWorkbook  workbook = new HSSFWorkbook();
		// 建立一個工作表
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 新增表頭行
		HSSFRow hssfHeadRow = sheet.createRow(0);
		// 設定單元格格式居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		// 新增表頭內容		
		if(titleList != null && titleList.size() > 0) {
			for (int i = 0; i < titleList.size(); i++  ) {
				HSSFCell headCell = hssfHeadRow.createCell(i);				
				String title = titleList.get(i);
				headCell.setCellValue(title);
				headCell.setCellStyle(cellStyle);
			}
		}
		// 新增資料內容
		if(dataList != null && dataList.size() > 0) {
			for (int i = 1; i <= dataList.size(); i++  ) {
				HSSFRow hssfDataRow = sheet.createRow(i);
				List<String> dataRowList = dataList.get((i-1));
				for (int j = 0; j < dataRowList.size(); j++  ) {
					HSSFCell headCell = hssfDataRow.createCell(j);
					String data = dataRowList.get(j);
					headCell.setCellValue(data);
					headCell.setCellStyle(cellStyle);
				}
				
			}
		}
		// 儲存Excel檔案
		try {
			String filePathName = SystemConfig.EXCEL_PATH.DEFAULT_CREATE_PATH + fileName + ".xls";
			File file = new File(filePathName);
			File filePath = new File(SystemConfig.EXCEL_PATH.DEFAULT_CREATE_PATH);
			if(!filePath.exists()) {
				filePath.mkdirs();
			}
			OutputStream outputStream = new FileOutputStream(filePathName);
			workbook.write(outputStream);
			outputStream.close();
			
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workbook.close();
		}
		return null;
	}

	
	public static void main(String args[]) throws IOException {
		List<String> titleList = new ArrayList<String>();
		
		titleList.add("title1");
		titleList.add("title2");
		titleList.add("title3");
		titleList.add("title4");
		
		List<List<String>> dataList = new ArrayList<List<String>>();
		
		dataList.add(new ArrayList<String>() {{
			add("row1data1");
			add("row1data2");
			add("row1data3");
			add("row1data4");
		}});
		dataList.add(new ArrayList<String>() {{
			add("row2data1");
			add("row2data2");
			add("row2data3");
			add("row2data4");
		}});
		dataList.add(new ArrayList<String>() {{
			add("row3data1");
			add("row3data2");
			add("row3data3");
			add("row3data4");
		}});
		File file = createExcel("testFile", "testSheet", titleList, dataList);
		
		System.out.println(file.exists());
		System.out.println(file.getName());
		System.out.println(file.getTotalSpace());
		System.out.println(file.getParent());
	}
}
