package tcb.shms.module.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Announcement;
import tcb.shms.module.entity.Menu;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.AnnouncementService;

/**
 * @author MARK3835
 *
 */
@Controller
public class AnnouncementAction extends GenericAction<Menu> {

	@Autowired
	AnnouncementService announcementService;

	SimpleDateFormat timeFormat = new SimpleDateFormat("MMddhhmmssSSS");

	@RequestMapping(value = "/announcement/getAnnouncement", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			Announcement announcement = new Announcement();
			announcement.setAnnouncementDate(new Date());
			List<Announcement> announcementList = announcementService.getAnnouncementListBeforeToday(announcement);
			jsonInString = new GsonBuilder().setDateFormat("yyyy/MM/dd").create().toJson(announcementList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

	@RequestMapping(value = "/announcement/api/getAnnouncement", method = RequestMethod.GET)
	public @ResponseBody String getApiAnnouncement() {
		String jsonInString = null;
		try {
			List<Announcement> announcementList = announcementService.getList(new Announcement());
			jsonInString = new GsonBuilder().setDateFormat("yyyy/MM/dd").create().toJson(announcementList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

	@RequestMapping(value = "/announcement/api/addAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String addAnnouncement(@RequestParam("announcementFile") MultipartFile[] multipartFiles,
			HttpServletRequest request) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try {
			Announcement announcement = new Announcement();
			if (multipartFiles != null && multipartFiles.length > 0) {
				for (MultipartFile multipartFile : multipartFiles) {
					if (!(multipartFile.isEmpty())) {
						String fileName = multipartFile.getOriginalFilename();
						long fileSize = multipartFile.getSize();
						byte[] bytes = multipartFile.getBytes();
						String fileType = multipartFile.getContentType();
						// Call the save to DB or disk here.
						log.info(fileName);
						log.info(fileSize);
						log.info(bytes);
						log.info(fileType);
						if(!SystemConfig.ANNOUNCEMENT.FILE_TYPE_PDF.equals(fileType)) {
							throw new Exception("檔案類型需為pdf");
						}
						String path = SystemConfig.ANNOUNCEMENT.FILE_ROOT_PATH + timeFormat.format(new Date());
						log.info( path);
						
						String[] fileNameSplit = fileName.split("\\\\");
						if(fileNameSplit.length > 1) {
							fileName = fileNameSplit[fileNameSplit.length-1];
						}
						
						File file = new File(  path + "/" + fileName);
						file.getParentFile().mkdirs();
						file.createNewFile();
						FileOutputStream fileOutput = new FileOutputStream(file);

						fileOutput.write(multipartFile.getBytes());
						fileOutput.flush();
						fileOutput.close();
						
						announcement.setFilePath(path);
						announcement.setFileName(fileName);
					}
				}
			}
			announcement.setAnnouncementDate(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(request.getParameter("announcementDate")));
			announcement.setAnnouncementName(request.getParameter("announcementName"));
			User user = getSessionUser();
			announcement.setCreateId(user.getRocId());
			announcement.setCreateTime(new Timestamp(System.currentTimeMillis()));
			announcement = announcementService.save(announcement);
			resultMap.put("result", "success");
			resultMap.put("id", announcement.getId().toString());
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}

	@RequestMapping(value = "/announcement/api/updateAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String updateAnnouncement(@RequestParam("announcementFile") MultipartFile[] multipartFiles,
			HttpServletRequest request) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try {
			Announcement announcement = announcementService.getById(Long.parseLong(request.getParameter("id")));
			announcement.setAnnouncementDate(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(request.getParameter("announcementDate")));
			announcement.setAnnouncementName(request.getParameter("announcementName"));
			
			if (multipartFiles != null && multipartFiles.length > 0) {
				for (MultipartFile multipartFile : multipartFiles) {
					if (!(multipartFile.isEmpty())) {
						File deleteFile = new File(announcement.getFilePath() + "/" + announcement.getFileName());	        
						File deleteFileParent = deleteFile.getParentFile();
						deleteFile.delete();
						deleteFileParent.delete();
						
						String fileName = multipartFile.getOriginalFilename();
						long fileSize = multipartFile.getSize();
						byte[] bytes = multipartFile.getBytes();
						String fileType = multipartFile.getContentType();
						// Call the save to DB or disk here.
						log.info(fileName);
						log.info(fileSize);
						log.info(bytes);
						log.info(fileType);
						if(!SystemConfig.ANNOUNCEMENT.FILE_TYPE_PDF.equals(fileType)) {
							throw new Exception("檔案類型需為pdf");
						}
						String path = SystemConfig.ANNOUNCEMENT.FILE_ROOT_PATH + timeFormat.format(new Date());
						log.info( path);
						File file = new File(  path + "/" + fileName);
						file.getParentFile().mkdirs();
						file.createNewFile();
						FileOutputStream fileOutput = new FileOutputStream(file);

						fileOutput.write(multipartFile.getBytes());
						fileOutput.flush();
						fileOutput.close();
						
						announcement.setFilePath(path);
						announcement.setFileName(fileName);
					}
				}
			}
			User user = getSessionUser();
			announcement.setEditId(user.getRocId());
			announcement.setEditTime(new Timestamp(System.currentTimeMillis()));
			announcementService.update(announcement);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}

	@RequestMapping(value = "/announcement/api/deleteAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String deleteAnnouncement(@RequestBody String data) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String, Object> map = new Gson().fromJson(data, HashMap.class);
			
			Announcement announcement = announcementService.getById(MapUtils.getLong(map, "ID"));
			File deleteFile = new File(announcement.getFilePath() + "/" + announcement.getFileName());	        
			File deleteFileParent = deleteFile.getParentFile();
			deleteFile.delete();
			deleteFileParent.delete();
			announcementService.deleteById(MapUtils.getLong(map, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}

	@RequestMapping(value = "/announcement/getAnnouncementFile/{announcementId}", method = RequestMethod.GET)
	public void getannouncementFile(@PathVariable("announcementId") String announcementId, HttpServletResponse response, HttpServletRequest request) throws Exception {
	    try {
	    	Announcement announcement = announcementService.getById(Long.parseLong(announcementId));
	    	
	        File fileToDownload = new File(announcement.getFilePath() + "/" + announcement.getFileName());	        
	        String filename = announcement.getFileName();
	        //檔名亂碼問題處理
	        // 获得请求头中的User-Agent
			String agent = request.getHeader("User-Agent");
			// 根据不同的客户端进行不同的编码
			String filenameEncoder = "";
			if (agent.contains("MSIE")) {
				// IE浏览器
				filenameEncoder = URLEncoder.encode(filename, "utf-8");
				filenameEncoder = filenameEncoder.replace("+", " ");
			} else if (agent.contains("Firefox")) {
				// 火狐浏览器
				org.apache.tomcat.util.codec.binary.Base64 base64Encoder = new org.apache.tomcat.util.codec.binary.Base64();
				filenameEncoder = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
			} else {
				// 其它浏览器
				filenameEncoder = URLEncoder.encode(filename, "utf-8");
			}
			InputStream inputStream = new FileInputStream(fileToDownload);
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "inline; filename=" + filenameEncoder); 
	        IOUtils.copy(inputStream, response.getOutputStream());
	        response.flushBuffer();
	        inputStream.close();
	    } catch (Exception e){
	        e.printStackTrace();
	    }

	}
}
