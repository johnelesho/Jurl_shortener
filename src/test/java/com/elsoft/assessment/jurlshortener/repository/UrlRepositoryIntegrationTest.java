package com.elsoft.assessment.jurlshortener.repository;

import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UrlRepositoryIntegrationTest {

//    @Autowired
//    private TestEntityManager entityManager;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void shouldInsert() {
        UrlEntity urlEntity = new UrlEntity("http://example.com");
        urlEntity = urlRepository.save(urlEntity);

        assertThat(urlEntity.getId()).isNotNull();

        Optional<UrlEntity> urlEntityFromDb = urlRepository.findById(urlEntity.getId());
        assertThat(urlEntityFromDb.isPresent()).isTrue();
        assertThat(urlEntityFromDb.get().getId()).isEqualTo(urlEntity.getId());
    }



    @Test
    public void shouldSave_returnSavedUrl(){
        var url = new UrlEntity("https://linkedln.com/in/johnelesho");
        var url2 = new UrlEntity(2L,"https://www.google.com/search?client=firefox-b-d&q=java.lang.NullPointerException%3A+Cannot+invoke+%22org.springframework.test.web.servlet.MockMvc.perform%28org.springframework.test.web.servlet.RequestBuilder%29%22+because+%22this.mvc%22+is+null", LocalDateTime.now(),LocalDateTime.now().plusDays(3));

        UrlEntity save = urlRepository.save(url);
        assertThat(save).isNotNull();
        assertThat(save.getId()).isGreaterThan(0);

    }
    @Test
    public void shouldSaveAll_returnSavedUrl(){
        var url = new UrlEntity("https://linkedln.com/in/johnelesho");
        var url2 = new UrlEntity(2L,"https://www.google.com/search?client=firefox-b-d&q=java.lang.NullPointerException%3A+Cannot+invoke+%22org.springframework.test.web.servlet.MockMvc.perform%28org.springframework.test.web.servlet.RequestBuilder%29%22+because+%22this.mvc%22+is+null", LocalDateTime.now(),LocalDateTime.now().plusDays(3));
        List<UrlEntity> saved = urlRepository.saveAll(List.of(url,url2));
        assertThat(saved).isNotNull();
        assertThat(saved).isNotEmpty();
        assertThat(saved.size()).isEqualTo(2);
    }
}
