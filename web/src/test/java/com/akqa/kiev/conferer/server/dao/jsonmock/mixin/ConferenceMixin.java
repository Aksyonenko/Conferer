package com.akqa.kiev.conferer.server.dao.jsonmock.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"startDate", "endDate"})
public abstract class ConferenceMixin extends BasicMixin {

}
