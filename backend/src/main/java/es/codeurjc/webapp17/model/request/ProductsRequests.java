package es.codeurjc.webapp17.model.request;

public class ProductsRequests {
    public static class ModifyProductsRequest{
        private String name;
        private String description;
        private String tags;
        private Float price;

        public String getName() {
            return name;
        }
        public String getDescription() {
            return description;
        }
        public String getTags() {
            return tags;
        }
        public Float getPrice() {
            return price;
        }      
    }

    public static class CreateProductsRequest{
        private String name;
        private String price;
        private String description;
        private String tags;
        public String getName() {
            return name;
        }
        public String getPrice() {
            return price;
        }
        public String getDescription() {
            return description;
        }
        public String getTags() {
            return tags;
        }
    }

    public static class GetPaginationRequest{
        private int page;

        public int getPage() {
            return page;
        }
    }

    public static class GetCommentInfoRequest{
        private String content;
        private int stars;

        public String getContent() {
            return content;
        }
        public int getStars() {
            return stars;
        }
    }
}
