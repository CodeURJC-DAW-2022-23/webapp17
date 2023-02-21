package es.codeurjc.webapp17.tools;

public class SecurityTools {
    public static enum HashMethod{
        MD5("MD5"), SHA_1("SHA-1");
        private String code;
        HashMethod(String code){
            this.code = code;
        }
    }
}
