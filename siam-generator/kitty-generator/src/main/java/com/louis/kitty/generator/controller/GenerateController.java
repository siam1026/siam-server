package com.louis.kitty.generator.controller;

import com.louis.kitty.core.http.HttpResult;
import com.louis.kitty.dbms.vo.ConnParam;
import com.louis.kitty.generator.service.GenerateService;
import com.louis.kitty.generator.utils.FileUtils;
import com.louis.kitty.generator.vo.GenerateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码生成控制器
 * @author Louis
 * @date Nov 9, 2018
 */
@RestController
@RequestMapping("")
public class GenerateController {

	@Autowired
    GenerateService generatorService;
	
	@PostMapping("/testConnection")
	public HttpResult testConnection(@RequestBody ConnParam connParam) {
		boolean success = generatorService.testConnection(connParam);
		if(success) {
			return HttpResult.ok(generatorService.testConnection(connParam));
		}
		return HttpResult.error("连接失败,请检查数据库及连接。");
	}

	@PostMapping("/getTables")
	public HttpResult getTables(@RequestBody ConnParam connParam) {
		return HttpResult.ok(generatorService.getTables(connParam));
	}
	
	@PostMapping("/getGenerateModel")
	public HttpResult getGenerateModel(@RequestBody GenerateModel generateModel) {
		return HttpResult.ok(generatorService.getGenerateModel(generateModel));
	}
	
	@PostMapping("/generateModels")
	public HttpResult generateModels(@RequestBody GenerateModel generateModel) throws Exception {
		generateModel.setOutPutFolderPath("/home/genCode");
		return HttpResult.ok(generatorService.generateModels(generateModel));
	}

	@RequestMapping("/downLoad")
	public String downLoad(HttpServletResponse response) {
		response.setContentType("application/force-download");// 设置强制下载不打开            
		response.addHeader("Content-Disposition", "attachment;fileName=" + "gen.zip");
		try {
			FileUtils.download(response.getOutputStream(),"/home/gen.zip");
		} catch (IOException e) {
			HttpResult.error("还没有生成代码，请生成代码在下载");
			e.printStackTrace();
		}
      return null;
	}

}
