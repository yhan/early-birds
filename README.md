# How to

## Launch unit tests

```sbt test```

## Launch raw Scala version

Put the input file in `data/input/xag.csv`

```
sbt runMain raw.EarlyBirds
```

## Launch Spark version

Put the input file in `data/input/xag.csv`

```
sbt runMain spark.EarlyBirds
```

# Implementation choices

Two implementations are provided.

- raw Scala (no need for distributed computing for a small dataset of 700M)
- Spark (to support bigger dataset)

It's an ETL use case, so the code is divided in three parts :

- Reader (the Extract part) 
- Calculator (the Tranform part) 
- Writer (the Load part) 

## Raw

- To lower memory consumption, the input file is not entirely mounted in memory.
- To reduce I/O, the file is read only two times.
- For performance reason, local mutability is used in Calculator
- Known limitation : Only one core/thread is used.

## Spark

- Can't use monotonically_increasing_id because generated ids are not consecutive
- built-in functions are used (instead of udf) for performance reasons

# Functional questions

- should we consider only the date part of the timestamp ?
- If yes, in which timezone ?

# Statement

```
On dispose d'un fichier CSV, selon le modèle suivant: 
input.csv : userId,itemId,rating,timestamp

On souhaite construire 3 CSV de la façon suivante: 

    aggratings.csv : userIdAsInteger,itemIdAsInteger,ratingSum 
    lookupuser.csv : userId,userIdAsInteger 
    lookup_product.csv : itemId,itemIdAsInteger


où: 

    userId : identifiant unique d'un utilisateur (String) 
    itemId : identifiant unique d'un produit (String) 
    rating : score (Float) 
    timestamp : timestamp unix, nombre de millisecondes écoulées depuis 1970-01-01 minuit GMT (Long/Int64) 
    userIdAsInteger : identifiant unique d'un utilisateur (Int) 
    itemIdAsInteger : identifiant unique d'un produit (Int) 
    ratingSum : Somme des ratings pour le couple utilisateur/produit (Float)


Ecrire un programme dans le langage de ton choix (idéalement Scala) respectant les contraintes suivantes: 

    dans agg_rating.csv, les couples utilisateur/produit sont uniques 
    Les userIdAsInteger (tout comme les productIdAsInteger) sont des entiers consécutifs, le premier indice étant 0. 
    une pénalité multiplicative de 0.95 est appliquée au rating pour chaque jour d'écart avec le timestamp maximal de input.csv 
    On ne souhaite conserver que les ratings > 0.01


Tu peux partager ton GitHub lorsque tu auras fini (ou nous envoyer tes sources).

Ton rendu sera évalué sur les critères suivants :

Efficacité :

    Est-ce que le test fonctionne ?
    Facilité d'installation / Explications
    Documentation (readme)

Forme :

    Propreté du code
    Qualité des commentaires
    Qualité des commits (nombre, commentaires...)

Architecture :

    Structuration globale
    Choix techniques (modules, framework,...)
    Utilisation de patterns connus/efficaces

Technique :

    Niveau de connaissance Java/Scala

Prospective :

    Capacité à progresser / Solidité des bases

Bonus (non demandé dans l'énnoncé) :

    Tests unitaires et qualité
```