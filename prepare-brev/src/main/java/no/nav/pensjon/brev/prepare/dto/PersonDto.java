package no.nav.pensjon.brev.prepare.dto;

import java.time.LocalDate;

public class PersonDto {

    private String fullnavn;
    private String adresse;
    private LocalDate fodselsDato;
    private String epost;
    private String telefonNummer;

    public String getFullnavn() {
        return fullnavn;
    }

    public void setFullnavn(String fullnavn) {
        this.fullnavn = fullnavn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getFodselsDato() {
        return fodselsDato;
    }

    public void setFodselsDato(LocalDate fodselsDato) {
        this.fodselsDato = fodselsDato;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }

    public String getTelefonNummer() {
        return telefonNummer;
    }

    public void setTelefonNummer(String telefonNummer) {
        this.telefonNummer = telefonNummer;
    }
}
