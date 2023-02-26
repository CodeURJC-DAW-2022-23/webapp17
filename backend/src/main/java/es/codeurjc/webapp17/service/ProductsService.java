package es.codeurjc.webapp17.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.UserProfile;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.repository.ProductsRepo;
import es.codeurjc.webapp17.model.Image;
import jakarta.annotation.PostConstruct;

@Service
public class ProductsService {
    
    @Autowired
    private ProductsRepo products;

    @PostConstruct
    public void init() throws IOException{
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
        
        Product product1 = new Product("Feijoada","La feijoada o frijolada es el plato nacional de Brasil, se trata de un guiso de alubias negras o frijoles y verduras, acompañados de carne de cerdo y embutidos.", Float.parseFloat("17"), tags1);
        setProductImage(product1, "/static/images/feijoada.jpg");
        
        Product product2 = new Product("Picanha", "La carne de picaña procede de los cuartos traseros, la zona posterior de la cadera, una zona donde a las terneras y las vacas se las golpea con una caña para dirigir su paso y marcar su velocidad.", Float.parseFloat("24"),tags2);
        setProductImage(product2, "/static/images/picana.jpg");
        
        products.save(product1);
        products.save(product2);
    }

    //TODO Añadir más imagenes a un producto
    public void setProductImage(Product product, String classpathResource) throws IOException { 
		product.setNumberOfImages(product.getNumberOfImages()+1);
		Resource image = new ClassPathResource(classpathResource);
        Image img = new Image(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        List<Image> images = new ArrayList<>();
        images.add(img);
		product.setImages(images);
        product.getImages().get(product.getNumberOfImages()-1).setProduct(product);
	}

    public ProductsRepo getProductsRepo(){
        return products;
    }

    public List<Product> getProducts() {
        return products.findAll();
    }

}