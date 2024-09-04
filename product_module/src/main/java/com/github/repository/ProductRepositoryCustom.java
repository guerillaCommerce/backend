package com.github.repository;

import com.github.domain.Product;
import com.github.domain.type.AgeCategory;
import com.github.domain.type.GenderCategory;
import com.github.domain.type.ProductCategory;
import com.github.repository.projection.GetProductProjectionImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    List<GetProductProjectionImpl> getProductList(
            String searchToken, ProductCategory productCategory,
            AgeCategory ageCategory, GenderCategory genderCategory,
            String sortBy, Pageable pageable);

//    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
//            "s.shopImageUrl AS shopImageUrl, " +
//            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
//            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
//            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
//            "FROM Product p " +
//            "JOIN p.seller s " +
//            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
//            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
//            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
//            "AND p.isDeleted = false " +
//            "AND p.name LIKE :searchToken " +
//            "ORDER BY p.price ASC")
//    List<GetProductProjection> searchProductSortByPrice(
//            @Param("searchToken") String searchToken,
//            @Param("productCategory") ProductCategory productCategory,
//            @Param("ageCategory") AgeCategory ageCategory,
//            @Param("genderCategory") GenderCategory genderCategory,
//            Pageable pageable
//    );
//
//    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
//            "s.shopImageUrl AS shopImageUrl, " +
//            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
//            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
//            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
//            "FROM Product p " +
//            "JOIN p.seller s " +
//            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
//            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
//            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
//            "AND p.isDeleted = false " +
//            "AND p.name LIKE :searchToken " +
//            "ORDER BY p.createdAt DESC")
//    List<GetProductProjection> searchProductSortByCreatedAt(
//            @Param("searchToken") String searchToken,
//            @Param("productCategory") ProductCategory productCategory,
//            @Param("ageCategory") AgeCategory ageCategory,
//            @Param("genderCategory") GenderCategory genderCategory,
//            Pageable pageable
//    );
//
//    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
//            "s.shopImageUrl AS shopImageUrl, " +
//            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
//            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
//            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
//            "FROM Product p " +
//            "JOIN p.seller s " +
//            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
//            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
//            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
//            "AND p.isDeleted = false " +
//            "AND p.name LIKE :searchToken " +
//            "ORDER BY p.id ASC")
//    List<GetProductProjection> searchProductSortById(
//            @Param("searchToken") String searchToken,
//            @Param("productCategory") ProductCategory productCategory,
//            @Param("ageCategory") AgeCategory ageCategory,
//            @Param("genderCategory") GenderCategory genderCategory,
//            Pageable pageable
//    );
//
//    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
//            "s.shopImageUrl AS shopImageUrl, " +
//            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
//            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
//            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
//            "FROM Product p " +
//            "JOIN p.seller s " +
//            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
//            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
//            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
//            "AND p.isDeleted = false " +
//            "ORDER BY p.price ASC")
//    List<GetProductProjection> findAllSortByPrice(
//            @Param("productCategory") String productCategory,
//            @Param("ageCategory") String ageCategory,
//            @Param("genderCategory") String genderCategory,
//            Pageable pageable
//    );
//
//    @Query(value = "SELECT p.id AS productId, s.id AS sellerId, s.shop_name AS shopName, " +
//            "s.shop_image_url AS shopImageUrl, p.content AS content, " +
//            "p.name AS name, p.price AS price, p.created_at AS createdAt, " +
//            "p.product_category AS productCategory, p.age_category AS ageCategory, " +
//            "p.gender_category AS genderCategory, p.left_amount AS leftAmount, " +
//            "p.thumbnail_url AS thumbnailUrl, p.image_urls AS imageUrls, " +
//            "p.average_star_point AS averageStarPoint " +
//            "FROM product p " +
//            "JOIN seller s ON p.sellers_id = s.id " +
//            "WHERE (p.product_category = :productCategory) " +
//            "AND (p.age_category = :ageCategory) " +
//            "AND (p.gender_category = :genderCategory) " +
//            "AND p.is_deleted = false " +
//            "ORDER BY p.created_at DESC",
//            nativeQuery = true)
//    List<GetProductProjection> findAllSortByCreatedAt(
//            @Param("productCategory") String productCategory,
//            @Param("ageCategory") String ageCategory,
//            @Param("genderCategory") String genderCategory,
//            Pageable pageable
//    );
//
//    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
//            "s.shopImageUrl AS shopImageUrl, " +
//            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
//            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
//            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
//            "FROM Product p " +
//            "JOIN p.seller s " +
//            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
//            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
//            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
//            "AND p.isDeleted = false " +
//            "ORDER BY p.id ASC")
//    List<GetProductProjection> findAllSortById(
//            @Param("productCategory") String productCategory,
//            @Param("ageCategory") String ageCategory,
//            @Param("genderCategory") String genderCategory,
//            Pageable pageable
//    );
//
//    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
//            "s.shopImageUrl AS shopImageUrl, " +
//            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
//            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
//            "p.thumbnailUrl AS thumbnailUrl, p.averageStarPoint AS averageStarPoint " +
//            "FROM Product p " +
//            "JOIN p.seller s " +
//            "WHERE p.id IN :productIds")
//    List<GetProductProjection> findProductsByProductIds(@Param("productIds") List<Long> productIds);
//
//    void deleteBySellerIdAndId(Long id, Long productId);
//
//    Product findBySellerIdAndId(Long id, Long id1);
//
//    List<Product> findProductsBySellerIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<Product> getProductDetailWithReviewListByProductId(long productId);
}
