package tcb.shms.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tcb.shms.module.service.ErrorLogService;

@Service
public class CsvService {

	private final static Logger log = LogManager.getLogger(CsvService.class);
	
	@Autowired
	protected ErrorLogService  errorLogService;

	public List<List<String>> readCsvFile(String fileName) {
		List<List<String>> result = new ArrayList<List<String>>();
		File csvFile = new File(fileName);
		InputStreamReader isr = null;
		BufferedReader reader = null;	

		try {
			isr = new InputStreamReader(new FileInputStream(csvFile),"BIG5");// 檔案讀取路徑
			reader = new BufferedReader(isr);			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				String[] item = line.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(item));
				result.add(list);
			}
		} catch (FileNotFoundException e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		} catch (IOException e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}finally {
			try {
				reader.close();
				isr.close();
			} catch (IOException e) {
				log.error("",e);
				errorLogService.addErrorLog(this.getClass().getName(), e);
			}
		}

		return result;
	}
	
	public static void main(String args[]) {
		new CsvService().readCsvFile("C://Users//mark3835//Desktop//test2.csv");
	}

}
