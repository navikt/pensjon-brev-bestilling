package no.nav.pensjon.brev.prepare.model;

import java.time.LocalDate;

public class Person {
    private String fornavn;
    private String etternavn;
    private String bostedsAdresse;
    private LocalDate fodselsDato;

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getBostedsAdresse() {
        return bostedsAdresse;
    }

    public void setBostedsAdresse(String bostedsAdresse) {
        this.bostedsAdresse = bostedsAdresse;
    }

    public LocalDate getFodselsDato() {
        return fodselsDato;
    }

    public void setFodselsDato(LocalDate fodselsDato) {
        this.fodselsDato = fodselsDato;
    }
}
