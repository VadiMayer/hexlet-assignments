package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
@Mapper(
        uses = {JsonNullableMapper.class},
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ProductMapper {
    @Mapping(target = "category.id", source = "categoryId")
    public abstract Product map(ProductCreateDTO createDTO);
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    public abstract ProductDTO map(Product product);

    @InheritConfiguration
    public abstract void update(ProductUpdateDTO productUpdateDTO, @MappingTarget Product product);
}
// END
