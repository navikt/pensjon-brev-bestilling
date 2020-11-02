package no.nav.pensjon.brev.prepare.mapper;

import no.nav.pensjon.brev.prepare.dto.PersonDto;
import no.nav.pensjon.brev.prepare.model.Kontaktinformasjon;
import no.nav.pensjon.brev.prepare.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {
    @Test
    void testPersonToPersonDto() {
        final String FORNAVN = "Hans";
        final String ETTERNAVN = "Hansen";
        final String FULLNAVN = FORNAVN + " " + ETTERNAVN;
        final String ADRESSE = "Sannergata 2 Oslo";
        final LocalDate FODSELSDATO = LocalDate.of(1992, 12, 24);
        final String EPOST = "hans.hansen@epost.no";
        final String TELEFONNUMMER = "123456789";

        Person person = new Person();
        person.setFornavn(FORNAVN);
        person.setEtternavn(ETTERNAVN);
        person.setBostedsAdresse(ADRESSE);
        person.setFodselsDato(FODSELSDATO);
        Kontaktinformasjon kontaktinformasjon = new Kontaktinformasjon();
        kontaktinformasjon.setEpost(EPOST);
        kontaktinformasjon.setTlfNummer(TELEFONNUMMER);

        PersonDto personDto = PersonMapper.MAPPER.personToPersonDto(person, kontaktinformasjon);

        assertThat(personDto.getFullnavn()).isEqualTo(FULLNAVN);
        assertThat(personDto.getAdresse()).isEqualTo(ADRESSE);
        assertThat(personDto.getFodselsDato()).isEqualTo(FODSELSDATO);
        assertThat(personDto.getEpost()).isEqualTo(EPOST);
        assertThat(personDto.getTelefonNummer()).isEqualTo(TELEFONNUMMER);
    }
}