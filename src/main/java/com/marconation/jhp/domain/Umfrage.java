package com.marconation.jhp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Umfrage.
 */
@Entity
@Table(name = "umfrage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "umfrage")
public class Umfrage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "umfrage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Antwort> antworts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Umfrage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public Umfrage text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public Umfrage status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Antwort> getAntworts() {
        return antworts;
    }

    public Umfrage antworts(Set<Antwort> antworts) {
        this.antworts = antworts;
        return this;
    }

    public Umfrage addAntwort(Antwort antwort) {
        this.antworts.add(antwort);
        antwort.setUmfrage(this);
        return this;
    }

    public Umfrage removeAntwort(Antwort antwort) {
        this.antworts.remove(antwort);
        antwort.setUmfrage(null);
        return this;
    }

    public void setAntworts(Set<Antwort> antworts) {
        this.antworts = antworts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Umfrage)) {
            return false;
        }
        return id != null && id.equals(((Umfrage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Umfrage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", text='" + getText() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
