package com.learn.mapstruct;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;


/**
 * https://www.baeldung.com/java-mapstruct-enum
 */
// @Mapper(componentModel = "cdi")
@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    /**
     * "2024-06-26T14:12:36.123CST"
     */
    static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSz";

    /**
     * We need to make sure to map all enum values from source to target for complete coverage and prevent unexpected behavior.
     */

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE) /** !important: This supresses `unmapped warnings` */
    @ValueMapping(source = "CarEnumOne", target = "CarDtoEnumOne")
    @ValueMapping(source = "CarEnumTwo", target = "CarDtoEnumOne")
    @ValueMapping(source = "CarEnumThree", target = "CarDtoEnumThree")
    // @ValueMapping(source = "CarEnumForCarOnly", target = "CarDtoEnumSpecialFprCarDto")
    // Or
    // @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "CarDtoEnumSpecialForCarDto")
    // Or
    @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = "CarDtoEnumSpecialForCarDto")
    // Or
    // @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = MappingConstants.NULL)
    // Or
    // @ValueMapping(source = MappingConstants.ANY_UNMAPPED, target = MappingConstants.THROW_EXCEPTION)
    // Or
    // @EnumMapping(nameTransformationStrategy = MappingConstants.STRIP_SUFFIX_TRANSFORMATION, configuration = "_Value")
    // @EnumMapping(nameTransformationStrategy = MappingConstants.PREFIX_TRANSFORMATION, configuration = "Value_")
    CarDtoEnum toCarDtoEnum(CarEnum source);

    @Mapping(source = "numberOfSeats", target = "seatCount")
    @Mapping(source = "carEnum", target = "carDtoEnum")
    /**
     * If source and target have the same name, no need for @Mapping(...)
     */
    // @Mapping(source = "carWeight", target = "carWeight")
    // @Mapping(source = "name", target = "name", qualifiedByName = "anyName")
    CarDto carToCarDto(Car car);

    /**
     * MapStruct will find the custom map by default function signature type. No need to specify `qualifiedByName`
     */
    default MyPojo anyName(String source) {
        return new MyPojo(source, 9);
    }

    /**
     * MapStruct will find the custom map by default function signature type. No need to specify `qualifiedByName`
     */
    default String map(GregorianCalendar date) {
        return date != null ? new SimpleDateFormat(DATE_FORMAT)
                .format(date.getTime()) : null;
    }

    /**
     * MapStruct will find the custom map by default function signature type. No need to specify `qualifiedByName`
     */
    default XMLGregorianCalendar toXMLGregorianCalendar(String date)
        throws ParseException {
        if (date == null) {
            return null;
        }

        try {
            Date date1 = new SimpleDateFormat(DATE_FORMAT).parse(date);
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date1);

            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (ParseException | DatatypeConfigurationException e) {
            System.out.println("====: " + e.getMessage());
        }
        return null;
    }

}
