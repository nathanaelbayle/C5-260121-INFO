# Data Base and XML


## Table des Matières

##### TODO

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

## Utilisation

Pour appliquer un document **DTD** sur un fichier **XML**, il faut utiliser `xmllint` de la manière suivante :

````bash
xmllint document.xml --dtdvalid document.dtd --noout
````

Pour appliquer un document **XML Schéma** sur un fichier **XML**, il faut utiliser `xmllint` de la manière suivante :

````bach
xmllint --schema document.xsd document.xml --noout
````

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
<exemple> contenu </exemple>
````

Balise avec attribut :

````xml
<exemple attr="value"> </exemple>
````

#### ELEMENT

| Syntaxe          | `<!ELEMENT balise (contenu)>`   |
| ------------- |-------------|
| Personne contient **1** nom    | `<!ELEMENT personne (nom)>` |
| nom contient une **valeur simple** | `<!ELEMENT nom (#PCDATA)>` |
| nom **contient ou non une valeur** | `<!ELEMENT nom EMPTY>`      |
| personne contient 1 nom **et** 1 prenom | `<!ELEMENT personne (nom, prenom)>`      |
| personne contient 1 nom **ou** 1 prenom | ``<!ELEMENT personne (nom \| prenom)>``   |
| personne contient 1 nom **et eventuellement** 1 prenom | `<!ELEMENT personne (nom, prenom?)>`      |
| repertoire contient **de 0 à n** personne | `<!ELEMENT repertoire (personne*)>`      |
| repertoire contient **de 1 à n** personne  | `<!ELEMENT repertoire (personne+)>`     |

### Attributes

In a DTD, **attributes** are declared with an *ATTLIST* declaration. 

````dtd
<!ATTLIST element-name attribute-name attribute-type attribute-value>
````

&nbsp;

The **attribute-type** can be one of the following:

| Type          | Description   |
| ------------- |-------------|
| CDATA    | The value is character data |
| (en1|en2|..) | The value must be one from an enumerated list |
| ID | The value is a unique id |
| IDREF | The value is the id of another element |
| IDREFS | The value is a list of other ids |
| NMTOKEN | The value is a valid XML name |
| NMTOKENS | The value is a list of valid XML names |
| ENTITY | The value is an entity |
| ENTITIES | The value is a list of entities |
| NOTATION | The value is a name of a notation |

&nbsp;

The **attribute-value** can be one of the following:

| Type          | Explanation   | Example |
| ------------- |-------------|-------------|
| *value* | The default value of the attribute | `<!ATTLIST square width CDATA "0">` |
| #REQUIRED | The attribute is required | `<!ATTLIST person number CDATA #REQUIRED>` |
| #IMPLIED | The attribute is optional | `<!ATTLIST contact fax CDATA #IMPLIED>` |
| #FIXED *value* | The attribute value is fixed | `<!ATTLIST sender company CDATA #FIXED "Microsoft">` |

&nbsp;

You can use **enumerated** attribute values when you want the attribute value to be one of a fixed set of legal values.

````dtd
<!ATTLIST element-name attribute-name (en1|en2|..) default-value>
````




&nbsp;


## XML Schema

An *XML Schema* describes the structure of an *XML* document. The `<schema>` element is the root element of **every** XML Schema.

#### XSD Example
````xml
<?xml version="1.0"?>
<xs:schema xmlns:xsi="http://www.w3.org/2001/XMLSchema" targetNamespace="http://interoperabilnost.hr" xmlns="http://interoperabilnost.hr" elementFormDefault="qualified">
  <xs:element name="note">
    <xs:complexType>
      <xs:sequence>
	<xs:element name="to" type="xs:string"/>
	<xs:element name="from" type="xs:string"/>
      </xs:sequence>
    </xs:complexType>
  </xs:element>
</xs:schema> 
````

<br />

## Simple Elements

A simple element can contain only **simple types**. It cannot contain other elements or attributes. 

The syntax for defining a simple element is: 

````xml
<xs:element name="xs:xxx" type="xs:yyy"/>
```` 
The most common types are:
  - `xs:string`
  - `xs:decimal`
  - `xs:integer`
  - `xs:boolean`
  - `xs:date`
  - `xs:time`

Simple elements may have a default value OR a fixed value specified.

````xml
<xs:element name="color" type="xs:string" default="red"/>
````

````xml
<xs:element name="color" type="xs:string" fixed="red"/>
````
<br />

## Attributes

The syntax for defining an attribute is:

````xml
<xs:attribute name="xs:xxx" type="xs:yyy"/>
````

Attributes are optional by default. To specify that the attribute is required, use the "use" attribute:
````xml
<xs:attribute name="lang" type="xs:string" use="required"/>
````

Attributes may have a default value OR a fixed value specified:
````xml
<xs:attribute name="lang" type="xs:string" default="EN"/>
````

````xml
<xs:attribute name="lang" type="xs:string" fixed="EN"/>
````

## Restrictions 
To **limit** the content of an XML element to a set of acceptable values, we would use the **enumeration** constraint.

````xml
<xs:element name="car" type="carType"/>

<xs:simpleType name="carType">
  <xs:restriction base="xs:string">
    <xs:enumeration value="Audi"/>
    <xs:enumeration value="Golf"/>
    <xs:enumeration value="BMW"/>
  </xs:restriction>
</xs:simpleType>
````


<br/>
<br/>
## XML Facets

De nouveaux **type simple** peuvent être créés à partit des types prédéfinis en ajoutant des contraintes via un mécanisme appelé **facettes**.

  - `xs:minInclusive`
  - `xs:maxInclusive`
  - `xs:minExclusive`
  - `xs:maxExclusive`
  - `xs:enumeration`
  - `xs:pattern`
  - `xs:whiteSpace`
  - `xs:length`
  - `xs:minLength`
  - `xs:maxLength`
  - `xs:totalDigits`
  - `xs:fractionDigits`


&nbsp;
Déclaration d'un type de taile fixe :

````xml
<xsd:simpleType name="typeCodePostal">
    <xsd:restriction base="xsd:integer">
	<xsd:length value="5"/>
    </xsd:restriction>
</xsd:simpleType>
<xsd:element name="codePostal" type="typeCodePostal"/>


````




### Min-max

````xml
<xs:element name="age">
  <xs:simpleType>
    <xs:restriction base="xs:integer">
      <xs:minInclusive value="0"/>
      <xs:maxInclusive value="120"/>
    </xs:restriction>
  </xs:simpleType>
</xs:element> 
````

### Enumeration

````xml
<xs:element name="car" type="carType"/>

<xs:simpleType name="carType">
  <xs:restriction base="xs:string">
    <xs:enumeration value="Audi"/>
    <xs:enumeration value="Golf"/>
    <xs:enumeration value="BMW"/>
  </xs:restriction>
</xs:simpleType>
````

### Pattern

- regexp

````xml
<xs:restriction base="xs:string">
  <xs:pattern value="([a-z])+"/>
</xs:restriction>
````

### WhiteSpace
- preserve: keep whitespaces
- replace: replace whitespace characters with spaces
- collapse: collapse all whitespace characters to a single space

````xml
<xs:restriction base="xs:string">
  <xs:whiteSpace value="preserve"/>
</xs:restriction>
````

<br />

## Complex Elements

- empty elements
- elements that contain only other elements
- elements that contain only text
- elements that contain both other elements and text

### Complex Example
````xml
<xs:element name="employee" type="personinfo"/>
<xs:element name="student" type="personinfo"/>

<xs:complexType name="personinfo">
  <xs:sequence>
    <xs:element name="firstname" type="xs:string"/>
    <xs:element name="lastname" type="xs:string"/>
  </xs:sequence>
</xs:complexType>

<xs:element name="professor" type="fullpersoninfo">

<xs:complexType name="fullpersoninfo">
  <xs:complexContent>
    <xs:extension base="personinfo">
      <xs:sequence>
        <xs:element name="address" type="xs:string"/>
        <xs:element name="city" type="xs:string"/>
        <xs:element name="country" type="xs:string"/>
      </xs:sequence>
    </xs:extension>
  </xs:complexContent>
</xs:complexType> 
````

### Empty Element
````xml
<xs:complexType name="prodtype">
  <xs:attribute name="prodid" type="xs:positiveInteger"/>
</xs:complexType>
````

### Text only + attribute

````xml
<xs:complexType name="shoetype">
  <xs:simpleContent>
    <xs:extension base="xs:integer">
      <xs:attribute name="country" type="xs:string" />
    </xs:extension>
  </xs:simpleContent>
</xs:complexType>
````

### Integer only + Attirbute + attribute restriction

````xml
<xs:complexType>
  <xs:simpleContent>
    <xs:extension base="xs:integer">
      <xs:attribute name="unit">
        <xs:simpleType>
          <xs:restriction base="xs:string">
            <xs:length value="3" />
          </xs:restriction>
        </xs:simpleType>
      </xs:attribute>
    </xs:extension>
  </xs:simpleContent>
</xs:complexType>
````
<br />

## Element Indicators

- All - any order, only once
- Choice - order, at most once
- Sequence - order, only once

- minOccurs
- maxOccurs

<br />

## Data Types

### String

- `xs:string`

### Date

| Name          | Description   |
| ------------- |-------------:|
| xs:date      | YYYY-MM-DD |
| xs:dateTime     | YYYY-MM-DDThh:mm:ss      |
| xs:duration | PnYnMnDTnHnMnS |
| xs:gDay | DD      |
| xs:gMonth | MM      |
| xs:gMonthDay | MM-DD      |
| xs:gYear | YYYY      |
| xs:gYearMonth | YYYY-MM      |
| xs:time | hh:mm:ss      |


### Numeric

- `xs:byte`
- `xs:decimal`
- `xs:int`
- `xs:integer`
- `xs:long`
- `xs:negativeInteger`
- `xs:nonNegativeInteger`
- `xs:nonPositiveInteger`
- `xs:positiveInteger`
- `xs:short`
- `xs:unsignedLong`
- `xs:unsignedInt`
- `xs:unsignedShort`
- `xs:unsignedByte`
- `xs:float`
- `xs:double`

### Boolean

- `xs:boolean`














































