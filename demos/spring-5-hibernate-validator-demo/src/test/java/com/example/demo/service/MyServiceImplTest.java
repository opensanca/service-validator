package com.example.demo.service;

import com.example.demo.AppConfig;
import com.example.demo.dto.DTO;
import io.github.opensanca.exception.ServiceValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author s2it_agomes
 * @since 25/03/18 16:49
 * @version $Revision: $<br/>
 *          $Id: $
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= AppConfig.class)
public class MyServiceImplTest {

    @Autowired
    private MyService myService;
    
    @Test(expected = ServiceValidationException.class)
    public void shouldValidateWhenAttributeIsNull(){

        myService.getDto(null);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldValidateWhenModelIsInvalid(){

        myService.getDto(new DTO());
    }

    @Test
    public void everythingFine(){

        final DTO dto = new DTO();
        dto.setNumber(1);
        dto.setText("test");

        final DTO result = myService.getDto(dto);
        Assert.assertEquals(result.getNumber(), dto.getNumber());
        Assert.assertEquals(result.getText(), dto.getText());
    }
}
