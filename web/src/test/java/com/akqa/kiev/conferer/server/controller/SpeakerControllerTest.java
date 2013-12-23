package com.akqa.kiev.conferer.server.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.akqa.kiev.conferer.server.dao.ActionDao;
import com.akqa.kiev.conferer.server.dao.SpeakerDao;
import com.akqa.kiev.conferer.server.model.Action;

@RunWith(MockitoJUnitRunner.class)
public class SpeakerControllerTest {

    @InjectMocks
    private SpeakerController speakerController = new SpeakerController();

    @Mock
    private SpeakerDao speakerDao;

    @Mock
    private ActionDao actionDao;

    @Test
    public void deleteExistingConference() throws Exception {
        BigInteger id = BigInteger.valueOf(1L);
        when(speakerDao.exists(id)).thenReturn(true);
        
        speakerController.delete(id);
        verify(speakerDao).delete(id);
        verify(actionDao).save(any(Action.class));
    }

    @Test
    public void deleteNotExistingConference() throws Exception {
        BigInteger id = BigInteger.valueOf(1L);
        when(speakerDao.exists(id)).thenReturn(false);
        
        speakerController.delete(id);
        verify(speakerDao, never()).delete(id);
        verify(actionDao, never()).save(any(Action.class));
    }
}
