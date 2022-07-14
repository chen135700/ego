package com.ego.service.impl;

import com.ego.service.PicService;
import com.ego.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PikServiceImpl implements PicService {
    //
    @Value("${ego.fastdfs.nginx}")
    private  String ngixnxHost;
    @Override
    public Map<String, Object> update(MultipartFile file) {
        Map<String,Object> map = new HashMap<>();
        try {
            String[] result = FastDFSClient.uploadFile(file.getInputStream(), file.getOriginalFilename());
            System.out.println(result[0]+"----"+result[1]);
            map.put("error",0);
            map.put("url",ngixnxHost+result[0]+"/"+result[1]);
            return  map;
        }catch (IOException e){
            e.printStackTrace();
        }
        map.put("error",1);
        map.put("message","错误信息");
        return map;
    }
}
