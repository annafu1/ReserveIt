package com.rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodMenuMapperTest {

    private FoodMenuMapper foodMenuMapper;

    @BeforeEach
    public void setUp() {
        foodMenuMapper = new FoodMenuMapperImpl();
    }
}
