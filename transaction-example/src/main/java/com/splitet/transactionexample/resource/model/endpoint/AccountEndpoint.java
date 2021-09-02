package com.splitet.transactionexample.resource.model.endpoint;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
@Builder
public class AccountEndpoint implements Endpoint {

	@JsonIgnore
	private final Type type = Type.ACCOUNT;

//	@JsonIgnore
	private final String identifier;

	@ConstructorProperties({"identifier"})
	public AccountEndpoint(String identifier) {
		this.identifier = identifier;
	}
}
