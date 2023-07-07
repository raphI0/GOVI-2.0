# GOVI-2.0

 repository github du code : https://github.com/raphI0/GOVI-2.0.git

 Cette version du GOVI succède à une version basée sur les principes de la programmation fonctionnelle
 Réalisé dans le langage interpréteur python. Il en eu existé 4 versions :  
 - V1.0 : Première version mise en production à la suite de deux ans et demi d'alternance
 - V1.1 : Première amélioration de la version initiale. Création et refonte de l'IHM, migration des bibliothèques, debug des problèmes critiques et ajouts rapides
 - V1.2 : Cette version inclue essentiellement l'ajout des garages L2 sur le GOVI
 - V1.3 : Ajout de fonctionnalités utiles pour le responsable pré-op (verification V1/V2 Mitry) et modif de l'IHM pour une meilleure gestion des erreurs
 - V1.4 : Peaufinage et améliorations mineures liées à des erreurs rencontrées en production. 

 Cette nouvelle édition du logiciel de génération des GOVIs a pour objectif d'apprendre des erreurs 
 de conception de son grand frère, pour mieux devenir le logiciel répondant aux besoins de toutes et tous.  
 Parmi les progrès majeurs visés, mais restant néanmoins des objectifs haut niveau, nous avons :  
 - Amélioration de la maintenabilité et la relecture global
 - La modularité du code
 - La réduction du temps de génération des GOVI
 - Faciliter l'adaptabilité du logiciel aux besoins opérationnels
 - Faciliter l'utilisation du logiciel pour toutes et tous (techno front)
 - ...

 Cette liste non exhaustive, viens nous permettre d'exposer et expliquer le choix des technologies utilisées 
 pour le développement.  
 L'architecture globale du projet est basée sur une architecture MVC, elle-même composée en couche aussi dit n-Tiers. 
 Dans le cas présent, le model MVC est ici représentée par :  
 - le MODEL, incarné par le back-end, codé en orienté objet via JAVA Spring boot.
 - la VIEW, incarné par le front-end, basé sur la techno Web ANGULAR, écrit donc en TypeScript, ainsi que HTML et CSS.
 - les CONTROLLERS, incarnés ici par les appelle d'API entre back et front, basé sur les requêtes http REST.
Pour notre View et surtout notre model, donc notre back et notre front, on retrouve une structure interne basée sur 
le système de couches n-Tiers évoqués, essentiellement composé ici de 4 couches :
 - La couche de présentation (IHM/UI), ici représenté par l'arrivée de l'appel d'API du front (dossier controller)
 - La couche de service (avec la logique métier, opérant aux calculs), ici, c'est notre dossier service
 - la couche de persistance (d'accès aux données) ici non directement représenté puisque nécessiterait 
une base de donnés SQL, no SQL ou autre à laquelle accéder
 - la couche model, transversale à toutes nos couches qui gère les models de données avec lesquels travaille notre logiciel (sous répertoire model dans chaque couche)  
 Cet état de la structure est simplifié et permet de comprendre la répartition des tâches au sein de l'application, mais reste sujet à évolution
 ou modifications localisées. Exemple avec le dossier configuration (parallèle à controller et service) qui vient ici extraire les informations
 statiques de notre fichier de configuration en .yml, que l'on fournit à notre application.
 Cela pourrait presque s'apparenter à une couche persistance, mais de manière bien plus primitive

Les conventions utilisées au sein du code et à respecter pour poursuivre sa lisibilité et la maintenabilité. 
On y retrouve notamment le camelCase, où les majuscules au début des noms sont réservées pour le nommage des classes uniquement.
Dans le but de produire un code propre, il est conseillé de developer avec l'IDE IntelliJ (gratuit pour les étudiants) et 
d'y ajouter le plugin Sonar Lint, capable d'analyser le code et cibler toutes les erreurs, de tous types et catégories.
L'objectif est de ne plus avoir d'erreurs SonarLint ou de l'IDE lui-même, toutes catégories confondus (Typo, Warning, Error...)
A noter qu'il est possible de télécharger le dictionnaire français sur IntelliJ pour éviter les fausses erreurs Typo liées à la différence de langue.

Pour ouvrir le projet, il est conseillé de se créer un repository local, si besoin aidé de GitDesktop, un excellent logiciel 
qui facilite l'utilisation de Git et Github. À noter qu'IntelliJ intègre aussi nativement la gestion de Git, qu'il est simple d'utiliser une fois tout en place.
Une fois le repo installé sur la machine, faire click droit sur le dossier projet, ici nommé 'GOVI-2.0', et faire 'ouvrir en tant que projet Intellij'.