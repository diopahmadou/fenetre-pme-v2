package com.solenboutique.fenetre_pme_v2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Imf.
 */
@Entity
@Table(name = "imf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "agrement", nullable = false)
    private String agrement;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "siege_social")
    private String siegeSocial;

    @Column(name = "registre_commerce")
    private String registreCommerce;

    @Column(name = "ninea")
    private String ninea;

    @Column(name = "secteur_activite")
    private String secteurActivite;

    @Column(name = "forme_juridique")
    private String formeJuridique;

    @Column(name = "capital")
    private Long capital;

    @Column(name = "adresse_agence")
    private String adresseAgence;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "imfGestionnaire")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pme> pmeGeres = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgrement() {
        return agrement;
    }

    public Imf agrement(String agrement) {
        this.agrement = agrement;
        return this;
    }

    public void setAgrement(String agrement) {
        this.agrement = agrement;
    }

    public String getNom() {
        return nom;
    }

    public Imf nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiegeSocial() {
        return siegeSocial;
    }

    public Imf siegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
        return this;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public String getRegistreCommerce() {
        return registreCommerce;
    }

    public Imf registreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
        return this;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public String getNinea() {
        return ninea;
    }

    public Imf ninea(String ninea) {
        this.ninea = ninea;
        return this;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getSecteurActivite() {
        return secteurActivite;
    }

    public Imf secteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
        return this;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public Imf formeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
        return this;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public Long getCapital() {
        return capital;
    }

    public Imf capital(Long capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(Long capital) {
        this.capital = capital;
    }

    public String getAdresseAgence() {
        return adresseAgence;
    }

    public Imf adresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
        return this;
    }

    public void setAdresseAgence(String adresseAgence) {
        this.adresseAgence = adresseAgence;
    }

    public String getTelephone() {
        return telephone;
    }

    public Imf telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public Imf email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Pme> getPmeGeres() {
        return pmeGeres;
    }

    public Imf pmeGeres(Set<Pme> pmes) {
        this.pmeGeres = pmes;
        return this;
    }

    public Imf addPmeGere(Pme pme) {
        this.pmeGeres.add(pme);
        pme.setImfGestionnaire(this);
        return this;
    }

    public Imf removePmeGere(Pme pme) {
        this.pmeGeres.remove(pme);
        pme.setImfGestionnaire(null);
        return this;
    }

    public void setPmeGeres(Set<Pme> pmes) {
        this.pmeGeres = pmes;
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
        Imf imf = (Imf) o;
        if (imf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Imf{" +
            "id=" + getId() +
            ", agrement='" + getAgrement() + "'" +
            ", nom='" + getNom() + "'" +
            ", siegeSocial='" + getSiegeSocial() + "'" +
            ", registreCommerce='" + getRegistreCommerce() + "'" +
            ", ninea='" + getNinea() + "'" +
            ", secteurActivite='" + getSecteurActivite() + "'" +
            ", formeJuridique='" + getFormeJuridique() + "'" +
            ", capital=" + getCapital() +
            ", adresseAgence='" + getAdresseAgence() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
