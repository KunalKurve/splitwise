package com.example.splitwise.strategies;

import com.example.splitwise.dtos.requests.SplitRequestDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EqualSplitStrategy implements SplitStrategy{


    @Override
    public Map<Integer, Double> calculateSplits(
            Double totalAmount,
            List<SplitRequestDto> splitRequestDtos
    ) {

        Map<Integer, Double> result = new HashMap<>();

        int totalUsers = splitRequestDtos.size();

        double splitAmount = totalAmount / totalUsers;

        for(SplitRequestDto dto : splitRequestDtos) {
            result.put(dto.getUserId(), splitAmount);
        }

        return result;
    }
}
