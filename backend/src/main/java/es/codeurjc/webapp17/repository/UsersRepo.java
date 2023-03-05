package es.codeurjc.webapp17.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.webapp17.model.UserProfile;

public interface UsersRepo extends JpaRepository<UserProfile, Long>{
    List<UserProfile> findByEmail(String email);


    // This query computes the near path recomendations for products
    @Query(value="SELECT PRODUCT_ID FROM (SELECT CREATED_BY_ID, PRODUCT_ID FROM CART JOIN CART_ITEM ON CART_ITEM.CART_ID=CART.ID) "+
    "JOIN (SELECT CREATED_BY_ID AS C, PRODUCT_ID AS P FROM CART JOIN CART_ITEM ON CART_ITEM.CART_ID=CART.ID) WHERE "+
    "(P IN (SELECT PRODUCT_ID FROM CART JOIN CART_ITEM ON CART_ITEM.CART_ID=CART.ID WHERE CART_ID=:order) AND C=CREATED_BY_ID) GROUP"+
    " BY PRODUCT_ID ORDER BY COUNT(PRODUCT_ID) DESC", nativeQuery = true)
    List<Long> getRecomendedByProductList(@Param("order") Long order);
}
