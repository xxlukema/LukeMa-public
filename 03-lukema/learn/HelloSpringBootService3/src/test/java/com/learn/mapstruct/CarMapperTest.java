package com.learn.mapstruct;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import lombok.extern.log4j.Log4j2;


@Log4j2
class CarMapperTest {

    @Test
    void testMapStruct() {
        log.debug(() -> "Start");

        List<String> strList = List.of("One", "Two", "3");

        List<MyPojo> myPojoList = List.of(
                new MyPojo("Luke", 1),
                new MyPojo("Hong", 2),
                new MyPojo("TT", 3),
                new MyPojo("HH", 4));

        MyPojo[] myPojoArray = {
                new MyPojo("Mike", 5),
                new MyPojo("Tom", 6)
        };

        //given
        Car car = new Car("Morris", 5, CarType.SEDAN, CarEnum.CarEnumForCarOnly, strList,
                myPojoList, myPojoArray, "5500.23",
                "Luke Ma",
                "2024-06-26T14:12:36.123CST");

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);

        //then
        Assertions.assertNotNull(carDto);
        Assertions.assertEquals(car.getMake(), carDto.getMake());
        Assertions.assertEquals(car.getNumberOfSeats(), carDto.getSeatCount());
        Assertions.assertEquals(car.getType().toString(), carDto.getType());

        log.debug("car: {}", () -> car);
        log.debug("carDto: {}", () -> carDto);

        log.debug(() -> "End");
    }

    @ParameterizedTest
    @CsvSource({ "Off,Off_Value", "Go,Go_Value" })
    void whenTrafficSignalIsMappedWithSuffix_thenGetTrafficSignalSuffixed(String source, String target) {
        log.debug("source: {}, target: {}", source, target);
    }
}
