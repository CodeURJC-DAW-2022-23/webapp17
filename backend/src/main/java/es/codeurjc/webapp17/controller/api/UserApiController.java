package es.codeurjc.webapp17.controller.api;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import es.codeurjc.webapp17.service.UsersService;
import es.codeurjc.webapp17.tools.NeedsSecurity;
import es.codeurjc.webapp17.tools.Tools;

@RestController
@RequestMapping(Tools.API_HEADER+"/user/")
public class UserApiController {

    @Autowired
    UsersService usersService;

    @GetMapping("/{id}/image")
    @NeedsSecurity(role=Tools.Role.NONE)
    public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException, IOException {
        ResponseEntity<Object> res = usersService.getUserImage(id);
        if (res != null) {
            return res;
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }	
    }
}
