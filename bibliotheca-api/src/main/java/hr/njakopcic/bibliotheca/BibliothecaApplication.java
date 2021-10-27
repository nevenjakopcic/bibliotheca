package hr.njakopcic.bibliotheca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BibliothecaApplication {

    @RequestMapping("/hello")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(BibliothecaApplication.class, args);
    }
}
