package com.znsd.oneself.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class BrowserOpenRunner implements org.springframework.boot.CommandLineRunner {

	@Autowired
	private Environment env;


	@Override
	public void run(String... args) throws Exception {
		showLog();
	}

	private void showLog() {
		String host = "";
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.error(e.getMessage(), e);
		}
		String port = env.getProperty("server.port");
		log.info("\n----------------------------启动信息---------------------------------------\n\t"
				+ "Application '{}' is running! Access URLs:\n\t" 
				+ "Local: \t\thttp://localhost:{}\n\t"
				+ "External: \thttp://{}:{}\n\t" 
				+ "Swagger: \thttp://{}:{}/swagger-ui/index.html\n\t"
				+ "Doc: \t\thttp://{}:{}/doc.html\n"
				+ "-----------------------------release list---------------------------------------\n\t"
				
				,env.getProperty("spring.application.name"), env.getProperty("server.port"), host, port, host, port,
				host, port);
		openUrl(String.format("http://%s:%s/doc.html",host,port));
	}

	private void openUrl(String url) {
			String osName = System.getProperty("os.name");
			String cmd;
			if (osName.startsWith("Mac OS")) {
				// Mac
				cmd = "open ";
			} else if (osName.startsWith("Windows")) {
				// Windows
				cmd = "explorer ";
			} else{
				return;
			}

			try {
				Runtime.getRuntime().exec(new String[]{cmd,url});
				log.debug("启动浏览器打开项目成功！" + url);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
	}
}
