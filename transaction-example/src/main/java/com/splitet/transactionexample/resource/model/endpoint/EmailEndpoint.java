package com.splitet.transactionexample.resource.model.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@Builder
public class EmailEndpoint implements Endpoint {

	@JsonIgnore
	private final Type type = Type.EMAIL;

//	@JsonIgnore
	private final String identifier;

	@ConstructorProperties({"identifier"})
	public EmailEndpoint(String identifier) {
		this.identifier = identifier;
	}
}
