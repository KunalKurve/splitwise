package com.example.splitwise.strategies;

import com.example.splitwise.dtos.requests.SplitRequestDto;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {

    Map<Integer, Double> calculateSplits(Double totalAmount, List<SplitRequestDto> splitRequestDtos);
}
