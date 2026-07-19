package com.officelunch.recommendation.domain;

import com.officelunch.common.error.BusinessException;
import com.officelunch.restaurant.domain.FoodCategory;
import com.officelunch.restaurant.domain.Restaurant;
import com.officelunch.restaurant.domain.RestaurantStatus;
import com.officelunch.restaurant.domain.WaitRisk;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class RecommendationSessionTest {

    @Test
    void 후보가_없으면_세션_생성_실패한다() {
        assertThrows(
            BusinessException.class,
            () -> new RecommendationSession(List.of())
        );
    }

    @Test
    void 후보가_null이면_세션_생성_실패한다() {
        assertThrows(
            BusinessException.class,
            () -> new RecommendationSession(null)
        );
    }

    @Test
    void 활성화된_식당만_추천한다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "폐업한 김치찌개집", RestaurantStatus.CLOSED),
            restaurant(2L, "비활성 돈까스집", RestaurantStatus.INACTIVE),
            restaurant(3L, "운영중 초밥집", RestaurantStatus.ACTIVE)
        ));

        Restaurant recommended = session.recommend();

        assertEquals(3L, recommended.getId());
        assertEquals("운영중 초밥집", recommended.getName());
    }

    @Test
    void 같은_식당을_중복_추천하지_않는다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "김치찌개집", RestaurantStatus.ACTIVE),
            restaurant(2L, "돈까스집", RestaurantStatus.ACTIVE)
        ));

        Restaurant first = session.recommend();
        Restaurant second = session.recommend();

        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    void 모든_후보를_추천하면_EXHAUSTED가_된다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "김치찌개집", RestaurantStatus.ACTIVE)
        ));

        session.recommend();

        assertThrows(BusinessException.class, session::recommend);
        assertEquals(RecommendationStatus.EXHAUSTED, session.getStatus());
    }

    @Test
    void 추천받지_않은_식당은_선택할_수_없다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "김치찌개집", RestaurantStatus.ACTIVE),
            restaurant(2L, "된장찌개집", RestaurantStatus.ACTIVE)
        ));
        session.recommend();

        assertThrows(
            BusinessException.class,
            () -> session.select(2L)
        );
    }

    @Test
    void select_성공시_상태가_SELECTED가_된다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "김치찌개집", RestaurantStatus.ACTIVE)
        ));
        Restaurant recommended = session.recommend();

        session.select(recommended.getId());

        assertEquals(RecommendationStatus.SELECTED, session.getStatus());
        assertEquals(1L, session.getSelectedRestaurantId());
    }

    @Test
    void 선택_완료된_세션은_추가_추천할_수_없다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "김치찌개집", RestaurantStatus.ACTIVE),
            restaurant(2L, "돈까스집", RestaurantStatus.ACTIVE)
        ));
        Restaurant recommended = session.recommend();

        session.select(recommended.getId());

        assertThrows(BusinessException.class, session::recommend);
    }

    @Test
    void 선택_완료된_세션은_다시_선택할_수_없다() {
        RecommendationSession session = new RecommendationSession(List.of(
            restaurant(1L, "김치찌개집", RestaurantStatus.ACTIVE)
        ));
        Restaurant recommended = session.recommend();
        session.select(recommended.getId());

        assertThrows(
            BusinessException.class,
            () -> session.select(recommended.getId())
        );
    }

    private Restaurant restaurant(Long id, String name, RestaurantStatus status) {
        return new Restaurant(
            id,
            name,
            FoodCategory.KOREAN,
            "서울시 강남구 테헤란로",
            37.5000,
            127.0300,
            5,
            10000,
            WaitRisk.LOW,
            status,
            "test:" + id
        );
    }
}
