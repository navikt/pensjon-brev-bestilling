# Embedded Kafka broker
Brukes til å starte opp en Spring embedded-kafka broker for 
lokal testing.

## Hvordan bruke
- Start EmbeddedKafkaApplication main class (f.eks. i IntelliJ/Eclipse)
- Start andre moduler du trenger med `embeddedkafka` Spring profil.

> For IntelliJ: `-Dspring.profiles.active=embeddedkafka` in _VM options_ in 
_Run Configuration_.


