//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.2 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2021.09.30 à 06:16:27 PM CEST 
//


package myfsXML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour FileSystem complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="FileSystem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://masterinfo.univlr.fr}directory"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileSystem", propOrder = {
    "directory"
})
public class FileSystem {

    @XmlElement(required = true)
    protected Directory directory;

    /**
     * Obtient la valeur de la propriété directory.
     * 
     * @return
     *     possible object is
     *     {@link Directory }
     *     
     */
    public Directory getDirectory() {
        return directory;
    }

    /**
     * Définit la valeur de la propriété directory.
     * 
     * @param value
     *     allowed object is
     *     {@link Directory }
     *     
     */
    public void setDirectory(Directory value) {
        this.directory = value;
    }

}
