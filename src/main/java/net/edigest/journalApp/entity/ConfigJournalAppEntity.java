package net.edigest.journalApp.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Journal_App_Config")
public class ConfigJournalAppEntity {

    private String key;
    private String value;

}