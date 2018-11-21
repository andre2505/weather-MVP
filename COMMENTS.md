# Commentaires personnels

N'hésitez pas à discuter de vos choix techniques ou architecturaux ici.

Choix technologies pour le dévelppement de l'application :

- Retrofit2: permet de créer un connexion serveur en mappant les models de données avec GSON. Retrofit permet une mise en place rapide pour communiquer avec le serveur
et tout ce qui concerne la connexion.

- RxJava2: programmation dynamique qui se focalise sur la logique Observer/Observable afin de regarder l'objet demandé, arrive correctement jusqu'à l'application.
Rxjava a aussi des méthodes beaucoup plus étendues mais dans le cadre de ce projet je me suis limité à une utilisation stricte dans le cadre d'une clean architecture de type MVP afin de servir la vue selon l'interaction au serveur.


Type d'architecture:

- Clean architecture MVP (Model View Controller) -> j'ai utilisé cette architecture pour découpler la couche vue et donnée afin de mieux organiser mon code et d'implémenter les features rapidement
  de plus cela rend le code plus maintenable et durable et ainsi évite les erreurs de code sale.

- Data-binding google : Dans le cadre de cette application, j'ai utilisé le binding google afin de d'afficher les valeurs des models sans passer par le code java qui multiplie les lignes dans l'activité en castant chaque objet de la vue et peut mener à des erreurs.





