# Bienvenue dans notre projet !

-Avec un joli circuit, on aura sûrement la 16ᵉ Ligue des Champions (dommage, pas cette année... mais on y croit pour l’année prochaine !!)

# Environnement et compilation et test

## Environnement

- Système utilisé : `Linux`.

## Compilation

- Cloner le projet.
- Télécharger la bibliothèque [JavaFX 23.0.2 ](https://download2.gluonhq.com/openjfx/23.0.2/openjfx-23.0.2_linux-x64_bin-sdk.zip) x64 SDK.
- Extraire l’archive ZIP téléchargée dans le dossier où vous avez cloné le projet.
- Exécuter la commande `make`.
- C'est bon!! Construisez votre circuit.

## Test

-Si vous voulez exécuter les classes de test, commencez par télécharger les bibliothèques nécessaires avec ces deux commandes : `wget https://repo1.maven.org/maven2/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar -P lib/` et `wget https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar -P lib/`

-Exécuter la commande `make test`.
