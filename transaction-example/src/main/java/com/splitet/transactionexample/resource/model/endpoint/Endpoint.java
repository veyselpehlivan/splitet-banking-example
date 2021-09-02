package com.splitet.transactionexample.resource.model.endpoint;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.util.Assert;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = AccountEndpoint.class, name = "account"),
		@JsonSubTypes.Type(value = EmailEndpoint.class, name = "email"),
})
/**
 * Interface that keeps Jackson sub type information and endpoint types.
 */
public interface Endpoint {

	Type getType();

	String getIdentifier();

	enum Type {
		ACCOUNT,
		EMAIL,
	}

	static Endpoint newInstance(Type type, String identifier) {
		Endpoint endpoint = null;
		switch (type) {
		case ACCOUNT:
			endpoint = new AccountEndpoint(identifier);
			break;
		case EMAIL:
			endpoint = new EmailEndpoint(identifier);
			break;
		default:
			break;
		}
		Assert.notNull(endpoint, String.format("Unknown endpoint type: %s", type));
		return endpoint;
	}
}

