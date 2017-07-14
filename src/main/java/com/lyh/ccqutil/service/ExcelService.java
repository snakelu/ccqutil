package com.lyh.ccqutil.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lyh.ccqutil.mapper.CreateTableMapper;
import com.lyh.ccqutil.model.Response;
import com.lyh.ccqutil.model.utils.ErrorCode;
import com.lyh.ccqutil.utils.ExcelUtil;
import com.lyh.ccqutil.utils.MD5Util;

@Service
public class ExcelService {

	private static final String EXTENSION_EXCEL = ".xls|.xlsx|.xlsm|.csv";

	@Autowired
	private CreateTableMapper createTableMapper;

	public Response<Boolean> upload(List<MultipartFile> mpfs) throws IOException {
		for (MultipartFile multipartFile : mpfs) {
			String originalFileExtension = getFileExtension(multipartFile.getOriginalFilename());
			if (StringUtils.isEmpty(originalFileExtension) || !isExtensionMatch(originalFileExtension)) {
				return new Response<Boolean>(ErrorCode.FILE_FOMATTER_ERROR);
			}
			String md5Name = MD5Util.GetMD5Code(multipartFile.getBytes());
			String tableNamePrefix = null;
			if (md5Name.length() > 6) {
				tableNamePrefix = md5Name.substring(0, 6);
			} else {
				tableNamePrefix = md5Name;
			}
			Map<String, List<String[]>> result = ExcelUtil.readExcel(multipartFile);
			for (Entry<String, List<String[]>> entry : result.entrySet()) {
				List<String[]> originValues = entry.getValue();
				if (!CollectionUtils.isEmpty(originValues)) {
					// 建表
					Map<String, Object> createMap = new HashMap<String, Object>();
					String[] keys = new String[originValues.get(0).length + 1];
					keys[0] = "id";
					for (int i = 1; i < keys.length; i++) {
						keys[i] = "row" + i;
					}
					createMap.put("tableName", tableNamePrefix + entry.getKey());
					createMap.put("keys", keys);
					createTableMapper.createTable(createMap);
					// 插入数据
					Map<String, Object> insertMap = new HashMap<String, Object>();
					insertMap.put("keys", keys);
					List<String> values = new ArrayList<String>();
					for (String[] originValue : originValues) {
						StringBuffer value = new StringBuffer();
						for (int i = 0; i < originValue.length; i++) {
							value.append(originValue[i]);
							value.append(",");
						}
						values.add(value.substring(0, value.length() - 1));
					}
					insertMap.put("values", values);
					createTableMapper.insertData(insertMap);
				}
				continue;
			}
		}
		return new Response<Boolean>(true);
	}

	private boolean isExtensionMatch(String originalFileExtension) {
		return originalFileExtension.matches(EXTENSION_EXCEL);
	}

	private String getFileExtension(String originalFilename) {
		if (originalFilename.lastIndexOf(".") == -1) {
			return null;
		}
		return originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
	}
}
