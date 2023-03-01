package es.codeurjc.webapp17.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idImage;

    @Lob
    @JsonIgnore
    private Blob imageFile;


    public Blob getImageFile() {
        return imageFile;
    }

    public void setImageFile(Blob imageFile) {
        this.imageFile = imageFile;
    }

    public ResponseEntity<Object> toHtmEntity() throws SQLException{
        Resource file = new InputStreamResource(getImageFile().getBinaryStream());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpg").contentLength(getImageFile().length()).body(file);
    }
}
