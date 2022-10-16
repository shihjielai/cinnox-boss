package com.example.cinnoxboss.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Document(collection = "messages")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {

    @Id
    private String id;

    private String userId;
    private String type;
    private String text;
    private String packageId;
    private String stickerId;

    @CreatedDate
    private Date createdAt;
}
