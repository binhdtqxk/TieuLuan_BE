package com.essay.TieuLuan_BE.entity.analytics;

import lombok.Data;

import java.util.List;

@Data
public class ChartData {
    private List<String> labels;
    private List<DataSeries> series;

    @Data
    public static class DataSeries {
        private String name;
        private List<Number> data;
    }
}