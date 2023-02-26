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
        String vendido = "Más vendido";
        String cerdo = "Cerdo";
        String vacuno = "Vacuno";
        String postre = "Postre";
        String guarnicion = "Postre";
        String bebida = "Bebida";
        String entrante = "Entrante";
        String pato = "Pato";

        String[] tags1 = {vendido, cerdo};
        String[] tags2 = {vendido, vacuno};
        String[] tags3 = {vendido, guarnicion};
        String[] tags4 = {vendido, pato};
        String[] tags5 = {vendido, postre};
        String[] tagsCerdo = {cerdo};
        String[] tagsEntrante = {entrante};
        String[] tagsPostre = {postre};
        String[] tagsBebida = {bebida};
        String[] tagsVendido = {vendido};
        
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
        setProductImage(product1, "/static/images/Feijoada.jpg");
        setProductImage(product1, "/static/images/Feijoada2.jpg");
        
        Product product2 = new Product("Picanha", "La carne de picaña procede de los cuartos traseros, la zona posterior de la cadera, una zona donde a las terneras y las vacas se las golpea con una caña para dirigir su paso y marcar su velocidad.", Float.parseFloat("24"),tags2);
        setProductImage(product2, "/static/images/Picana.jpg");

        Product product3 = new Product("Mandioca frita", "Yuca frita. Una gran alternativa a las patatas fritas tradicionales", Float.parseFloat("4"), tags3);
        setProductImage(product3, "/static/images/Yuca.jpg");

        Product product4 = new Product("Coxinha e quibe", "Croquetas brasileñas c/ 06 unidades", Float.parseFloat("9"), tagsEntrante);
        setProductImage(product4, "/static/images/Coxinha.jpg");

        Product product5 = new Product("Calabresa", "Salchicha ahumada acompañado con patatas fritas", Float.parseFloat("13"), tagsCerdo);
        setProductImage(product5, "/static/images/Calabresa.jpg");

        Product product6 = new Product("Pão de queijo de minas", "(panecillos de yuca/ unidad)", Float.parseFloat("4"), tags3);
        setProductImage(product6, "/static/images/PanDeQueijo.jpg");

        Product product7 = new Product("Tarta de la jefa", "Tarta de galleta con crema de leche condensada y coco", Float.parseFloat("5"), tagsPostre); 
        setProductImage(product7, "/static/images/TartaJefa.jpg");

        Product product8 = new Product("Pastel especial", "Pastel de calabaza y coco", Float.parseFloat("5"), tagsPostre);
        setProductImage(product8, "/static/images/Pastel.jpg");

        Product product9 = new Product("Botella de agua 1L", "Bebida refrescante", Float.parseFloat("2.5"), tagsBebida);
        setProductImage(product9, "/static/images/Agua.jpg");

        Product product10 = new Product("Coca Cola 1L", "Bebida refrescante", Float.parseFloat("3"), tagsBebida);
        setProductImage(product10, "/static/images/CocaCola.jpg");

        Product product11 = new Product("Nestea 1L", "Bebida refrescante", Float.parseFloat("3"), tagsBebida);
        setProductImage(product11, "/static/images/Nestea.jpg");

        Product product12 = new Product("Pato no Tucupi", "Guiso de pato originario del norte de Brasil servido con arroz", Float.parseFloat("14"), tags4);
        setProductImage(product12, "/static/images/PatoNoTucupi.jpg");

        Product product13 = new Product("Vatapa", "Guiso brasileño a base de camarones, leche de coco, cacahuetes y frutas y verduras tropicales", Float.parseFloat("17"), tagsVendido);
        setProductImage(product13, "/static/images/Vatapa.jpg");

        Product product14 = new Product("Acarajé", "Hamburguesa de frijoles de ojos negros que se fríe y se sirve caliente y humeante. Se acompaña de langostinos y aceite de chile", Float.parseFloat("8"), tagsVendido);
        setProductImage(product14, "/static/images/Acaraje.jpg");

        Product product15 = new Product("Brigadeiro", "Bolas de tobas hechas con leche condensada y cubiertas de espolvoreos de chocolate", Float.parseFloat("5"), tags5);
        setProductImage(product15, "/static/images/Brigadeiro.jpg");

        products.save(product4);
        products.save(product1);
        products.save(product2);
        products.save(product5);
        products.save(product12);
        products.save(product13);
        products.save(product14);
        products.save(product3);
        products.save(product6);
        products.save(product9);
        products.save(product10);
        products.save(product11);
        products.save(product15);
        products.save(product7);
        products.save(product8);
    }

    public void setProductImage(Product product, String classpathResource) throws IOException { 
		product.setNumberOfImages(product.getNumberOfImages()+1);
		Resource image = new ClassPathResource(classpathResource);
        Image img = new Image(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        List <Image> images = product.getImages();
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