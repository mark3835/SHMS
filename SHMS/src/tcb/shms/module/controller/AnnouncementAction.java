package tcb.shms.module.controller;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.Announcement;
import tcb.shms.module.entity.Menu;
import tcb.shms.module.service.AnnouncementService;

/**
 * @author MARK3835
 *
 */
@Controller
public class AnnouncementAction extends GenericAction<Menu> {

	@Autowired
	AnnouncementService announcementService;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

	@RequestMapping(value = "/announcement/getAnnouncement", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			List<Announcement> announcementList = announcementService.getList(new Announcement());
			jsonInString = new GsonBuilder().setDateFormat("yyyy/MM/dd").create().toJson(announcementList);
		} catch (Exception e) {
			log.error(e);
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
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/announcement/api/addAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String addAnnouncement(@RequestParam("announcementFile") MultipartFile[] multipartFiles,HttpServletRequest request) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {					
			Announcement announcement = new Announcement();
			announcement.setAnnouncementDate(format.parse(request.getParameter("announcementDate")));
			announcement.setAnnouncementName(request.getParameter("announcementName"));
			Map<String, String[]> paramMap = request.getParameterMap();
			for (Entry<String, String[]> entry : paramMap.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			}
			 if (multipartFiles != null && multipartFiles.length > 0) {
		            for (MultipartFile multipartFile : multipartFiles) {
		                try {
		                    if (!(multipartFile.isEmpty())) {
		                        String fileName = multipartFile.getOriginalFilename();
		                        long fileSize = multipartFile.getSize();
		                        byte[] bytes = multipartFile.getBytes();
		                         // Call the save to DB or disk here.
		                        log.info(fileName);
		                        log.info(fileSize);
		                        log.info(bytes);
		                       }
		                } catch (Exception e) {
		                    return "failure";
		                }
		            }
		        }
			announcement = announcementService.save(announcement);
			resultMap.put("result", "success");
			resultMap.put("id", announcement.getId().toString());					
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");		
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/announcement/api/updateAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String updateAnnouncement(@RequestParam("files") MultipartFile[] files,HttpServletRequest request) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {		
			Announcement announcement = announcementService.getById(Long.parseLong(request.getParameter("id")));
			announcement.setAnnouncementDate(format.parse(request.getParameter("announcementDate")));
			announcement.setAnnouncementName((String)request.getAttribute("announcementName"));
			log.info(files[0].getName());
			log.info(files[0].getSize());
			announcementService.update(announcement);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");	
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/announcement/api/deleteAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String deleteAnnouncement(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			announcementService.deleteById(MapUtils.getLong(map, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");	
		}

		return new Gson().toJson(resultMap);
	}

}
