package com.lyh.ccqutil.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.lyh.ccqutil.model.Response;
import com.lyh.ccqutil.service.ExcelService;

@RestController
@RequestMapping("/excel")
public class ExcelController {

	@Autowired
	private ExcelService excelService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> upload(MultipartHttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Iterator<String> itr = request.getFileNames();
		List<MultipartFile> mpfs = new ArrayList<MultipartFile>();
		while (itr.hasNext()) {
			mpfs.add(request.getFile(itr.next()));
		}
		return excelService.upload(mpfs);
	}

}
