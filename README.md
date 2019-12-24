# SQLITE-Editor

SqliteEditor , est une application Android permettant la gestion de base de données SQLITE.
Notre objectif est de créer une application qui aidera les utilisateurs au sein d’une entreprise ; le but est de mettre à leur disposition une interface permettant une manipulation de base des bases de données.
Les fonctions principales de cette application sont : la création d’objets (bases de données, tables, lignes), la suppression d’objets (lignes, tables) en plus d’une couche d’authentification permettant de faire de distinguer deux types d’utilisateurs (Administrateur et utilisateur normal).

Fonctionnement de l’application

Notre application repose sur des bases de données internes permettant le stockage des utilisateurs, et des tables créés.
L’interface de démarrage comprend un formulaire de connexion permettant à chaque utilisateur l’authentification pour accéder à la vue qui lui est consacrée.
Il existe deux types de vue selon le type d’utilisateur : « Administrateur » ou « User ».
 
La vue « Administrateur » permet la gestion et recherche des utilisateurs ayant un compte sur l’application, alors que la vue « User » permet à travers une interface la création des base de données, des tables, l’exécution des requêtes SQL et l’export des base de données.
