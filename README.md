# GOVI-2.0
 GOVI POO, Front-end Web (Angular), Back-end Java (SpringBoot)

To do List :

Front:
-
- Ne pas afficher les W les gars RATP.  
- Ne pas afficher les trains de 0s.  
- Ne pas afficher les trains identiques à l'arrivée et au départ (sauf cas passage par voie Z notamment) 
- Verifier que le component GoviDisplayer accepte et affiche les code mission au format SAS (10SOSI au lieu de SOSI10 par exemple) 
- Afficher les trains sur Mitry voie GI même si 0s de retournement (Dégarage).
- Limiter le front à afficher jusqu'à 1H30 à J+1 (lui imposer de ne pas aller au delà)
- Afficher les trains de 00:01 à partir de l'heure de départ et non d'arrivée
- écourter les traits trop long pour les remplacer par des des petits au début et à la fin
- 

Back :
-
- Faire une méthode unique pour la création des retournements, qui permette de créer ceux de la RATP et du BHL SNCF  
- Pour la voie GI de Mitry, ajouter du temps de retournement et mettre QUE la mission de départ (Dégarage) dans le retournement. (Attention aux W à 0s globalement)
- Verifier pourquoi les quais avec ID 3 et 5, pour la gare de Mitry sont vides alors que le BHL ne l'est pas
- Créer 2 hashmap, en sorti de fonction recherche PS/FS, qui donne pour chaque conducteur sa mission PS dans une hashmap, sa FS dans l'autre
- 