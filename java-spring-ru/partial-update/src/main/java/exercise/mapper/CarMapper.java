package exercise.mapper;

import org.mapstruct.*;

import exercise.dto.CarCreateDTO;
import exercise.dto.CarUpdateDTO;
import exercise.dto.CarDTO;
import exercise.model.Car;

// BEGIN
@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "Spring")
public interface CarMapper {

    CarDTO map(Car car);

    Car map(CarCreateDTO createDTO);

    @InheritConfiguration
    void update(CarUpdateDTO carUpdateDTO, @MappingTarget Car car);

}
// END
