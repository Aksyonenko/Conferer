
package com.akqa.kiev.conferer.server.dao;


import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.akqa.kiev.conferer.server.model.GcmRegistration;

public interface GcmRegistrationDao extends CrudRepository<GcmRegistration, String>,
        QueryDslPredicateExecutor<GcmRegistration> {

}
