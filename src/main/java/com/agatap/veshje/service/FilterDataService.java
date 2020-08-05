package com.agatap.veshje.service;

import com.agatap.veshje.model.FilterData;
import com.agatap.veshje.model.SizeType;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FilterDataService {

    private FilterData filterData = new FilterData();

    public FilterData getFilterData() {
        return filterData;
    }

    public FilterData addFilterToList(FilterData filterData) {
        FilterData data = getFilterData();
        data.setName(filterData.getName());
        data.setSearch(filterData.getSearch());
        data.setMinPrice(filterData.getMinPrice());
        data.setMaxPrice(filterData.getMaxPrice());
        data.setSort(filterData.getSort());

        if(filterData.getColor() != null) {
            List<String> colorList = new ArrayList<>();
            for(String color : filterData.getColor()) {
                colorList.add(color);
            }
            data.setColor(colorList);
        }

        if(filterData.getSizeType() != null) {
            List<SizeType> sizeList = new ArrayList<>();
            for(SizeType size : filterData.getSizeType()) {
                sizeList.add(size);
            }
            data.setSizeType(sizeList);
        }

        return data;
    }

    public FilterData clearFilter() {
        FilterData data = getFilterData();
        data.setName(null);
        data.setSearch(null);
        data.setMinPrice(null);
        data.setMaxPrice(null);
        data.setSort(null);
        data.setColor(null);
        data.setSizeType(null);
        return filterData;
    }
}
