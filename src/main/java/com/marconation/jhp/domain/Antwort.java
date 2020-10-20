package com.marconation.jhp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "text", nullable = false)
    private String text;

    @OneToMany(mappedBy = "antwort")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Userantwort> userAntworts = new HashSet<>();

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

    public Set<Userantwort> getUserAntworts() {
        return userAntworts;
    }

    public Antwort userAntworts(Set<Userantwort> userAntworts) {
        this.userAntworts = userAntworts;
        return this;
    }

    public Antwort addUserAntwort(Userantwort userAntwort) {
        this.userAntworts.add(userAntwort);
        userAntwort.setAntwort(this);
        return this;
    }

    public Antwort removeUserAntwort(Userantwort userAntwort) {
        this.userAntworts.remove(userAntwort);
        userAntwort.setAntwort(null);
        return this;
    }

    public void setUserAntworts(Set<Userantwort> userAntworts) {
        this.userAntworts = userAntworts;
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
            ", text='" + getText() + "'" +
            "}";
    }
}
