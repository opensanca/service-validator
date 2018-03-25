package com.example.demo.service;

import com.example.demo.dto.DTO;
import io.github.opensanca.annotation.ServiceValidation;

/**
 * @author s2it_agomes
 * @version $Revision: $<br/>
 * $Id: $
 * @since 25/03/18 16:22
 */
public class MyServiceImpl implements MyService {

    @Override
    @ServiceValidation
    public DTO getDto (final DTO dto){
        return dto;
    }

}
