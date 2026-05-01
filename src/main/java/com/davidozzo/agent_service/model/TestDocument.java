package com.davidozzo.agent_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "test_documents")
public class TestDocument {

    @Id
    private String id;
    private String name;

    public TestDocument() {
    }

    public TestDocument(String name) {
        this.name = name;
    }

    public TestDocument(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}