package com.rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rocks.zipcode.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodMenuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodMenu.class);
        FoodMenu foodMenu1 = new FoodMenu();
        foodMenu1.setId(1L);
        FoodMenu foodMenu2 = new FoodMenu();
        foodMenu2.setId(foodMenu1.getId());
        assertThat(foodMenu1).isEqualTo(foodMenu2);
        foodMenu2.setId(2L);
        assertThat(foodMenu1).isNotEqualTo(foodMenu2);
        foodMenu1.setId(null);
        assertThat(foodMenu1).isNotEqualTo(foodMenu2);
    }
}
