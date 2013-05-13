
package com.akqa.kiev.conferer.server.dao;


import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class AbstractMongoDao<T> {

    private final MongoTemplate template;

    protected AbstractMongoDao(MongoTemplate template) {
        this.template = template;
    }

    @SuppressWarnings("unchecked")
    protected Class<T> getGenericClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected List<T> find(Query query, String collectionName) {

        return template.find(query, getGenericClass(), collectionName);
    }
}
