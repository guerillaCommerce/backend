package com.github.dto;

import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;
import com.github.domain.type.SortType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ModelAttribute에 의해 기본 생성자로 생성 이후 setter로 필드 값 주입
 * enum을 사용하는 필드는 setter 개별 구현
 */
@Getter
@Setter
@NoArgsConstructor
public class GetProductInputDto {
    private int size = 15;
    private int page = 0;
    private ProductCategory productCategory = ProductCategory.ALL;
    private AgeCategory ageCategory = AgeCategory.ALL;
    private GenderCategory genderCategory = GenderCategory.ALL;
    private SortType sort = SortType.ID;
    private String search = "";


    public void setProductCategory(String productCategory) {
        this.productCategory = ProductCategory.fromQueryString(productCategory);
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = AgeCategory.fromQueryString(ageCategory);
    }

    public void setGenderCategory(String genderCategory) {
        this.genderCategory = GenderCategory.fromQueryString(genderCategory);
    }

    public void setSort(String sort) {
        this.sort = SortType.fromQueryString(sort);
    }


}