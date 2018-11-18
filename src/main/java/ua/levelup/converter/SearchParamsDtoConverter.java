package ua.levelup.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.SearchParams;
import ua.levelup.web.dto.SearchParamsDto;

/**
 *
 */
@Component("searchParamsDtoConverter")
public class SearchParamsDtoConverter
        implements Converter<SearchParamsDto, SearchParams> {

    @Override
    public SearchParams convert(@NonNull SearchParamsDto searchParamsDto) {
        SearchParams searchParams = new SearchParams();
        int categoryId = searchParamsDto.getCategoryId();
        int subcategoryId = searchParamsDto.getSubcategoryId();
        int actualSearchedCategoryId = (subcategoryId > 0) ? subcategoryId : categoryId;
        searchParams.setCategoryId(actualSearchedCategoryId);
        searchParams.setManufacturerId(searchParamsDto.getManufacturerId());
        searchParams.setAvailableOnly(searchParamsDto.isAvailableOnly());
        searchParams.setMinPrice(searchParamsDto.getMinPrice());
        searchParams.setMaxPrice(searchParamsDto.getMaxPrice());
        searchParams.setOrderMethod(SearchParams.OrderMethod
                .get(searchParamsDto.getOrderMethodIndex()));
        return searchParams;
    }
}
