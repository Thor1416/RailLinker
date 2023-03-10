package com.railweb.shared.infra.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RawJsonDeserializer extends JsonDeserializer<String>{

	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException{
		ObjectMapper mapper = (ObjectMapper) p.getCodec();
		JsonNode node = mapper.readTree(p);
		return mapper.writeValueAsString(node);
	}
}
