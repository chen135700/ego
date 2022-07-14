package com.ego.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PicService {
    Map<String,Object> update(MultipartFile file);
}
