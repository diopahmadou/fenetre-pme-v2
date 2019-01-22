package com.solenboutique.fenetre_pme_v2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.solenboutique.fenetre_pme_v2.domain.enumeration.EtatPret;

/**
 * A Pret.
 */
@Entity
@Table(name = "pret")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pret implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Long montant;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatPret etat;

    @NotNull
    @Column(name = "taux", nullable = false)
    private Float taux;

    @Column(name = "echeancier")
    private Integer echeancier;

    @Column(name = "garanties")
    private String garanties;

    @OneToMany(mappedBy = "pret")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fichier> fichiers = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("prets")
    private Pme pme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontant() {
        return montant;
    }

    public Pret montant(Long montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public EtatPret getEtat() {
        return etat;
    }

    public Pret etat(EtatPret etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatPret etat) {
        this.etat = etat;
    }

    public Float getTaux() {
        return taux;
    }

    public Pret taux(Float taux) {
        this.taux = taux;
        return this;
    }

    public void setTaux(Float taux) {
        this.taux = taux;
    }

    public Integer getEcheancier() {
        return echeancier;
    }

    public Pret echeancier(Integer echeancier) {
        this.echeancier = echeancier;
        return this;
    }

    public void setEcheancier(Integer echeancier) {
        this.echeancier = echeancier;
    }

    public String getGaranties() {
        return garanties;
    }

    public Pret garanties(String garanties) {
        this.garanties = garanties;
        return this;
    }

    public void setGaranties(String garanties) {
        this.garanties = garanties;
    }

    public Set<Fichier> getFichiers() {
        return fichiers;
    }

    public Pret fichiers(Set<Fichier> fichiers) {
        this.fichiers = fichiers;
        return this;
    }

    public Pret addFichier(Fichier fichier) {
        this.fichiers.add(fichier);
        fichier.setPret(this);
        return this;
    }

    public Pret removeFichier(Fichier fichier) {
        this.fichiers.remove(fichier);
        fichier.setPret(null);
        return this;
    }

    public void setFichiers(Set<Fichier> fichiers) {
        this.fichiers = fichiers;
    }

    public Pme getPme() {
        return pme;
    }

    public Pret pme(Pme pme) {
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
        Pret pret = (Pret) o;
        if (pret.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pret.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pret{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", etat='" + getEtat() + "'" +
            ", taux=" + getTaux() +
            ", echeancier=" + getEcheancier() +
            ", garanties='" + getGaranties() + "'" +
            "}";
    }
}
