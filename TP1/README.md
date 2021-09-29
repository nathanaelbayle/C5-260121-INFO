# TP 1 : XML & DTD

## Environnement de travail
Pour manipuler les documents XML (ainsi que les DTD et les schémas RELAX NG) nous utiliserons l’éditeur Exchanger XML disponible dans le dossier « Applications » sous Windows. À noter que pour les personnes souhaitant installer un éditeur sur leur poste il est disponible à l’adresse suivante : https://code.google.com/p/exchangerxml/.

## Notion de document bien formé

 Le document XML ci-dessous n’est pas bien formé. Sans utiliser d’outil, indiquer la position (numéro de ligne) et la nature de chaque erreur de syntaxe :

````xml
<?xml version="1.0" ?>
<!-- Annuaire d’illustration -->
<annuaire>
  <!-- ERREUR 1 : le commentaire ne peut pas etre imbriqu’e dans une balise-->
  <personne> <!-- une 1ere personne -->
    <nom>Dupont</nom>
    <!-- ERREUR 2 : le debut d’un commentaire est comporte 2 tirets -->
    <!-- ERREUR 3 : la balise fermante doit porter le meme nom (accent) -->
    <prenom>Paul <!-- ou Polo --> </prenom>
    <!-- ERREUR 4 : oubli de la balise fermante (date) -->
    <!-- ERREUR 5 : une chaine se termine par le meme delimiteur utilis’e au debut (fr-fr) -->
    <!-- ERREUR 6 : un attribut, pour un elt donn’e, n’existe qu’une seule fois ("ISO")-->
    <date format="fr-fr">1998-07-23 </date>
    <telephone/>
  </personne>
  <personne> <!-- une 2nd personne -->
    <nom>Alain</nom>
    <prenom courant=’Vrai’>Adrien</prenom>
    <!-- ERREUR 7 : la valeur d’un attribut doit etre entre " ou ’ (ISO)-->
    <date format=’ISO’>1969-06-04</date>
    <telephone>
      <!-- ERREUR 8 : pas d’espace dans les noms d’elt (indicatif tel) -->
      <indicatif_tel> 02 </indicatif_tel>
      <!-- ERREUR 9 : caractere # non autoris’e dans un nom d’elt (num#tel) -->
      <num_tel> 40 40 36 18 </num_tel>
      <!-- ERREUR 10 : les balises fermantes doivent etre appair’ees correctement -->
      <!-- inversion (personne <-> telephone -->
    </telephone>
  </personne>
  <!-- ERREUR 11 : un seule racine (2 racines <annuaire>) -->
  <personne>
    <nom>DUPONT</nom>
    <prenom>MARC</prenom>
    <telephone/>
  </personne>
</annuaire>
````

<br />

## DTD et validité de documents XML

Indiquer pour quelles raisons les fichiers XML suivants sont valides ou non. Justifier votre réponse.

##### Question 1

*erreur* : la racine du document doit être `tutorial`.

````xml
<?xml version="1.0"?>
<!DOCTYPE tutorial SYSTEM "tutorial.dtd">

<text>This is an XML document</text>
````

##### Question 2

Soit l'extrait de la DTD tutorial :

````dtd
<!ELEMENT tutorial (XXX)>
<!ELEMENT XXX (AAA , BBB)>
<!ELEMENT AAA (#PCDATA)>
<!ELEMENT BBB (#PCDATA)>
````

et les fichiers :
````xml
<?xml version="1.0"?>
<!DOCTYPE tutorial SYSTEM "tutorial.dtd">
<tutorial>
  <XXX> <AAA>Start</AAA> <BBB>End</BBB> </XXX>
</tutorial>
````

````xml
<?xml version="1.0"?>
<!DOCTYPE tutorial SYSTEM "tutorial.dtd">

<tutorial>
  <XXX> <AAA/> <BBB/> <BBB/> </XXX>
</tutorial>
````
Le premier est ok, mais le seconf est faux : l'élément XXX ne peut contenir qu'un seul élément BBB.


##### Question 3

Soit l’extrait de la DTD tutorial :

````xml
<!ELEMENT tutorial (XXX)>
<!ELEMENT XXX (AAA? , BBB+)>
<!ELEMENT AAA (CCC? , DDD*)>
<!ELEMENT BBB (CCC , DDD)>
<!ELEMENT CCC (#PCDATA)>
<!ELEMENT DDD (#PCDATA)>
````

Et les fichiers :

````xml
<?xml version="1.0"?>
<!DOCTYPE tutorial SYSTEM "tutorial.dtd">

<tutorial>
  <XXX>
    <AAA/> <BBB> <CCC/> <DDD/> </BBB>
  </XXX>
</tutorial>
````

````xml
<?xml version="1.0"?>
<!DOCTYPE tutorial SYSTEM "tutorial.dtd">

<tutorial>
  <XXX>
    <AAA>
      <CCC/><CCC/>
      <DDD/><DDD/>
    </AAA>
  <BBB>
    <CCC/><DDD/>
  </BBB>
  </XXX>
</tutorial>
````

Le premier est okn mais le second est faux : l'élément AAA ne peut contenir au plus qu'un seul éléménet CCC.

##### Question 4

Soit l’extrait de la DTD tutorial :

````xml
<!ELEMENT tutorial (XXX)>
<!ELEMENT XXX (AAA+ , BBB+)>
<!ELEMENT AAA (BBB | CCC )>
<!ELEMENT BBB (#PCDATA | CCC )*>
<!ELEMENT CCC (#PCDATA)>
````

Et le fichier :

````xml
<?xml version="1.0"?>
<!DOCTYPE tutorial SYSTEM "tutorial.dtd">

<tutorial>
  <XXX>
   <AAA>
    <CCC>Un seul élément</CCC>
   </AAA>
  <AAA>
    <BBB>
      <CCC/>
      <CCC/>
      <CCC/>
    </BBB>
  </AAA>
  <BBB/>
  <BBB>
    C’est <CCC/> une combinaison d’éléments <CCC> CCC </CCC>
  </BBB>
  <BBB>
    Text only.
  </BBB>
 </XXX>
</tutorial>
````

Le document est ok. À noter que dna sla définition de BBB l'ordre est important : `#PCDATA` doit apparaître avant CCC.

## Création d'une DTD

On souhaite créer un document XML représentant une liste de commandes. Une liste de commandes doit contenir une ou plusieurs commandes. Une commande est constituée d’une ou plusieurs lignes de commandes (référençant un seul produit) et peut possèder une remise. De plus, chaque commande possède un identifiant propre, l’identifiant du client qu’il l’a passée et son montant total qui s’il n’est pas fourni pourra être calculé par l’application traitant le fichier XML. Une ligne de commande est constituée de l’identifiant de l’article concerné, de la quantité commandée et du prix unitaire de cet article.

````xml
<!ELEMENT commandes (commande+)>
<!ELEMENT commande (ligne_commande+, remise?)>

<!ELEMENT ligne_commande EMPTY>
<!ELEMENT remise EMPTY>

<!ATTLIST commande id ID #REQUIRED>
<!ATTLIST commande id_client IDREF #REQUIRED>
<!ATTLIST commande montant CDATA #IMPLIED>

<!ATTLIST ligne_commande 
            ref_article IDERF #REQUIRED
            qantite CDATA #REQUIRED
            prix CDATA #REQUIRED
            >

<!ATTLIST remise valeur CDATA #REQUIRED>
````

## Écriture d’une DTD à partir d’un document existant

À partir du document suivant, et sans utiliser le type ANY, écrire une DTD permettant de valider le document :

````xml
<?xml version="1.0" standalone="no"?>
<!DOCTYPE musique SYSTEM "musique.dtd">
<musique>
    <cd>
        <titre>It's a wonderful life</titre>
        <groupe>Sparklehorse</groupe>
        <chanson>Eyepennies</chanson>
        <chanson>Dog door</chanson>
        <chanson/>
    </cd>
    <cd>
        <titre>BOF le fabuleux destin ... </titre>
        <artiste>
            <nom>Tiersen</nom>
            <prenom>Yann</prenom>
        </artiste>
        <chanson>A quai</chanson>
        <chanson duree='3:40'>Comptine d'un autre été</chanson>
    </cd>
</musique>
````

````xml
<!ELEMENT musique (cd*)>
<!ELEMENT cd (titre, artiste?, groupe?, chanson*)>
<!ELEMENT titre (#PCDATA)>
<!ELEMENT groupe (#PCDATA)>
<!ELEMENT artiste (nom, prenom)>
<!ELEMENT nom (#PCDATA)>
<!ELEMENT prenom (#PCDATA)>
<!ELEMENT chanson (#PCDATA)>
<!ATTLIST chanson duree CDATA #IMPLIED>
````














