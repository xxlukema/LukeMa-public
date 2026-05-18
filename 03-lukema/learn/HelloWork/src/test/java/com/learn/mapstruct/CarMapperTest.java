package com.learn.mapstruct;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class CarMapperTest {

    @Test
    public void testMapStruct() {
        log.debug(() -> "Start");

        //given
        Car car = new Car("Morris", 5, CarType.SEDAN);

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        //then
        Assertions.assertNotNull(carDto);
        Assertions.assertEquals(car.getMake(), carDto.getMake());
        Assertions.assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        Assertions.assertEquals(car.getType().toString(), carDto.getType());

        log.debug(() -> "End");
    }
}
