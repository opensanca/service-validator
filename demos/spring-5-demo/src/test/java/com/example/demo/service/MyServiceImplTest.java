package com.example.demo.service;

import com.example.demo.AppConfig;
import io.github.opensanca.exception.ServiceValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author s2it_agomes
 * @since 25/03/18 16:49
 * @version $Revision: $<br/>
 *          $Id: $
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class MyServiceImplTest {

    @Autowired
    private MyService myService;
    
    @Test(expected = ServiceValidationException.class)
    public void test(){

        myService.getDto(null);
    }
}
