package Main.Service;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Main.Entity.Url;
import Main.Repository.UrlRepository;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    private final Hashids hashids = new Hashids("my-secret-salt", 6);

    public String shortUrl(String originalUrl) {

        Url url = new Url();
        url.setOriginalUrl(originalUrl);

        Url savedUrl = repository.save(url);

        String shortCode = hashids.encode(savedUrl.getId());
        savedUrl.setShortCode(shortCode);

        repository.save(savedUrl);

        return shortCode;
    }

    public void updateUrl(String originalUrl, String code) {

        Url url = repository.findByShortCode(code);

        if (url == null) {
            throw new RuntimeException("Short code not found");
        }

        url.setOriginalUrl(originalUrl);
        repository.save(url);
    }

    public void deleteUrl(String code) {

        Url url = repository.findByShortCode(code);

        if (url == null) {
            throw new RuntimeException("Short code not found");
        }

        repository.delete(url);
    }

    public String getOriginalUrl(String shortCode) {

        Url url = repository.findByShortCode(shortCode);

        if (url == null) {
            throw new RuntimeException("Short code not found");
        }

        return url.getOriginalUrl();
    }
}