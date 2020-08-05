package com.agatap.veshje.controller;

import com.agatap.veshje.model.FilterData;
import com.agatap.veshje.service.FilterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/filter-data")
public class FilterDataController {

    @Autowired
    private FilterDataService filterDataService;

    @GetMapping
    public FilterData getFilterData() {
        return filterDataService.getFilterData();
    }

    @PostMapping
    public FilterData addFilterToList(@RequestBody FilterData filterData) {
        return filterDataService.addFilterToList(filterData);
    }

    @DeleteMapping
    public FilterData clearFilter() {
        return filterDataService.clearFilter();
    }
}
