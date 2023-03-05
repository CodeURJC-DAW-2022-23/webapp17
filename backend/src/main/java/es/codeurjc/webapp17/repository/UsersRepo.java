package es.codeurjc.webapp17.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.codeurjc.webapp17.model.UserProfile;

public interface UsersRepo extends JpaRepository<UserProfile, Long>{
    List<UserProfile> findByEmail(String email);


    // This query computes the near path recomendations for products
    @Query(value="select product_id from (select created_by_id, product_id from cart join cart_item on cart_item.cart_id=cart.id) "+
    "join (select created_by_id as c, product_id as p from cart join cart_item on cart_item.cart_id=cart.id) where "+
    "(p in (select product_id from cart join cart_item on cart_item.cart_id=cart.id where cart_id=:order) and c=created_by_id) group"+
    " by product_id order by count(product_id) desc", nativeQuery = true)
    List<Long> getRecomendedByProductList(@Param("order") Long order);

    @Query(value="SELECT COUNT(*) AS total_users FROM USER_PROFILE;", nativeQuery = true)
    int getTotalUsers();
}
