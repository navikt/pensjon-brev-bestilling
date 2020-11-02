package no.nav.pensjon.brev.prepare.mapper;

import no.nav.pensjon.brev.prepare.dto.PersonDto;
import no.nav.pensjon.brev.prepare.model.Kontaktinformasjon;
import no.nav.pensjon.brev.prepare.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PersonMapper {

    PersonMapper MAPPER = Mappers.getMapper( PersonMapper.class);

    @Mapping(target = "fullnavn", expression = "java(person.getFornavn() + \" \" + person.getEtternavn())")
    @Mapping(source = "person.bostedsAdresse", target = "adresse")
    @Mapping(source = "kontaktinformasjon.tlfNummer", target = "telefonNummer")
    PersonDto personToPersonDto(Person person, Kontaktinformasjon kontaktinformasjon);
}