package com.rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.rocks.zipcode.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodMenuDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodMenuDTO.class);
        FoodMenuDTO foodMenuDTO1 = new FoodMenuDTO();
        foodMenuDTO1.setId(1L);
        FoodMenuDTO foodMenuDTO2 = new FoodMenuDTO();
        assertThat(foodMenuDTO1).isNotEqualTo(foodMenuDTO2);
        foodMenuDTO2.setId(foodMenuDTO1.getId());
        assertThat(foodMenuDTO1).isEqualTo(foodMenuDTO2);
        foodMenuDTO2.setId(2L);
        assertThat(foodMenuDTO1).isNotEqualTo(foodMenuDTO2);
        foodMenuDTO1.setId(null);
        assertThat(foodMenuDTO1).isNotEqualTo(foodMenuDTO2);
    }
}
