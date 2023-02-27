package es.codeurjc.webapp17.service;

import java.io.IOException;
import java.sql.Timestamp;
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
import es.codeurjc.webapp17.repository.UsersRepo;
import es.codeurjc.webapp17.repository.CommentsRepo;
import es.codeurjc.webapp17.model.Image;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class DatabaseInitializer {
    
    @Autowired
    private ProductsRepo products;

    @Autowired
    private CommentsRepo comments;

    @Autowired
    private UsersRepo users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws IOException{

        //EJEMPLOS DE PLATOS

        String vendido = "Más vendido";
        String cerdo = "Cerdo";
        String vacuno = "Vacuno";
        String postre = "Postre";
        String guarnicion = "Postre";
        String bebida = "Bebida";
        String entrante = "Entrante";
        String patoSt = "Pato";

        String[] tags1 = {vendido, cerdo};
        String[] tags2 = {vendido, vacuno};
        String[] tags3 = {vendido, guarnicion};
        String[] tags4 = {vendido, patoSt};
        String[] tags5 = {vendido, postre};
        String[] tagsCerdo = {cerdo};
        String[] tagsEntrante = {entrante};
        String[] tagsPostre = {postre};
        String[] tagsBebida = {bebida};
        String[] tagsVendido = {vendido};
        
        Product feijoada = new Product("Feijoada", 
         "La feijoada o frijolada es el plato nacional de Brasil, se trata de un guiso de alubias negras o frijoles y verduras, acompañados de carne de cerdo y embutidos.", 
         Float.parseFloat("17"), tags1);
        setProductImage(feijoada, "/static/images/Feijoada.jpg");
        setProductImage(feijoada, "/static/images/Feijoada2.jpg");
        
        Product picanha = new Product("Picanha", 
         "La carne de picaña procede de los cuartos traseros, la zona posterior de la cadera, una zona donde a las terneras y las vacas se las golpea con una caña para dirigir su paso y marcar su velocidad.", 
         Float.parseFloat("24"),tags2);
        setProductImage(picanha, "/static/images/Picana.jpg");

        Product yuca = new Product("Mandioca frita", 
         "Yuca frita. Una gran alternativa a las patatas fritas tradicionales", 
         Float.parseFloat("4"), tags3);
        setProductImage(yuca, "/static/images/Yuca.jpg");

        Product coxinha = new Product("Coxinha e quibe", 
         "Croquetas brasileñas c/ 06 unidades", 
         Float.parseFloat("9"), tagsEntrante);
        setProductImage(coxinha, "/static/images/Coxinha.jpg");

        Product calabresa = new Product("Calabresa", 
         "Salchicha ahumada acompañado con patatas fritas", 
         Float.parseFloat("13"), tagsCerdo);
        setProductImage(calabresa, "/static/images/Calabresa.jpg");

        Product pao = new Product("Pão de queijo de minas", 
         "Panecillos de yuca. Se vende por unidad", 
         Float.parseFloat("4"), tags3);
        setProductImage(pao, "/static/images/PanDeQueijo.jpg");

        Product tarta = new Product("Tarta de la jefa", 
         "Tarta de galleta con crema de leche condensada y coco", 
         Float.parseFloat("5"), tagsPostre); 
        setProductImage(tarta, "/static/images/TartaJefa.jpg");

        Product pastel = new Product("Pastel especial", 
         "Pastel de calabaza y coco", 
         Float.parseFloat("5"), tagsPostre);
        setProductImage(pastel, "/static/images/Pastel.jpg");

        Product agua = new Product("Botella de agua 1L", 
         "Bebida refrescante", 
         Float.parseFloat("2.5"), tagsBebida);
        setProductImage(agua, "/static/images/Agua.jpg");

        Product cocacola = new Product("Coca Cola 1L", 
         "Bebida refrescante", 
         Float.parseFloat("3"), tagsBebida);
        setProductImage(cocacola, "/static/images/CocaCola.jpg");

        Product nestea = new Product("Nestea 1L", 
         "Bebida refrescante", 
         Float.parseFloat("3"), tagsBebida);
        setProductImage(nestea, "/static/images/Nestea.jpg");

        Product pato = new Product("Pato no Tucupi", 
         "Guiso de pato originario del norte de Brasil servido con arroz", 
         Float.parseFloat("14"), tags4);
        setProductImage(pato, "/static/images/PatoNoTucupi.jpg");

        Product vatapa = new Product("Vatapa", 
         "Guiso brasileño a base de camarones, leche de coco, cacahuetes y frutas y verduras tropicales", 
         Float.parseFloat("17"), tagsVendido);
        setProductImage(vatapa, "/static/images/Vatapa.jpg");

        Product acaraje = new Product("Acarajé", 
         "Hamburguesa de frijoles de ojos negros que se fríe y se sirve caliente y humeante. Se acompaña de langostinos y aceite de chile", 
         Float.parseFloat("8"), tagsVendido);
        setProductImage(acaraje, "/static/images/Acaraje.jpg");

        Product brigadeiro = new Product("Brigadeiro", 
         "Bolas de tobas hechas con leche condensada y cubiertas de espolvoreos de chocolate", 
         Float.parseFloat("5"), tags5);
        setProductImage(brigadeiro, "/static/images/Brigadeiro.jpg");

        products.save(coxinha);
        products.save(feijoada);
        products.save(picanha);
        products.save(calabresa);
        products.save(pato);
        products.save(vatapa);
        products.save(acaraje);
        products.save(yuca);
        products.save(pao);
        products.save(agua);
        products.save(cocacola);
        products.save(nestea);
        products.save(brigadeiro);
        products.save(tarta);
        products.save(pastel);

        //EJEMPLOS DE USUARIOS

        UserProfile jorge = new UserProfile("test@test.com", 
         "test", passwordEncoder.encode("test"));
        UserProfile alejandro = new UserProfile("alejandro@gmail.com", "Alejandro",
         passwordEncoder.encode("contr"));
        UserProfile guillermo = new UserProfile("guillermo@gmail.com", "Guillermo",
         passwordEncoder.encode("aaa"));
        UserProfile jesus = new UserProfile("jesus@gmail.com", "Jesus",
         passwordEncoder.encode("1234"));

        users.save(jorge);
        users.save(alejandro);
        users.save(guillermo);
        users.save(jesus);

        //EJEMPLOS DE COMENTARIOS

        Comment commentFeij1 = new Comment(9,"Está buenísimo. Repetiría con mucho gusto.",
         new Timestamp(System.currentTimeMillis()));
        setComment(commentFeij1,feijoada,guillermo);
        
        Comment commentPican1 = new Comment(8,"El corte es perfecto, me gustaría conocer al chef",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentPican1,picanha,guillermo);

        Comment commentCox1 = new Comment(8,"Increíbles croquetas",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentCox1,coxinha,guillermo);

        Comment commentCox2 = new Comment(9,"Exquisito entrante",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentCox2,coxinha,alejandro);

        Comment commentCox3 = new Comment(6,"Bastante sobrevalorado. Correcto",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentCox3,coxinha,jorge);

        Comment commentCox4 = new Comment(10,"El sabor es espectacular",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentCox4,coxinha,jorge);

        Comment commentCox5 = new Comment(7,"Un poco caro",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentCox5,coxinha,jesus);

        Comment commentCox6 = new Comment(9,"Wow",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentCox6,coxinha,alejandro);

        Comment commentTarta1 = new Comment(8,"El mejor postre sin duda",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentTarta1,tarta,alejandro);

        Comment commentTarta2 = new Comment(8,"Increíble fusión de sabores",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentTarta2,tarta,jorge);

        Comment commentFeij2 = new Comment(6,"Plato correcto pero caro",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentFeij2,feijoada,jesus);

        Comment commentPao1 = new Comment(9,"Ideal para acompañar",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentPao1,pao,guillermo);

        Comment commentYuca1 = new Comment(6,"La cantidad es pequeña",
        new Timestamp(System.currentTimeMillis()));
        setComment(commentYuca1,yuca,jorge);

         comments.save(commentFeij1);
         comments.save(commentFeij2);
         comments.save(commentPican1);
         comments.save(commentTarta1);
         comments.save(commentTarta2);
         comments.save(commentCox1);
         comments.save(commentCox2);
         comments.save(commentCox3);
         comments.save(commentCox4);
         comments.save(commentCox5);
         comments.save(commentCox6);
         comments.save(commentPao1);
         comments.save(commentYuca1);
        
    }

    private void setProductImage(Product product, String classpathResource) throws IOException { 
		product.setNumberOfImages(product.getNumberOfImages()+1);
		Resource image = new ClassPathResource(classpathResource);
        Image img = new Image(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        List <Image> imagesList = product.getImages();
        img.setPositionInProduct(imagesList.size());
        if (img.getPositionInProduct()==0) {
            img.setFirstOne(true);
        } else {
            img.setFirstOne(false);
        }
        imagesList.add(img);
		product.setImages(imagesList);
        product.getImages().get(imagesList.size()-1).setProduct(product);
	}

    private void setComment(Comment comment, Product product, UserProfile user){
        List <Comment> commentsList = product.getComments();
        commentsList.add(comment);
        product.setComments(commentsList);
        product.getComments().get(commentsList.size()-1).setProduct(product);
        product.getComments().get(commentsList.size()-1).setUser(user);
    }

}