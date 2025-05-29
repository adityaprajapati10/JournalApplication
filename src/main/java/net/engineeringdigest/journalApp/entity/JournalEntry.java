package net.engineeringdigest.journalApp.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Document(collection = "journal_entries") // the entity is mapped with mongodb collection
@Data
public class JournalEntry {

    @Id
    private ObjectId id;
    private  String title;
    private String content;
    private LocalDateTime date;
}
