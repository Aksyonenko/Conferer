package com.akqa.kiev.conferer.server.dao.jsonsource.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class BasicMixin {

	@JsonProperty("_id")
	public abstract String getId();
}
