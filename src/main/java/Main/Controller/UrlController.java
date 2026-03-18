package Main.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import Main.DTO.UrlRequest;
import Main.Service.UrlService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService service;


@PostMapping("/short")
public ResponseEntity<String> shortUrl(@Valid @RequestBody UrlRequest request) {
    String code = service.shortUrl(request.getUrl());
    return ResponseEntity.ok(code);
}

    @PutMapping("/update/{code}")
    public ResponseEntity<Void> updateUrl(
            @PathVariable String code,
            @RequestBody String url) {

        service.updateUrl(url, code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String code) {
        service.deleteUrl(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String originalUrl = service.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}