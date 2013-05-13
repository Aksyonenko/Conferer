
package com.akqa.kiev.conferer.server.dao;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.akqa.kiev.conferer.Speaker;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class})
public class MongoSpeakersDaoTest {

    @Autowired
    private MongoSpeakerDao speakerDao;

    @Test
    public void existingSpeakerTest() {
        Speaker speaker = speakerDao.getSpeakerById("SP_1");
        assertThat(speaker, notNullValue());
        assertThat(speaker.getFirstName(), is("Adam"));
    }

    @Test
    public void notExistingSpeakerTest() {
        Speaker speaker = speakerDao.getSpeakerById("not_exist");
        assertThat(speaker, nullValue());
    }
}
