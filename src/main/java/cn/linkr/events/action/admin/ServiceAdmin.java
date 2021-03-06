package cn.linkr.events.action.admin;

import java.util.HashMap;
import java.util.LinkedList;
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
import cn.linkr.events.entity.EvtService;
import cn.linkr.events.entity.SysUser;
import cn.linkr.events.service.ServiceService;
import cn.linkr.events.util.AuthPassport;
import cn.linkr.events.util.BeanUtilEx;
import cn.linkr.events.util.Constant;
import cn.linkr.events.vo.ServiceVo;

import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping(Constant.API_PATH_ADMIN + "service/")
public class ServiceAdmin extends BaseAction {
	@Autowired
	ServiceService serviceService;
	
	@AuthPassport(validate = true)
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, @RequestBody JSONObject req) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String eventId = req.getString("eventId");
		
		List<EvtService> pos = serviceService.listForEdit(Long.valueOf(eventId), null);
        List<ServiceVo> vos = new LinkedList<ServiceVo>();
        for (EvtService po: pos) {
        	ServiceVo vo = new ServiceVo();
        	BeanUtilEx.copyProperties(vo, po);
        	vo.setTypeName(po.getType().getName());
        	vos.add(vo);
        }

		ret.put("services", vos);
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}
	
	@AuthPassport(validate = true)
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, @RequestBody ServiceVo vo) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		EvtService service = serviceService.save(vo);
		
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}
	
	@AuthPassport(validate = true)
	@RequestMapping(value = "disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(HttpServletRequest request, @RequestBody JSONObject to) {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		long serviceId = to.getLong("id");
		
		boolean success = serviceService.disablePers(serviceId);
		
		ret.put("code", Constant.RespCode.SUCCESS.getCode());
		return ret;
	}
}
