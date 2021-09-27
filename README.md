# Data Base and XML

## XML
### Définition eXtended Markup Langage

Un document Xml est composé de balises **balises**, elles mêmes possédant possédant des **attributs**. Il peut également contenir des **entités**, des **règles d'analyse**, de déclarations d'**espace de nom** (namespace), de **commentaires** et du **texte**.

### Prologue

Il faut **imperativement** inclure le prologue à la première ligne du fichier :
````xml
<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
````
### Règles de nommage balise et attibut

- Un nom contient des lettres, des chiffres ou des caractères spéciaux.
- Un nom ne débute pas par un nombre ou un caractère spécial
- Un nom ne contient pas d'espaces


## DTD

### Document Type Definition

La **DTD** peut être interne au document ou externe. Elle définit les règles devant être respectées dans le corps **XML**

### Conformité et validation

Un document **valide** est un document **bien formé conforme à une définition**. Un document **conforme** à une définition est un document qui **respecte toutes les règles** qui sont imposées dans la DTD.


### Contenu 

Commentaire :
````xml
<!-- commentaire -->
````

Balise simple :
````xml
<exemple/>
````

Balise par paire :
````xml
<exemple>contenu</exemple>
````

Balise avec attribut :
````xml
<exemple attr="value"></exemple>
````

### ELEMEENT

Syntaxe :
````xml
<!ELEMENT balise (contenu)>
````

personne contient 1 nom :
````xml
<!ELEMENT personne (nom)>
````

nom contient une **valeur simple**:
````xml
<!ELEMENT nom (#PCDATA)>
````

nom **contient ou non une valeur**:
````xml
<!ELEMENT nom EMPLT>
````

personne contient 1 nom **et** 1 prenom :
````xml
<!ELEMENT personne (nom, prenom)>
````

personne contient 1 nom **ou** 1 prenom :
````xml
<!ELEMENT personne (nom | prenom)>
````
personne contient 1 nom **et eventuellement** 1 prenom :
````xml
<!ELEMENT personne (nom, prenom?)>
````
repertoire contient **de 0 à n** personne :
````xml
<!ELEMENT repertoire (personne*)>
````

repertoire contient **de 1 à n** personne :
````xml
<!ELEMENT repertoire (personne+)>
````

















