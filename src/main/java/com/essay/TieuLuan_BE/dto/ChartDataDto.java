package com.essay.TieuLuan_BE.dto;

import lombok.Data;

import java.util.List;
@Data
public class ChartDataDto {
    private List<String> labels;
    private List<DataSeriesDto> series;

    @Data
    public static class DataSeriesDto {
        private String name;
        private List<Number> data;
    }
}
