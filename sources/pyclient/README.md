Per avviare il calcolo delle metriche, inserire da linea di comando in
```
docker-compose -f ./docker-compose-dev.yml up
```

Nonostante docker-compose venga utilizzato principalmente per il multi container deployment, per motivi di praticità e semplicità viene da noi utilizzato per evitare il passaggio di parametri direttamente da command line in fase di sviluppo.
Il file .csv viene salvato nello stesso volume accessibile dal container.
