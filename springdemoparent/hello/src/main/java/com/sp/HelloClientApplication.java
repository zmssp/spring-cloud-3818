package com.sp;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;
import java.security.GeneralSecurityException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
@RefreshScope
public class HelloClientApplication {
	@Autowired
	HelloClient client;

	@Value("${timeout:4000}")
	private String connectTimeout;

	@RequestMapping("/hello")
	public String hello() {
		return "hello client, connectTimeout: " + connectTimeout;
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloClientApplication.class);
	}

	@FeignClient("hello-server")
	interface HelloClient {
		@RequestMapping(value = "/hello", method = GET)
		String hello();
	}

	/**
	 * This custom define DiscoveryClient will trigger the bug
	 * @return
	 */
	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() {

		EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder()
			.withClientName("eureka-client")
			.withReadTimeout(30 * 1000)
			.withConnectionTimeout(30 * 1000)
			.withMaxTotalConnections(10)
			.withMaxConnectionsPerHost(10);

		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
		args.setEurekaJerseyClient(builder.build());
		return args;
	}
}
