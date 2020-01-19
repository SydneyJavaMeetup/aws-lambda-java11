package com.mycodefu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonSerialization {
	public static final ObjectMapper objectMapper = new ObjectMapper();
	public static final ObjectWriter prettyObjectWriter = objectMapper.writerWithDefaultPrettyPrinter();
}
