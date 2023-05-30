package com.avadamedia.USAINUA_Admin.services.impl;

import com.avadamedia.USAINUA_Admin.entity.Stats;
import com.avadamedia.USAINUA_Admin.repositories.StatsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatsServiceImplTest {
    @InjectMocks
    private StatsServiceImpl statsService;
    @Mock
    private StatsRepository statsRepository;
    @Test
    void getAllMonth() {
        List<Stats> statsList = new ArrayList<>();
        statsList.add(new Stats(1L,"January", 100L));
        statsList.add(new Stats(2L, "February", 200L));
        statsList.add(new Stats(3L, "March", 300L));

        when(statsRepository.findAll()).thenReturn(statsList);

        List<String> result = statsService.getAllMonth();
        List<String> expected = List.of("January", "February", "March");

        assertEquals(expected, result);
    }
    @Test
    void getAllValue() {
        List<Stats> statsList = new ArrayList<>();
        statsList.add(new Stats(1L,"January", 100L));
        statsList.add(new Stats(2L, "February", 200L));
        statsList.add(new Stats(3L, "March", 300L));

        when(statsRepository.findAll()).thenReturn(statsList);

        List<Long> result = statsService.getAllValue();
        List<Long> expected = List.of(100L, 200L, 300L);

        assertEquals(expected, result);
    }
}