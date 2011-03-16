package com.resanet.camel_su.routes;

import java.util.logging.Logger;

import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.ErrorHandler;
import org.apache.camel.spring.spi.TransactionErrorHandler;

public class Routes extends RouteBuilder {

	private static final Logger logger = Logger.getLogger(Routes.class
			.getCanonicalName());

	@Override
	public void configure() throws Exception {
		from("amqTX:in").transacted().bean(new Routes()).to("amqTX:out");
	}

	public void process(@Body String body) throws Exception {
		logger.info("Simulate a long treatement...");
		Thread.sleep(10000);

		if (body.equals("exception")) {
			throw new Exception("rollback requested !");
		} else {
			logger.info("commit !");
		}
	}
}
