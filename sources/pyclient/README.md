Per avviare il calcolo delle metriche, inserire da linea di comando in
```
docker-compose -f ./docker-compose-dev.yml up
```

Nonostante docker-compose venga utilizzato principalmente per il multi container deployment, per motivi di praticità e semplicità viene da noi utilizzato per evitare il passaggio di parametri direttamente da command line in fase di sviluppo.
Il file .csv viene salvato nello stesso volume accessibile dal container.

## (IMPORTANTE) Spiegazione variabili d'ambiente
```yml
environment:
                # queste prime 6 variabili sono quelle richieste nella consegna
                N1: 10
                N2: 100
                N3: 50
                N4: 20
                P1: 0.9
                P2: 0.87
                
                # IMPORTANTE: SI ASSUME CHE L'USER SIA STATO CREATO PRIMA CHE VENGA AVVIATO IL CLIENT PYTHON
                USERNAME: user@a.a
                PASSWORD: USERNAME
                ENDPOINT: http://osa.localhost/fms
                
                # LOAD_SEC è la durata in secondi delle richieste che vengono simulate dal client
                # utilizzato dalle richieste Load2 e Load3
                LOAD_SEC: 30
                
                # I due valori sottostanti servono per la Load4, per simulare file esistenti e non esistenti
                EXISTEND_FILE_ID: 2
                NON_EXISTENT_FILE_ID: 3
                
                # Il valore sottostante imposta il nome del file di output contenete le metriche
                OUTPUT_FILE: metrics.csv

```
