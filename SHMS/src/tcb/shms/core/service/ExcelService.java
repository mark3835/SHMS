package tcb.shms.core.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {
	
	private final static Logger log = LogManager.getLogger(ExcelService.class);
	
	public File createExcel(List<Map> dataList) {

        HSSFWorkbook workbook=new HSSFWorkbook();
        File f=new File("D:/Test/test.xls");
        GeericExcelGenerator gl=new GeericExcelGenerator("Test",workbook);
        FileOutputStream outputStream=new FileOutputStream(f);
        gl.generate(outputStream,arrlist);
	}

}
