package com.lgb.ribbonconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MySelfRule
{
	@Bean
	public IRule myRule()
	{
		return new RandomRule();  //随机
		//return new RoundRobinRule() ;//轮询
	}
}
