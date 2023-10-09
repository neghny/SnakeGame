# Projet 

### Membres du projet : 
* GHANNAY Nesrine 
* LEFEVRE Julien
* GOUMAIDI Kawthar
* DI SALVO-CILIA Pauline


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