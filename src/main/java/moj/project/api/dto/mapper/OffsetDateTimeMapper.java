package moj.project.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface OffsetDateTimeMapper {
    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter LOCAL_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Named("mapOffsetDateTimeToString")
    default String mapOffsetDateTimeToString(OffsetDateTime offsetDateTime){
        return Optional.ofNullable(offsetDateTime)
                .map(odt-> offsetDateTime.atZoneSameInstant(ZoneOffset.UTC))
                .map(odt-> odt.format(DATE_FORMAT))
                .orElse(null);
    }
    @Named("mapStringToOffsetDateTime")
    default OffsetDateTime mapStringToOffsetDateTime(String date, String time){
        LocalDate localDate = Optional.ofNullable(date)
                .map(str -> LocalDate.parse(str, LOCAL_DATE_FORMAT))
                .orElse(null);
        LocalTime localTime = Optional.ofNullable(time)
                .map(str -> LocalTime.parse(str, LOCAL_TIME_FORMAT))
                .orElse(null);
        return OffsetDateTime.of(localDate, localTime, ZoneOffset.UTC);
    }
    }
