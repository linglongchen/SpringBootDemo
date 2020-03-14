package com.chunqiu.mrjuly;

import com.chunqiu.mrjuly.common.utils.CacheRedisUtil;
import com.chunqiu.mrjuly.modules.system.service.LyConfigStarService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//解决引入websocket无法进行JUnit测试：为App生成嵌入式servlet容器启动并监听定义的端口，默认8080
@SpringBootTest(classes = SpringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringbootApplicationTests {

	@Autowired
	private LyConfigStarService lyConfigStarService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
    CacheRedisUtil redisUtil;

}
