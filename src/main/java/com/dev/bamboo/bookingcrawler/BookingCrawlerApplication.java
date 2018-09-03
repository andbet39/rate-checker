package com.dev.bamboo.bookingcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class BookingCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingCrawlerApplication.class, args);
	}

}
