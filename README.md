# <span style="color: #086F61"> Projet Génie Logiciel : Game Engine

#### <span style="color: #086F61"> Membres de l'équipe 35 :

* <span style="color: #85BDB5"> COMTI Mattéo
* <span style="color: #85BDB5"> DIALLO Mamadou Sellou
* <span style="color: #85BDB5"> DI SALVO-CILIA Pauline
* <span style="color: #85BDB5"> GHANNAY Nesrine
* <span style="color: #85BDB5"> LEFEVRE Julien
* <span style="color: #85BDB5"> GOUMAIDI Kawthar

## <span style="color: #086F61"> Objectifs

Dans le domaine exigeant du développement de jeux vidéo, la création d'un logiciel de qualité représente un défi de taille,
amplifié par les contraintes temporelles et les évolutions fréquentes des exigences. Ce projet s'inscrit dans cette réalité
en visant la conception d'un prototype de jeu vidéo, offrant ainsi une simulation authentique des conditions industrielles.
Dans ce contexte, l'application de méthodes et techniques avancées du génie logiciel devient impérative pour relever les
nombreux défis inhérents à ce domaine dynamique.

## <span style="color: #086F61"> Implémentation

#### <span style="color: #086F61"> Moteur Physique

Les méthodes du moteur physique reposent principalement sur des bibliothèques disponibles en ligne. Leur objectif 
principal est de définir les formes qui, une fois établies, seront ensuite visuellement représentées dans le moteur graphique pour créer les différents objets du jeu.

#### <span style="color: #086F61"> Moteur Graphique</span>

Il est responsable de l'affichage, de l'animation, et de la gestion d'une scène de jeu vidéo qui est organisée dans une 
structure spatiale.

#### <span style="color: #086F61"> Core Kernel</span>
 
Le Core Kernel assure la synchronisation des modules moteurs, qu'ils soient séquentiels ou parallèles, en fonction des événements 
provenant de la couche gameplay et des sorties des moteurs.  \
Les classes qui vont ici alimenter le core kernel sont :

#### <span style="color: #85BDB5"> Objet : 
La classe `Objet` est conçue pour gérer les aspects physiques et graphiques des objets du jeu. Elle encapsule les fonctionnalités suivantes :

  * Propriétés Physiques :  \
Coordonnées (x, y) de l'objet.  \
Vitesses horizontale et verticale.  \
Forme physique de l'objet (implémentée par l'interface IForme).
    </span>

* Propriétés Graphiques :  \
Chargement d'une image à partir d'un chemin spécifié.  \
Redimensionnement de l'image pour l'adapter à une taille définie.  \
Affichage graphique de l'objet dans l'interface utilisateur. 
</span>

* Mouvement :  \
  Mise à jour de la position de l'objet en fonction de sa vitesse.
  </span>

* Détection de Collision :  \
Méthode "percute(Objet other)" pour déterminer si l'objet entre en collision avec un autre objet.
  </span>

* Fonctions Auxiliaires :  \
  Méthodes pour obtenir les positions, tailles, et vitesses de l'objet.  \
   Calcul de la vitesse basé sur la distance de Manhattan.
  </span>

* Affichage Graphique :  \
  Surcharge de la méthode "paintComponent(Graphics g)" pour l'affichage graphique.
  </span>


 La classe "Objet" peut être instanciée en fournissant des paramètres tels que les coordonnées initiales, la forme 
 physique, le chemin de l'image, et les dimensions souhaitées. Elle peut ensuite être intégrée dans l'interface utilisateur
 graphique du jeu pour représenter divers éléments interactifs.
  </span>
 Remarques :

    * La classe offre une méthode "updatePosition()" pour actualiser la position de l'objet en fonction de ses vitesses.
    * Une méthode de collision "percute(Objet other)" permet de vérifier les collisions entre objets.

#### <span style="color: #85BDB5"> KeyListenerKernel :

La classe "KeyListenerKernel" gère les événements liés au clavier et à la souris dans le contexte du noyau du jeu. 
Cette classe est conçue pour intercepter les actions liées aux touches du clavier et aux clics de souris, offrant une 
gestion réactive des événements de l'utilisateur. Voici une vue d'ensemble des fonctionnalités et de l'utilisation de 
cette classe :

* Singleton Pattern :  \
La classe `KeyListenerKernel` suit le modèle de conception Singleton, garantissant une seule instance de la classe dans 
l'application. L'instance unique est accessible via la méthode statique getInstance().
```
KeyListenerKernel keyListener = KeyListenerKernel.getInstance();
```

* Gestion des Touches du Clavier :
  + La méthode `keyPressed(KeyEvent e)` est appelée lorsqu'une touche du clavier est enfoncée. Elle délègue le changement de 
  direction à la classe "Gameplay" en fonction de la touche pressée.
  + La méthode `keyReleased(KeyEvent e)` est appelée lorsqu'une touche du clavier est relâchée. Si la touche relâchée est la 
  touche "Escape" (VK_ESCAPE), la méthode exitGame() de la classe "Control" est appelée pour quitter le jeu.

* Gestion des Clics de Souris :
  + Les méthodes de l'interface `MouseListener` sont implémentées pour détecter les événements de la souris.
  + La méthode `mouseClicked(MouseEvent mouseEvent)` est utilisée pour identifier l'objet graphique cliqué. 
  En fonction de l'objet cliqué, différentes actions sont déclenchées, telles que le démarrage du jeu, l'affichage des
  instructions, le retour au menu principal, etc.

#### <span style="color: #85BDB5"> Score :

La classe "Score" encapsule les informations associées à un score dans le jeu. Chaque objet "Score" est composé d'un 
pseudo (nom du joueur) et d'un score entier.

Remarques :

    Les scores sont comparés pour déterminer leur position relative.
    Cette classe sont utilisée dans des contextes tels que la gestion des meilleurs scores ou la création d'un tableau de classement.

#### <span style="color: #85BDB5"> Classe Gameplay

La classe `Gameplay` est un composant clé du moteur de jeu, gérant la logique du gameplay, les interactions utilisateur et le système de score.
Elle orchestre les principales fonctionnalités du jeu, notamment le mouvement du serpent, la détection des collisions, le scoring et les interactions utilisateur. Elle utilise la classe `MoteurGraphique` pour le rendu graphique et les interactions.

* Principales fonctionnalités

- **Interface utilisateur :** Gère le menu principal, le menu des options et l'affichage du classement.
- **Logique de jeu :** Gère le mouvement du serpent, la détection des collisions et le scoring.
- **Interaction utilisateur :** Capture les entrées utilisateur via le clavier et la souris.
- **Classement :** Affiche et met à jour les cinq meilleurs scores.
- **Configuration :** Permet aux utilisateurs de personnaliser les paramètres du jeu, tels que le niveau de difficulté et la couleur du serpent.

- **Utilisation :**

Instanciez la classe `Gameplay` en utilisant `Gameplay.getInstance()` et utilisez ses méthodes pour contrôler le déroulement du jeu.

```java
Gameplay jeu = Gameplay.getInstance();
jeu.showMainMenu();
````


#### <span style="color: #85BDB5"> Control

La classe `Control` est le composant central du contrôle-commande du jeu vidéo. Elle agit comme un orchestrateur entre les différentes parties du jeu, en particulier la logique de jeu (classe `Gameplay`) et le moteur graphique (classe `MoteurGraphique`). Sa responsabilité principale est de faire fonctionner le jeu en coordonnant les interactions entre ces composants.

- **Boucle de jeu :** La méthode `run` gère la boucle principale du jeu. À chaque itération de la boucle, elle s'occupe du mouvement du serpent, de la gestion des collisions, et de l'affichage des objets. La durée de chaque itération est contrôlée par la variable `frameLength`.

- **Initialisation :** La classe `Control` est responsable de l'initialisation des instances de `Gameplay` et `MoteurGraphique` nécessaires au fonctionnement du jeu.

- **Sortie du jeu :** La méthode `exitGame` permet de mettre fin à la boucle de jeu, arrêtant ainsi l'exécution du programme.

- **Utilisation :**

Pour utiliser la classe `Control`, instanciez-la en utilisant `Control.getInstance()` et appelez la méthode `run` pour démarrer la boucle de jeu.

```java
Control control = Control.getInstance();
control.run();
```

# <span style="color: #086F61"> Gradle

Modèle basique de projet gradle pour interface graphique avec `swing`.

Les commandes gradle les plus utiles :

- `gradle test` pour lancer les tests,
- `gradle run` pour lancer le programme,
- `gradle jar` pour construire un `jar` dans `build/libs`.
- `gradle checkStyleMain` pour vérifier le style du code principal avec l'outil [checkstyle](https://checkstyle.sourceforge.io/) (rapports dans `build/reports/checkstyle/`).
- `gradle checkStyleTest` pour vérifier le style du code de test avec l'outil [checkstyle](https://checkstyle.sourceforge.io/) (rapports dans `build/reports/checkstyle/`).
- `gradle jacocoTestReport` pour lancer la couverture de code via l'outil [Jacoco](https://www.eclemma.org/jacoco/) (rapports dans `build/reports/jacoco/`). 
- `gradle spotbugsMain` pour vérifier la présence de bugs dans le code principal avec l'outil [SpotBugs](https://spotbugs.github.io/) (rapports dans `reports/spotbugs/main/spotbugs.html`).
- `gradle spotbugsTest` pour vérifier la présence de bugs dans le code de test avec l'outil [SpotBugs](https://spotbugs.github.io/) (rapports dans `reports/spotbugs/test/spotbugs.html`).
- `gradle pmdMain` pour faire l'analyse statique du code principal avec l'outil [PMD](https://pmd.github.io/) (rapports dans `reports/pmd`).
- `gradle pmdTest` pour faire l'analyse statique du code de test avec l'outil [PMD](https://pmd.github.io/) (rapports dans `reports/pmd`).

Le fichier `build.gradle` contient la configuration du projet avec notamment la définition de la classe contenant la méthode `main` à exécuter pour l'application.

Le projet est configuré (via le fichier `.gitlab-ci.yml`) pour produire un jar et lancer les tests sur le serveur à chaque *push*.

## Copyrights

Image provenant de [Larry Ewing](lewing@isc.tamu.edu) and The GIMP