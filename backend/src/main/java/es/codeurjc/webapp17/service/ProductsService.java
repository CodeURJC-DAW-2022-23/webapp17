package es.codeurjc.webapp17.service;

import java.net.URI;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.webapp17.model.CartItem;
import es.codeurjc.webapp17.model.Comment;
import es.codeurjc.webapp17.model.Product;
import es.codeurjc.webapp17.model.UserProfile;
import es.codeurjc.webapp17.repository.ProductsRepo;
import es.codeurjc.webapp17.tools.Tools;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductsService {
    
    @Autowired
    UsersService usersService;

    @Autowired
    PermissionsService permissionsService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    private ProductsRepo products;

    public ProductsRepo getProductsRepo(){
        return products;
    }

    public List<Product> getProducts() {
        return products.findAll();
    }

    public void modifyProduct(long id, String title, Float price, String description, String[] tags){
        Product product = products.findById(id).get(0);
        product.setTtile(title);
        product.setPrice(price);
        if(description != null){
            product.setDescription(description);
        }
        if(tags != null){
            product.setTags(tags);
        }
        getProductsRepo().saveAndFlush(product);
    }

    public void deleteProduct(long id){
        products.delete(products.getReferenceById(id));
    }

    public ResponseEntity<Object> downloadImage(long id, int idImage) throws SQLException {
        List<Product> product = getProductsRepo().findById(id);
        if (!product.isEmpty() && product.get(0).getImages().get(idImage).getImageFile() != null) {
            return product.get(0).getImages().get(idImage).toHtmEntity();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }	
    }

    public void editProductImage(long id, int index){ 
        Product product = products.findById(id).get(0);
        product.setImages(null);
    }

    public ResponseEntity<Object> addComment(HttpServletRequest request, long id, String content, int stars) throws SQLException {
        List<Product> product = getProductsRepo().findById(id);
        if (!product.isEmpty() && request.getUserPrincipal() != null) {
            UserProfile user = usersService.getUser(request.getUserPrincipal().getName());
                Comment comment = new Comment(stars,content,
                    new Timestamp(System.currentTimeMillis()), user, product.get(0));
                product.get(0).getComments().add(comment);
                getProductsRepo().saveAndFlush(product.get(0));
                return ResponseEntity.status(HttpStatus.ACCEPTED).location(URI.create(Tools.API_HEADER+"/products/comment")).build();
            } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }	
    }

    public Page<Product> getProducts(int numPage, int pageSize) {
        Pageable pageable = PageRequest.of(numPage, pageSize);
        return products.findAll(pageable);
    }

    public int getTotalPages(List<Product> listProducts) {
        int pageSize = 8;
        int totalProducts = listProducts.size();
        int totalPages = totalProducts / pageSize;
        if (totalProducts % pageSize != 0) {
            totalPages++;
        }
        return totalPages;
    }

    public void addProduct(String name, Float price, String description, String[] tags){
        if (description == null){
            description = "";
        }
        Product product = new Product(name, description, price, tags);
        getProductsRepo().saveAndFlush(product);
    }

    public HashMap<String, Object> productsPaginated(int page, HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        try {
            int pageSize = 8;
            List<Product> listProducts = getProducts();
            int totalPages = getTotalPages(listProducts);
            boolean moreProducts = true;
            map.put("totalPages", totalPages);
            map.put("currentPage", page);
            if (page<=totalPages-1){
                map.put("product", getProducts(page, pageSize));
            } else {
                moreProducts=false;
                map.put("product", null);
            }
            map.put("moreProducts", moreProducts);

            if(request.getUserPrincipal() != null){
                UserProfile user = usersService.getUser(request.getUserPrincipal().getName());
                if(user != null && !user.getOrders().isEmpty()){
                    List<Long> recomendedProducts = usersService.getUsersRepo()
                        .getRecomendedByProductList(user.getOrders().get(Math.max(user.getOrders().size()-2, 0)).getId());
                    List<Product> products = getProductsRepo().findAllById(recomendedProducts);
                    if(products.size() > 0){
                        map.put("recomended_product", products.subList(0, Math.min(products.size(), 4)));
                        map.put("has_recomended", true);
                    }
                }
            }
        } catch (Exception ex) {}
        
        return map;
    }

    public HashMap<String,Object> addToCart(long id, HttpServletRequest request){
    HashMap<String, Object> map = new HashMap<>();
        Product product = getProductsRepo().findById(id).get(0);
        try{
            UserProfile user = usersService.getUsersRepo().findByEmail(request.getUserPrincipal().getName()).get(0);
            user.getCart().addCartItem(new CartItem(product,user.getCart()));
            usersService.getUsersRepo().saveAndFlush(user);
            map.put("ok","true");
        }catch(NullPointerException ex){
            map.put("Login", "true");
        }
        return map;
    }

    public HashMap<String,Object> descriptionProduct(long id, int page, HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();
        try {
            int pageSize = 4;
            Product product = getProducts().get((int) id - 1);
            List<Comment> listComments = product.getComments();
            int totalPages = commentsService.getTotalPagesComments(listComments);
            boolean moreProducts = true;
            //Page<Product> test = productsService.getProducts(page, pageSize);
            map.put("totalPages", totalPages);
            map.put("currentPage", page);
            map.put("product", product);
            //model.addAttribute("product", productsService.getProductsRepo().findById(id));
            if (page<=totalPages-1){
                map.put("productComments", commentsService.getComments(product, page, pageSize));
            } else {
                moreProducts=false;
                map.put("productComments", null);
            }
            map.put("moreComments", moreProducts);
            if(request.getUserPrincipal() != null)
            map.put("userProfile_id", usersService.getUser(request.getUserPrincipal().getName()).getID());
            } catch (Exception ex) {}
        return map;
    }

}