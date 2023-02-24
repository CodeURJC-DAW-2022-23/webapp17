package es.codeurjc.webapp17.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.repository.ProductsRepo;
import jakarta.annotation.PostConstruct;

@Service
public class ProductsService {
    
    @Autowired
    private ProductsRepo products;

    @PostConstruct
    public void init(){
        String[] tags1 = {"Más vendido", "Cerdo"};
        String[] tags2 = {"Más vendido", "Vacuno"};
        
        /* Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        UserProfile user = new UserProfile("Guille","guigrsanti@gmail.com");
    
        Product product1 = new Product(1,"Feijoada", "https://www.goya.com/media/3642/feijoada-brazilian-meat-and-bean-stew.jpg?quality=80","La feijoada o frijolada es el plato nacional de Brasil, se trata de un guiso de alubias negras o frijoles y verduras, acompañados de carne de cerdo y embutidos.", 17, tags1, null);
        Comment comment1 = new Comment(1,user,product1,9,"Está buenísimo. Repetiría con mucho gusto.",timestamp);
        List<Comment> comments1 = new ArrayList<>();
        comments1.add(comment1); */

        /* Product product2 = new Product(2,"Picanha", "https://upload.wikimedia.org/wikipedia/commons/9/9e/Picanha3.jpg","La carne de picaña procede de los cuartos traseros, la zona posterior de la cadera, una zona donde a las terneras y las vacas se las golpea con una caña para dirigir su paso y marcar su velocidad.", 24,tags2,null);
        Comment comment2 = new Comment(2,user,product2,8,"El corte es perfecto, me gustaría conocer al chef",timestamp);
        List<Comment> comments2 = new ArrayList<>();
        comments2.add(comment2); */
        
        Product product1 = new Product(1,"Feijoada", "https://us.123rf.com/450wm/diogoppr/diogoppr1307/diogoppr130700191/21498199-feijoada-una-receta-tradicional-de-la-cocina-brasile%C3%B1a.jpg","La feijoada o frijolada es el plato nacional de Brasil, se trata de un guiso de alubias negras o frijoles y verduras, acompañados de carne de cerdo y embutidos.", 17, tags1);
        Product product2 = new Product(2,"Picanha", "https://us.123rf.com/450wm/luizrocha/luizrocha1708/luizrocha170800273/84115446-picanha-a-la-parrilla-corte-brasile%C3%B1o-tradicional-.jpg","La carne de picaña procede de los cuartos traseros, la zona posterior de la cadera, una zona donde a las terneras y las vacas se las golpea con una caña para dirigir su paso y marcar su velocidad.", 24,tags2);

        products.save(product1);
        products.save(product2);
    }

    public ProductsRepo getProductsRepo(){
        return products;
    }

    public List<Product> getProducts() {
        return products.findAll();
    }

}