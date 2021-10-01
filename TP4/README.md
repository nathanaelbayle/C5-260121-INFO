## Expressions XPath

Une expression XPath permet d’accèder aux éléments et aux attributs d’un document XML (représenté ici par un arbre DOM) de manière similaire qu’en spécifiant un chemin d’accès à des fichiers dans un système de fichiers. À partir du fichier commandes.xml donner les expressions XPath permettant :

1. d’obtenir toutes les commandes.
2. d’obtenir toutes les commandes passées au magasin de Dompierre.
3. d’obtenir les numéros de toutes les commandes passées au magasin de Dompierre.
4. donner le nom du client ayant le numéro 1.
5. d’obtenir les 2 dernières commandes (cela doit fonctionner sur d’autres fichiers de commandes respectant la même structure).
6. donner le nom du client de la commande 1.

````xml
<?xml version="1.0" encoding="UTF-8"?>
<!--FICHIERCOMMANDE.XML-->
<commandes_et_clients>
    <commande nocde="2">
        <datecde>2004-06-12</datecde>
        <magasin>LaRochelle</magasin>
        <noduclient>2</noduclient>
    </commande>
    <commande nocde="1">
        <datecde>2004-07-12</datecde>
        <magasin>Dompierre</magasin>
        <noduclient>1</noduclient>
    </commande>
    <commande nocde="3">
        <datecde>2004-07-12</datecde>
        <magasin>Dompierre</magasin>
        <noduclient>1</noduclient>
    </commande>
    <commande nocde="4">
        <datecde>2004-08-12</datecde>
        <magasin>Dompierre</magasin>
        <noduclient>1</noduclient>
    </commande>
    <commande nocde="5">
        <datecde>2004-07-12</datecde>
        <magasin>Dompierre</magasin>
        <noduclient>3</noduclient>
    </commande>
    <clients>
        <Nom>dupont</Nom>
        <Prenom>valérie</Prenom>
        <Type>PME-PMI</Type>
        <noclient>1</noclient>
    </clients>
    <clients>
        <Nom>valere</Nom>
        <Prenom>eric</Prenom>
        <Type>GRANDCOMPTE</Type>
        <noclient>2</noclient>
    </clients>
    <clients>
        <Nom>hector</Nom>
        <Prenom>henri</Prenom>
        <Type>PARTICULIER</Type>
        <noclient>3</noclient>
    </clients>
    <clients>
        <Nom>pierre</Nom>
        <Prenom>désiré</Prenom>
        <Type>GRANDCOMPTE</Type>
        <noclient>4</noclient>
    </clients>
    <clients>
        <Nom>boudaud</Nom>
        <Prenom>léon</Prenom>
        <Type>PME-PMI</Type>
        <noclient>5</noclient>
    </clients>
</commandes_et_clients>
````

### Solutions 

1. `//commande`
2. `//commande[magasin/text()="Dompierre"]`
3. `//commande[magasin/text()="Dompierre"]/@nocde`
4. `//clients[noclient="1"]/Nom/text()`
5. `//commande[position()=(last()-1) or position()=last()]`
6. `//clients[noclient=//commande[@nocde="1"]/noduclient]/Nom/text()`

## Utilisation de l’API Java XML Bindings















