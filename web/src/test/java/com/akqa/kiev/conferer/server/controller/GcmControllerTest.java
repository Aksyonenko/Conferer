package com.akqa.kiev.conferer.server.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.akqa.kiev.conferer.server.dao.GcmRegistrationDao;
import com.akqa.kiev.conferer.server.gcm.GcmSender;
import com.akqa.kiev.conferer.server.model.GcmRegistration;

@RunWith(MockitoJUnitRunner.class)
public class GcmControllerTest {

    @InjectMocks
    private GcmController gcmController = new GcmController();
    
    @Mock
    private GcmRegistrationDao gcmRegistrationDao;

    @Mock
    private GcmSender gcmSender;
    
    private GcmRegistration expectedGcmRegistration;
    
    @Before
    public void before(){
        expectedGcmRegistration = new GcmRegistration();
        expectedGcmRegistration.setRegistrationId("ABC");
        expectedGcmRegistration.setCreated(new Date());
    }
    
    @Test
    public void testRegistraion(){
        when(gcmRegistrationDao.save(any(GcmRegistration.class))).thenReturn(expectedGcmRegistration);
        GcmRegistration actual = gcmController.register("ABC");
        
        verify(gcmRegistrationDao).save(any(GcmRegistration.class));
        assertEquals(expectedGcmRegistration.getRegistrationId(), actual.getRegistrationId());
    }
    
    @Test
    public void testUnRegistraionWhenGcmRegistrationExists(){        
        String id = "ABC";
        when(gcmRegistrationDao.exists(anyString())).thenReturn(true);
        
        gcmController.unregister(id);
        verify(gcmRegistrationDao).exists(id);
        verify(gcmRegistrationDao).delete(id);

    }
    
    @Test
    public void testUnRegistraionWherGcmRegistrationNotExists(){        
        String id = "ABC";
        when(gcmRegistrationDao.exists(anyString())).thenReturn(false);
        
        gcmController.unregister(id);
        verify(gcmRegistrationDao).exists(id);
        verify(gcmRegistrationDao, never()).delete(id);
    }
    
    @Test
    public void testSendNotification() throws IOException{             
        gcmController.sendNotification();
        verify(gcmSender).sendNotification();
    }
}
