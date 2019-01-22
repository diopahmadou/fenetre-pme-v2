package com.solenboutique.fenetre_pme_v2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Projet.
 */
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "investissement_materiel")
    private String investissementMateriel;

    @Column(name = "fond_roulement")
    private Long fondRoulement;

    @Column(name = "organismes_financement")
    private String organismesFinancement;

    @ManyToOne
    @JsonIgnoreProperties("projets")
    private Pme pme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvestissementMateriel() {
        return investissementMateriel;
    }

    public Projet investissementMateriel(String investissementMateriel) {
        this.investissementMateriel = investissementMateriel;
        return this;
    }

    public void setInvestissementMateriel(String investissementMateriel) {
        this.investissementMateriel = investissementMateriel;
    }

    public Long getFondRoulement() {
        return fondRoulement;
    }

    public Projet fondRoulement(Long fondRoulement) {
        this.fondRoulement = fondRoulement;
        return this;
    }

    public void setFondRoulement(Long fondRoulement) {
        this.fondRoulement = fondRoulement;
    }

    public String getOrganismesFinancement() {
        return organismesFinancement;
    }

    public Projet organismesFinancement(String organismesFinancement) {
        this.organismesFinancement = organismesFinancement;
        return this;
    }

    public void setOrganismesFinancement(String organismesFinancement) {
        this.organismesFinancement = organismesFinancement;
    }

    public Pme getPme() {
        return pme;
    }

    public Projet pme(Pme pme) {
        this.pme = pme;
        return this;
    }

    public void setPme(Pme pme) {
        this.pme = pme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Projet projet = (Projet) o;
        if (projet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", investissementMateriel='" + getInvestissementMateriel() + "'" +
            ", fondRoulement=" + getFondRoulement() +
            ", organismesFinancement='" + getOrganismesFinancement() + "'" +
            "}";
    }
}
