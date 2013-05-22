package com.akqa.kiev.conferer.server.dao.jsonmock.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"speakers", "startTime", "endTime"})
public abstract class SessionMixIn extends BasicMixin {

}
