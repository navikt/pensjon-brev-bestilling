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
        Person person = new Person();
        person.setFornavn("Hans");
        person.setEtternavn("Hansen");
        person.setBostedsAdresse("Sannergata 2 Oslo");
        person.setFodselsDato(LocalDate.of(1992, 12, 24));
        Kontaktinformasjon kontaktinformasjon = new Kontaktinformasjon();
        kontaktinformasjon.setEpost("hans.hansen@epost.no");
        kontaktinformasjon.setTlfNummer("123456789");

        PersonDto personDto = PersonMapper.MAPPER.personToPersonDto(person, kontaktinformasjon);

        assertThat(personDto.getFullnavn()).isEqualTo("Hans Hansen");
        assertThat(personDto.getAdresse()).isEqualTo("Sannergata 2 Oslo");
        assertThat(personDto.getFodselsDato()).isEqualTo(LocalDate.of(1992, 12, 24));
        assertThat(personDto.getEpost()).isEqualTo("hans.hansen@epost.no");
        assertThat(personDto.getTelefonNummer()).isEqualTo("123456789");
    }
}