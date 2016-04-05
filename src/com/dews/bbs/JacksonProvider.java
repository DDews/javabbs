package com.dews.bbs;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JacksonProvider implements ContextResolver<ObjectMapper> {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.setSerializationInclusion(Include.NON_EMPTY);
		MAPPER.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
	}
	
	public JacksonProvider() {
		System.out.println("Instantiate JacksonProvider");
	}
	@Override
	public ObjectMapper getContext(Class<?> type) {
		
		return MAPPER;
	}
}
