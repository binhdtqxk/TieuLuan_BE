package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.ChartDataDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChartDataDtoMapper {
    public static ChartDataDto createTimeSeriesChart(List<String> labels, String seriesName, List<Number> data) {
        ChartDataDto chart = new ChartDataDto();
        chart.setLabels(labels);

        ChartDataDto.DataSeriesDto series = new ChartDataDto.DataSeriesDto();
        series.setName(seriesName);
        series.setData(data);

        chart.setSeries(Collections.singletonList(series));

        return chart;
    }

    public static ChartDataDto createMultiSeriesChart(List<String> labels, List<String> seriesNames, List<List<Number>> seriesData) {
        ChartDataDto chart = new ChartDataDto();
        chart.setLabels(labels);

        List<ChartDataDto.DataSeriesDto> seriesList = new ArrayList<>();
        for (int i = 0; i < seriesNames.size(); i++) {
            ChartDataDto.DataSeriesDto series = new ChartDataDto.DataSeriesDto();
            series.setName(seriesNames.get(i));
            series.setData(seriesData.get(i));
            seriesList.add(series);
        }

        chart.setSeries(seriesList);

        return chart;
    }

    public static List<String> generateWeeklyLabels() {
        List<String> labels = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            labels.add("Week " + i);
        }
        return labels;
    }
}
