package br.iteratorsystems.cps.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.iteratorsystems.cps.interfaces.EntityAble;

/**
 * PrecoId generated by hbm2java
 */
@Embeddable
public class Tabelas_PrecoId  implements java.io.Serializable, EntityAble {

	 private static final long serialVersionUID = -884623258394820100L;
	 private Integer idRede;
     private Integer idLoja;
     private String codigoBarras;

    public Tabelas_PrecoId() {
    }

    public Tabelas_PrecoId(Integer idRede, Integer idLoja, String codigoBarras) {
       this.idRede = idRede;
       this.idLoja = idLoja;
       this.codigoBarras = codigoBarras;
    }
   

    @Column(name="id_rede", nullable=false)
    public Integer getIdRede() {
        return this.idRede;
    }
    
    public void setIdRede(Integer idRede) {
        this.idRede = idRede;
    }

    @Column(name="id_loja", nullable=false)
    public Integer getIdLoja() {
        return this.idLoja;
    }
    
    public void setIdLoja(Integer idLoja) {
        this.idLoja = idLoja;
    }

    @Column(name="codigo_barras", nullable=false, length=20)
    public String getCodigoBarras() {
        return this.codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Tabelas_PrecoId) ) return false;
		 Tabelas_PrecoId castOther = ( Tabelas_PrecoId ) other; 
         
		 return (this.getIdRede()==castOther.getIdRede())
 && (this.getIdLoja()==castOther.getIdLoja())
 && ( (this.getCodigoBarras()==castOther.getCodigoBarras()) || ( this.getCodigoBarras()!=null && castOther.getCodigoBarras()!=null && this.getCodigoBarras().equals(castOther.getCodigoBarras()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdRede();
         result = 37 * result + this.getIdLoja();
         result = 37 * result + ( getCodigoBarras() == null ? 0 : this.getCodigoBarras().hashCode() );
         return result;
   }

@Override
public String toString() {
	return "Tabelas_PrecoId [codigoBarras=" + codigoBarras + ", idLoja=" + idLoja
			+ ", idRede=" + idRede + "]";
}   
   
   
}