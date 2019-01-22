package com.solenboutique.fenetre_pme_v2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.solenboutique.fenetre_pme_v2.domain.enumeration.FormeJuridique;

/**
 * A Pme.
 */
@Entity
@Table(name = "pme")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_dossier", nullable = false)
    private Integer numeroDossier;

    @NotNull
    @Column(name = "pme_name", nullable = false)
    private String pmeName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "forme_juridique", nullable = false)
    private FormeJuridique formeJuridique;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Column(name = "date_enregistrement")
    private LocalDate dateEnregistrement;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "activite", nullable = false)
    private String activite;

    @NotNull
    @Column(name = "clientel", nullable = false)
    private String clientel;

    @Column(name = "ca_passe")
    private Double caPasse;

    @Column(name = "ca_prevu")
    private Double caPrevu;

    @OneToMany(mappedBy = "pme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContactPme> personneContacts = new HashSet<>();
    @OneToMany(mappedBy = "pme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pret> prets = new HashSet<>();
    @OneToMany(mappedBy = "pme")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Projet> projets = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("pmeGeres")
    private Imf imfGestionnaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroDossier() {
        return numeroDossier;
    }

    public Pme numeroDossier(Integer numeroDossier) {
        this.numeroDossier = numeroDossier;
        return this;
    }

    public void setNumeroDossier(Integer numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public String getPmeName() {
        return pmeName;
    }

    public Pme pmeName(String pmeName) {
        this.pmeName = pmeName;
        return this;
    }

    public void setPmeName(String pmeName) {
        this.pmeName = pmeName;
    }

    public FormeJuridique getFormeJuridique() {
        return formeJuridique;
    }

    public Pme formeJuridique(FormeJuridique formeJuridique) {
        this.formeJuridique = formeJuridique;
        return this;
    }

    public void setFormeJuridique(FormeJuridique formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public Pme dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateEnregistrement() {
        return dateEnregistrement;
    }

    public Pme dateEnregistrement(LocalDate dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
        return this;
    }

    public void setDateEnregistrement(LocalDate dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public String getType() {
        return type;
    }

    public Pme type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivite() {
        return activite;
    }

    public Pme activite(String activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getClientel() {
        return clientel;
    }

    public Pme clientel(String clientel) {
        this.clientel = clientel;
        return this;
    }

    public void setClientel(String clientel) {
        this.clientel = clientel;
    }

    public Double getCaPasse() {
        return caPasse;
    }

    public Pme caPasse(Double caPasse) {
        this.caPasse = caPasse;
        return this;
    }

    public void setCaPasse(Double caPasse) {
        this.caPasse = caPasse;
    }

    public Double getCaPrevu() {
        return caPrevu;
    }

    public Pme caPrevu(Double caPrevu) {
        this.caPrevu = caPrevu;
        return this;
    }

    public void setCaPrevu(Double caPrevu) {
        this.caPrevu = caPrevu;
    }

    public Set<ContactPme> getPersonneContacts() {
        return personneContacts;
    }

    public Pme personneContacts(Set<ContactPme> contactPmes) {
        this.personneContacts = contactPmes;
        return this;
    }

    public Pme addPersonneContact(ContactPme contactPme) {
        this.personneContacts.add(contactPme);
        contactPme.setPme(this);
        return this;
    }

    public Pme removePersonneContact(ContactPme contactPme) {
        this.personneContacts.remove(contactPme);
        contactPme.setPme(null);
        return this;
    }

    public void setPersonneContacts(Set<ContactPme> contactPmes) {
        this.personneContacts = contactPmes;
    }

    public Set<Pret> getPrets() {
        return prets;
    }

    public Pme prets(Set<Pret> prets) {
        this.prets = prets;
        return this;
    }

    public Pme addPret(Pret pret) {
        this.prets.add(pret);
        pret.setPme(this);
        return this;
    }

    public Pme removePret(Pret pret) {
        this.prets.remove(pret);
        pret.setPme(null);
        return this;
    }

    public void setPrets(Set<Pret> prets) {
        this.prets = prets;
    }

    public Set<Projet> getProjets() {
        return projets;
    }

    public Pme projets(Set<Projet> projets) {
        this.projets = projets;
        return this;
    }

    public Pme addProjet(Projet projet) {
        this.projets.add(projet);
        projet.setPme(this);
        return this;
    }

    public Pme removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.setPme(null);
        return this;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }

    public Imf getImfGestionnaire() {
        return imfGestionnaire;
    }

    public Pme imfGestionnaire(Imf imf) {
        this.imfGestionnaire = imf;
        return this;
    }

    public void setImfGestionnaire(Imf imf) {
        this.imfGestionnaire = imf;
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
        Pme pme = (Pme) o;
        if (pme.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pme.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pme{" +
            "id=" + getId() +
            ", numeroDossier=" + getNumeroDossier() +
            ", pmeName='" + getPmeName() + "'" +
            ", formeJuridique='" + getFormeJuridique() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateEnregistrement='" + getDateEnregistrement() + "'" +
            ", type='" + getType() + "'" +
            ", activite='" + getActivite() + "'" +
            ", clientel='" + getClientel() + "'" +
            ", caPasse=" + getCaPasse() +
            ", caPrevu=" + getCaPrevu() +
            "}";
    }
}
