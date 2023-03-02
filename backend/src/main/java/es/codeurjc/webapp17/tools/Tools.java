package es.codeurjc.webapp17.tools;


import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import ch.qos.logback.core.boolex.Matcher;
import es.codeurjc.webapp17.model.Image;

public class Tools {
    public static enum HashMethod{
        MD5("MD5"), SHA_1("SHA-1");
        private String code;
        HashMethod(String code){
            this.code = code;
        }
        public String getCode() {
            return code;
        }
    }

    public static enum Role{
        ADMIN("ADMIN"), USER("USER"), NONE("NONE"), AUTH("AUTH");
        private String code;
        Role(String code){
            this.code = code;
        }
        public String getCode() {
            return code;
        }
        
    }

    public static Blob resourceToBlob(String resource) throws IOException{
        Resource image = new ClassPathResource(resource);
        return BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
    }

    public static Logger getLogger(Class baseClass){
        return LoggerFactory.getLogger(baseClass);
    }

    public static String tr(String code, String locale){
        return java.util.ResourceBundle
                .getBundle("lang/"+locale)
                .getString(code);
    }

    public static class PaginationMustache{

        private boolean hasMore;

        private Object elements;

        private int page;

        public PaginationMustache(boolean hasMore, Object elements, int page){
            this.hasMore = hasMore;
            this.elements = elements;
            this.page = page;
        }

    }

}
