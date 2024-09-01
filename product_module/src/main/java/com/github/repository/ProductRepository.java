package com.github.repository;

import com.github.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
            "AND p.isDeleted = false " +
            "AND p.name LIKE :searchToken " +
            "ORDER BY p.price ASC")
    List<GetProductProjection> searchProductSortByPrice(
            @Param("searchToken") String searchToken,
            @Param("productCategory") String productCategory,
            @Param("ageCategory") String ageCategory,
            @Param("genderCategory") String genderCategory,
            Pageable pageable
    );

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
            "AND p.isDeleted = false " +
            "AND p.name LIKE :searchToken " +
            "ORDER BY p.createdAt DESC")
    List<GetProductProjection> searchProductSortByCreatedAt(
            @Param("searchToken") String searchToken,
            @Param("productCategory") String productCategory,
            @Param("ageCategory") String ageCategory,
            @Param("genderCategory") String genderCategory,
            Pageable pageable
    );

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
            "AND p.isDeleted = false " +
            "AND p.name LIKE :searchToken " +
            "ORDER BY p.id ASC")
    List<GetProductProjection> searchProductSortById(
            @Param("searchToken") String searchToken,
            @Param("productCategory") String productCategory,
            @Param("ageCategory") String ageCategory,
            @Param("genderCategory") String genderCategory,
            Pageable pageable
    );

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
            "AND p.isDeleted = false " +
            "ORDER BY p.price ASC")
    List<GetProductProjection> findAllSortByPrice(
            @Param("productCategory") String productCategory,
            @Param("ageCategory") String ageCategory,
            @Param("genderCategory") String genderCategory,
            Pageable pageable
    );

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
            "AND p.isDeleted = false " +
            "ORDER BY p.createdAt DESC")
    List<GetProductProjection> findAllSortByCreatedAt(
            @Param("productCategory") String productCategory,
            @Param("ageCategory") String ageCategory,
            @Param("genderCategory") String genderCategory,
            Pageable pageable
    );

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.imageUrls AS imageUrls, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE (:productCategory IS NULL OR p.productCategory = :productCategory) " +
            "AND (:ageCategory IS NULL OR p.ageCategory = :ageCategory) " +
            "AND (:genderCategory IS NULL OR p.genderCategory = :genderCategory) " +
            "AND p.isDeleted = false " +
            "ORDER BY p.id ASC")
    List<GetProductProjection> findAllSortById(
            @Param("productCategory") String productCategory,
            @Param("ageCategory") String ageCategory,
            @Param("genderCategory") String genderCategory,
            Pageable pageable
    );

    @Query("SELECT p.id AS productId, s.id AS sellerId, s.shopName AS shopName, " +
            "s.shopImageUrl AS shopImageUrl, " +
            "p.content AS content, p.name AS name, p.price AS price, p.createdAt AS createdAt, p.productCategory AS productCategory, " +
            "p.ageCategory AS ageCategory, p.genderCategory AS genderCategory, p.leftAmount AS leftAmount, " +
            "p.thumbnailUrl AS thumbnailUrl, p.averageStarPoint AS averageStarPoint " +
            "FROM Product p " +
            "JOIN p.seller s " +
            "WHERE p.id IN :productIds")
    List<GetProductProjection> findProductsByProductIds(@Param("productIds") List<Long> productIds);

    void deleteBySellerIdAndId(Long id, Long productId);

    Product findBySellerIdAndId(Long id, Long id1);

    List<Product> findProductsBySellerIdAndIsDeleted(Long id, boolean isDeleted);

    Optional<Product> findOneById(long productId);
}
