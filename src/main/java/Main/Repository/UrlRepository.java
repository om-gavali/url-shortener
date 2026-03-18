package Main.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Main.Entity.Url;
import java.util.List;


@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{

	Url findByShortCode(String shortCode);
}
