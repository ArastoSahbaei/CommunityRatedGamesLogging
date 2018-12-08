package com.communityratesgames.logging.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "logs")
public class Logging implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;
    @Column(name="logintime")
    private Timestamp recieved;
    @Lob
    @Column(name="user", columnDefinition = "text")
    private String user;

    public Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getRecieved() {
        return this.recieved;
    }

    public void setRecieved(Timestamp recieved) {
        this.recieved = recieved;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
