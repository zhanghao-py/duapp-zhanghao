package com.baidu.foo.ssh.demo.web.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baidu.foo.ssh.account.bo.SysUser;


@Controller  
@RequestMapping("index")
public class BookAction  {
	
	private static Logger log = Logger.getLogger(BookAction.class);

	
	@RequestMapping(value = "hello/{username}", method = RequestMethod.GET)  
    public ModelAndView hello(@PathVariable String username) {  
//		String username = "zhanghao_py";
		Assert.notNull(username, "username can't be null.");
		 
		ModelAndView mav = new ModelAndView("index/index");
		mav.addObject("username", username);
        return mav;  
    }
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> save(@ModelAttribute SysUser user) {  
		String username = user.getUsername();
		log.info(username);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("username", username);
		
        return jsonMap;  
    }
	
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseBody
    public Map<String, Object> update(@ModelAttribute SysUser user) {  
		String username = user.getUsername();
		log.info(username);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("username", username);
		
        return jsonMap;  
    }
	
	
	@RequestMapping(value = "hellojson", method = RequestMethod.GET) 
	@ResponseBody
    public Map<String, Object> hellojson() {  
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("status", 200);
		jsonMap.put("data", "zhanghao");
		
        return jsonMap;
    }
	
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download() throws IOException {
		File file = new File("/Users/zhanghao/Downloads/zhanghao.txt");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "dict.txt");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}

}
