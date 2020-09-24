package com.marconation.jhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Antwort.
 */
@Entity
@Table(name = "antwort")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "antwort")
public class Antwort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "poll_id", nullable = false)
    private Integer pollID;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JsonIgnoreProperties(value = "antworts", allowSetters = true)
    private Umfrage umfrage;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPollID() {
        return pollID;
    }

    public Antwort pollID(Integer pollID) {
        this.pollID = pollID;
        return this;
    }

    public void setPollID(Integer pollID) {
        this.pollID = pollID;
    }

    public String getText() {
        return text;
    }

    public Antwort text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Umfrage getUmfrage() {
        return umfrage;
    }

    public Antwort umfrage(Umfrage umfrage) {
        this.umfrage = umfrage;
        return this;
    }

    public void setUmfrage(Umfrage umfrage) {
        this.umfrage = umfrage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Antwort)) {
            return false;
        }
        return id != null && id.equals(((Antwort) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Antwort{" +
            "id=" + getId() +
            ", pollID=" + getPollID() +
            ", text='" + getText() + "'" +
            "}";
    }
}
