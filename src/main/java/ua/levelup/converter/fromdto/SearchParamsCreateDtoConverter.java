package ua.levelup.converter.fromdto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.SearchParams;
import ua.levelup.web.dto.create.SearchParamsCreateDto;

/**
 *
 */
@Component("searchParamsCreateDtoConverter")
public class SearchParamsCreateDtoConverter
        implements Converter<SearchParamsCreateDto, SearchParams> {

    @Override
    public SearchParams convert(@NonNull SearchParamsCreateDto searchParamsCreateDto) {
        SearchParams searchParams = new SearchParams();
        int categoryId = searchParamsCreateDto.getCategoryId();
        int subcategoryId = searchParamsCreateDto.getSubcategoryId();
        int actualSearchedCategoryId = (subcategoryId > 0) ? subcategoryId : categoryId;
        searchParams.setCategoryId(actualSearchedCategoryId);
        searchParams.setManufacturerId(searchParamsCreateDto.getManufacturerId());
        searchParams.setAvailableOnly(searchParamsCreateDto.isAvailableOnly());
        searchParams.setMinPrice(searchParamsCreateDto.getMinPrice());
        searchParams.setMaxPrice(searchParamsCreateDto.getMaxPrice());
        searchParams.setOrderMethod(SearchParams.OrderMethod
                .get(searchParamsCreateDto.getOrderMethodIndex()));
        return searchParams;
    }
}
