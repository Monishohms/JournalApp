package net.engineeringdigest.journalApp.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data // this annotation is equivalent to getter, setter, toString, equals and HashCode
@NoArgsConstructor
public class ConfigJournalAppEntity {

    private String key;
    private  String value;


}