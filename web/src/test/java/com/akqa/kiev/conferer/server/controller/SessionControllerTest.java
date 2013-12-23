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
import com.akqa.kiev.conferer.server.dao.SessionDao;
import com.akqa.kiev.conferer.server.model.Action;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController = new SessionController();

    @Mock
    private SessionDao sessionDao;

    @Mock
    private ActionDao actionDao;

    @Test
    public void deleteExistingConference() throws Exception {
        BigInteger id = BigInteger.valueOf(1L);
        when(sessionDao.exists(id)).thenReturn(true);
        
        sessionController.delete(id);
        verify(sessionDao).delete(id);
        verify(actionDao).save(any(Action.class));
    }

    @Test
    public void deleteNotExistingConference() throws Exception {
        BigInteger id = BigInteger.valueOf(1L);
        when(sessionDao.exists(id)).thenReturn(false);
        
        sessionController.delete(id);
        verify(sessionDao, never()).delete(id);
        verify(actionDao, never()).save(any(Action.class));
    }
}
