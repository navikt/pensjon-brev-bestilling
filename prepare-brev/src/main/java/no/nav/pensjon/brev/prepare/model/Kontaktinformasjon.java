package no.nav.pensjon.brev.prepare.model;

public class Kontaktinformasjon {
    private String epost;
    private String tlfNummer;

    public String getTlfNummer() {
        return tlfNummer;
    }

    public void setTlfNummer(String tlfNummer) {
        this.tlfNummer = tlfNummer;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }
}
