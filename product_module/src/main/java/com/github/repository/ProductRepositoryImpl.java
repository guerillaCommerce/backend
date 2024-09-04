package com.github.repository;

import com.github.domain.Product;
import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;
import com.github.repository.projection.GetProductProjectionImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Product> getProductDetailWithReviewListByProductId(
            final long productId
    ) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT p " +
                        "FROM Product p LEFT JOIN FETCH p.reviews " +
                        "WHERE p.id = :productId"
        );
        TypedQuery<Product> query = entityManager.createQuery(queryBuilder.toString(), Product.class);
        query.setParameter("productId", productId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<GetProductProjectionImpl> getProductList(
            String searchToken, ProductCategory productCategory,
            AgeCategory ageCategory, GenderCategory genderCategory,
            String sortBy, Pageable pageable) {

        StringBuilder queryBuilder = new StringBuilder(
                "SELECT new com.github.repository.projection.GetProductProjectionImpl( " +
                        "p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
                        "p.name AS name, p.content AS content, p.price AS price, " +
                        "p.leftAmount AS leftAmount, p.productCategory AS productCategory, " +
                        "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, " +
                        "p.createdAt AS createdAt, p.updatedAt AS updatedAt, " +
                        "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, " +
                        "p.options AS options, p.averageStarPoint AS averageStarPoint, " +
                        "p.isDeleted AS isDeleted, s.shopImageUrl AS shopImageUrl) " +
                        "FROM Product p JOIN p.seller s WHERE p.isDeleted = false ");

        // 조건 추가
        if (searchToken != null && !searchToken.isEmpty()) {
            queryBuilder.append("AND p.name LIKE :searchToken ");
        }
        if (productCategory != null) {
            queryBuilder.append("AND p.productCategory = :productCategory ");
        }
        if (ageCategory != null) {
            queryBuilder.append("AND p.ageCategory = :ageCategory ");
        }
        if (genderCategory != null) {
            queryBuilder.append("AND p.genderCategory = :genderCategory ");
        }

        // 정렬 옵션
        switch (sortBy) {
            case "price":
                queryBuilder.append("ORDER BY p.price ASC ");
                break;
            case "createdAt":
                queryBuilder.append("ORDER BY p.createdAt DESC ");
                break;
            default:
                queryBuilder.append("ORDER BY p.id ASC ");
                break;
        }

        TypedQuery<GetProductProjectionImpl> query = entityManager.createQuery(queryBuilder.toString(), GetProductProjectionImpl.class);

        // 파라미터 설정
        if (searchToken != null && !searchToken.isEmpty()) {
            query.setParameter("searchToken", "%" + searchToken + "%");
        }
        if (productCategory != null) {
            query.setParameter("productCategory", productCategory);
        }
        if (ageCategory != null) {
            query.setParameter("ageCategory", ageCategory);
        }
        if (genderCategory != null) {
            query.setParameter("genderCategory", genderCategory);
        }

        // 페이지네이션 설정
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }


}
