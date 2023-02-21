package es.codeurjc.webapp17.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        ADMIN("ADMIN"), USER("USER"), NONE("NONE");
        private String code;
        Role(String code){
            this.code = code;
        }
        public String getCode() {
            return code;
        }
        
    }

    public static Logger getLogger(Class base_class){
        return LoggerFactory.getLogger(base_class);
    }
}
