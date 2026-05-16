package com.example.splitwise.strategies;

import com.example.splitwise.dtos.requests.SplitRequestDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PercentageSplitStrategy implements SplitStrategy {

    @Override
    public Map<Integer, Double> calculateSplits(
            Double totalAmount,
            List<SplitRequestDto> splitRequestDtos
    ) {

        Map<Integer, Double> result = new HashMap<>();

        double totalPercent = 0;

        for(SplitRequestDto dto : splitRequestDtos) {

            totalPercent += dto.getPercentage();

            double splitAmount =
                    (dto.getPercentage() * totalAmount) / 100;

            result.put(dto.getUserId(), splitAmount);
        }

        if(totalPercent != 100) {
            throw new RuntimeException("Percent total must be 100");
        }

        return result;
    }
}
