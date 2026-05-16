package com.example.splitwise.strategies;

import com.example.splitwise.dtos.requests.SplitRequestDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public Map<Integer, Double> calculateSplits(
            Double totalAmount,
            List<SplitRequestDto> splitRequestDtos
    ) {

        Map<Integer, Double> result = new HashMap<>();

        double sum = 0;

        for(SplitRequestDto dto : splitRequestDtos) {

            result.put(dto.getUserId(), dto.getAmount());

            sum += dto.getAmount();
        }

        if(sum != totalAmount) {
            throw new RuntimeException("Invalid exact split amounts");
        }

        return result;
    }
}