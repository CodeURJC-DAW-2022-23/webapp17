package es.codeurjc.webapp17.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Credential;

public interface CredentialsRepo extends JpaRepository<Credential, Long>{
}
