package com.Tallerdecoches.services;

import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CodigoPostalServiceTest {

    @Mock
    private CodigoPostalRepository codigoPostalRespository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CodigoPostalServiceImpl codigoPostalService;
}
