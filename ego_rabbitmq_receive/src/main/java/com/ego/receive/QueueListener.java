package com.ego.receive;

import com.ego.commons.pojo.BigAd;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.utils.HttpClientUtil;
import com.ego.utils.JsonUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QueueListener {
    @Reference
    private TbContentDubboService tbContentDubboService;
    @Value("${ego.bigad.categoryId}")
    private Long bigadId;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    // @RabbitListener(queues = "content")
    @RabbitListener(bindings = {@QueueBinding(value=@Queue(name="content"),exchange = @Exchange(name="amq.direct"))})
    public void content(Object obj){
        System.out.println("接收到消息"+obj);

        // 把数据缓存到redis中
        List<TbContent> list = tbContentDubboService.selectAllByCategoryid(bigadId);
        List<BigAd> listBigad = new ArrayList<>();
        for(TbContent tbContent :list){
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);
            listBigad.add(bigAd);
        }
        redisTemplate.opsForValue().set("com.ego.portal:bigad", JsonUtils.objectToJson(listBigad));
        //第二种方式
        //直接条用ego_portal
     //   HttpClientUtil.doGet("http://localhost:8082/bigad");




    }
}
