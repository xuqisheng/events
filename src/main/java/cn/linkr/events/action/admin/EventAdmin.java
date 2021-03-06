package cn.linkr.events.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkr.events.action.client.BaseAction;
import cn.linkr.events.entity.EvtBanner;
import cn.linkr.events.entity.EvtClient;
import cn.linkr.events.entity.EvtDocument;
import cn.linkr.events.entity.EvtEvent;
import cn.linkr.events.entity.EvtOrganizer;
import cn.linkr.events.entity.SysUser;
import cn.linkr.events.service.BannerService;
import cn.linkr.events.service.DocumentService;
import cn.linkr.events.service.EventService;
import cn.linkr.events.service.OrganizerService;
import cn.linkr.events.util.AuthPassport;
import cn.linkr.events.util.Constant;
import cn.linkr.events.vo.BannerVo;
import cn.linkr.events.vo.DocumentVo;
import cn.linkr.events.vo.EventVo;
import cn.linkr.events.vo.OrganizerVo;
import cn.linkr.events.vo.Page;
import cn.linkr.events.vo.UserVo;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping(Constant.API_PATH_ADMIN + "event/")
public class EventAdmin extends BaseAction {
	@Autowired
	EventService eventService;
	@Autowired
	DocumentService documentService;
	@Autowired
	BannerService bannerService;
	@Autowired
	OrganizerService organizerService;
	
	@AuthPassport(validate = true)
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody JSONObject req) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		UserVo vo = (UserVo) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);
		
		int currentPage = req.getString("currentPage") == null? 0: Integer.valueOf(req.getString("currentPage")) - 1;
		int itemsPerPage = req.getString("itemsPerPage") == null? Constant.PAGE_SIZE: Integer.valueOf(req.getString("itemsPerPage"));
		String status = req.getString("status");
		
		Page page = eventService.list(vo.getCompanyId(), status, currentPage, itemsPerPage);
		List<EventVo> vos = eventService.genVos(page.getItems());
        
		ret.put("totalItems", page.getTotal());
        ret.put("events", vos);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}
	
	@AuthPassport(validate = true)
	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> get(HttpServletRequest request) {
		Map<String, Object> ret = new HashMap<String, Object>();
		JSONObject req = reqJson(request);
		String eventId = req.getString("eventId");
		
		EvtClient client = (EvtClient) request.getSession().getAttribute(Constant.HTTP_SESSION_CLIENT_KEY);
		
		EvtEvent event = eventService.getDetail(Long.valueOf(eventId));
        EventVo eventVo = eventService.genVo(event);
		
		List<EvtDocument> docPos = documentService.listByEvent(Long.valueOf(eventId), null);
        List<DocumentVo> docVos = documentService.genVos(docPos);
        
		List<EvtBanner> bannerPos = bannerService.listByEvent(Long.valueOf(eventId));
		List<BannerVo> bannerVos = bannerService.genVos(bannerPos);
        
        List<EvtOrganizer> organizerPos = organizerService.listByEvent(Long.valueOf(eventId));
        Map<String, List<OrganizerVo>> organizerMap = organizerService.genOrganizerMap(organizerPos);
        
        eventVo.setDocuments(docVos);
        eventVo.setBanners(bannerVos);
        eventVo.setOrganizers(organizerMap);
        
        ret.put("event", eventVo);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}
	
	@AuthPassport(validate = true)
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestBody EventVo vo) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		UserVo userVo = (UserVo) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_KEY);
		
		EvtEvent event = eventService.save(vo, userVo.getId(), userVo.getCompanyId());
        EventVo eventVo = eventService.genVo(event);
        
        ret.put("event", eventVo);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}
	
}
