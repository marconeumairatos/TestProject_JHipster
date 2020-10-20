package com.marconation.jhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A UserAntwort.
 */
@Entity
@Table(name = "user_antwort")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "userantwort")
public class Userantwort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userID;

    @ManyToOne
    @JsonIgnoreProperties(value = "userAntworts", allowSetters = true)
    private Umfrage umfrage;

    @ManyToOne
    @JsonIgnoreProperties(value = "userAntworts", allowSetters = true)
    private Antwort antwort;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public Userantwort userID(Integer userID) {
        this.userID = userID;
        return this;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Umfrage getUmfrage() {
        return umfrage;
    }

    public Userantwort umfrage(Umfrage umfrage) {
        this.umfrage = umfrage;
        return this;
    }

    public void setUmfrage(Umfrage umfrage) {
        this.umfrage = umfrage;
    }

    public Antwort getAntwort() {
        return antwort;
    }

    public Userantwort antwort(Antwort antwort) {
        this.antwort = antwort;
        return this;
    }

    public void setAntwort(Antwort antwort) {
        this.antwort = antwort;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Userantwort)) {
            return false;
        }
        return id != null && id.equals(((Userantwort) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAntwort{" +
            "id=" + getId() +
            ", userID=" + getUserID() +
            "}";
    }
}
