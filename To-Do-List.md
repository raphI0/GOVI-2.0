# GOVI-2.0 - To do List :
[exception](GOVI-Back%2Fsrc%2Fmain%2Fjava%2Fcom%2Fsncf%2Fgovi%2Fservice%2Fexception)
Front:
-
- Ne pas afficher les W les gars RATP.  
- Ne pas afficher les trains de 0s.  
- Ne pas afficher les trains identiques à l'arrivée et au départ (sauf cas passage par voie Z notamment) 
- Verifier que le component GoviDisplayer accepte et affiche les code mission au format SAS (10SOSI au lieu de SOSI10 par exemple) 
- Afficher les trains sur Mitry voie GI même si 0s de retournement (Dé-garage/ Garage).
- Limiter le front à afficher jusqu'à 1H30 à J+1 (lui imposer de ne pas aller au-delà)
- Afficher les trains de 00:01 à partir de l'heure de départ et non d'arrivée (et pas besoins d'afficher pour eux les missions d'arrivées)
- écourter les traits trop longs pour les remplacer par des petits au début et à la fin (cf GOVI classique)
- l'affichage doit se faire de 3H30 à 1H30, avec une exception pour 00:01 qui sont à retenir puisque provenant de la veille (stationnement à quai) et ceux qui restent à quai le soir et ne partent que le lendemain (+CODEMISSION, cf GOVI classique)
- Unir les passes-minuit en une seule ligne
- Faire avancer la timeline du GOVI automatiquement par rapport à l'heure qu'il est (pouvoir bouger manuellement si besoin, ce qui désactivera le tracking automatiquement, mais pouvoir le remettre de nouveau actif en un click)
- Automatiquement ne pas afficher les quais vides, sans retournement

Back :
-
- Faire une méthode unique pour la création des retournements, qui permette de créer ceux de la RATP et du BHL SNCF  
- Pour la voie GI de Mitry, ajouter du temps de retournement et mettre QUE la mission de départ (Dé-garage) dans le retournement. (Attention aux W à 0s globalement)
- Verifier pourquoi les quais avec ID 3 et 5, pour la gare de Mitry sont vides alors que le BHL ne l'est pas
- Créer 2 hashmap, en sorti de fonction recherche PS/FS, qui donne pour chaque conducteur sa mission PS dans une hashmap, sa FS dans l'autre
- Ajuster la couleur des rames en fonction du cas de figure
- Ajuster la couleur du conducteur en fonction du cas de figure
- Envisager de remplacer nos listes de gare/quai par des Hashmap, qui évite ainsi de perdre du temps et de la lisibilité à parcourir toutes nos listes avec des boucles for imbriquées.
- Envisager de remplacer certaines classes de données super-flux par un/des tuples, avec le mot clé record (conteneurConducteur...)
- 