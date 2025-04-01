/*
 * Client.java
 *
 * Created on 17 iunie 2004, 23:50
 */

package UML;
import java.util.*;

/**
 *
 * @author Alex
 */
//********************************
public class Client {
    public int codclient;
    public String numepren;
    public String email; 
    
    /** Creates a new instance of Client */
    public Client(int pCodclient,String pNumePren,String pEmail) {
        codclient=pCodclient;
        numepren=pNumePren;
        email=pEmail;
    }
  //*******************************
     public static void main(String[] args){
           Chitare ch1=new Acustica(1001,"Yamaha","Yrx-Y2",21,"Pal");
           Chitare ch2=new SemiAcustica(1002,"Gibson","Les-Paul",23,"Carbon",1,"Di Martio");
           Chitare ch3=new Electrica(1003,"Ibanez","RGX",26,5,"Sey Duncan","Floyd Royce");
           
           Componente compo1=new Componente(101,"Doza",2500000);
           Componente compo2=new Componente(102,"Grif",3500000);
           
           GradDefectiune grad1=new GradDefectiune(1,.15);
           GradDefectiune grad2=new GradDefectiune(2,.35);
           GradDefectiune grad3=new GradDefectiune(3,.60);
           
              
           Client c1=new PersoaneFizice(10000,"Alex","axl@gahoo.com","cnp 1","Adresa 1");
           Client c2=new PersoaneJuridice(10001,"Mihnea","mihnea_balan@feaa.uaic.ro",0100201236,"SEDIU 2",64654);          
           ArtRepar ar1=new ArtRepar(ch1,3,grad2,compo1,3,1);
           ArtRepar ar2=new ArtRepar(ch2,2,grad3,compo2,0.10);
           ArtRepar ar3=new ArtRepar(ch3,3,grad1,compo2,0.20);
           
           
           Reparatii r1=new Reparatii(1,null,c1);
           r1.addLinieRepar(ar2);
           r1.addLinieRepar(ar3);
           r1.calculValoareTotala();
          
           System.out.println("==========================================");
           System.out.println("Se repara un nr de "+(int)ar2.cantitate+" chitari : "+ch2.nume+" cu componenta "+compo2.numecompo+ " defecta de gradul "+grad3.graddef);
           System.out.println("Valoare Reparatie -->> "+(int)ar2.valoare+" cu discountul "+(int)ar2.valDiscount+"        "+((int)ar2.valoare-(int)ar2.valDiscount));
           System.out.println("-----------------------------------------");
           
           System.out.println("Se repara un nr de "+(int)ar3.cantitate+" chitari : "+ch3.nume+" cu componenta "+compo2.numecompo+ " defecta de gradul "+grad1.graddef);
           System.out.println("Valoare Reparatie -->> "+(int)ar3.valoare+" cu discountul "+(int)ar3.valDiscount+"        "+((int)ar3.valoare-(int)ar3.valDiscount));
           System.out.println("------------------------------------------");
           System.out.println("Clientului: "+c1.numepren+"  i se acorda Discount La Valoare || Valoarea Reparatiei -->> "+(int)r1.valoareTotala);
           System.out.println("=========================================");
           
           Reparatii r2 =new Reparatii(2,null,c2);
           r2.addLinieRepar(ar1);
           r2.calculValoareTotala();
           System.out.println("-----------------------------------------");
           System.out.println("Se repara un nr de "+(int)ar1.cantitate+" chitari : "+ch1.nume+" cu componenta "+compo1.numecompo+ " defecta de gradul "+grad2.graddef);
           System.out.println("Valoare Reparatie -->> "+(int)ar1.valoare+" cu discountul "+(int)ar1.valDiscount+"         "+((int)ar1.valoare-(int)ar1.valDiscount));
           System.out.println("=========================================");
           System.out.println("Clientului: "+c2.numepren+"  i se acorda Discount La Cantitate || Valoarea Reparatiei -->> "+(int)r2.valoareTotala);
           
     }
}
//**********************************
class PersoaneFizice extends Client{
    /** Creates a new instance of PersoaneFizice */
   public String cnp;
   public String adresa;
public PersoaneFizice(int pCodclient,String pNumePren,String pEmail,String pCnp,String pAdresa){
        super(pCodclient,pNumePren,pEmail);
        cnp=pCnp;
    }
}
//**********************************   
   class PersoaneJuridice extends Client{
    public int codfiscal;
    public String sediu;
    public int capital;
    /** Creates a new instance of PersoaneJuridice */
    public PersoaneJuridice(int pCodclient,String pNumePren,String pEmail,int pCodfiscal,String pSediu,int pCapital){
        super(pCodclient,pNumePren,pEmail);
        codfiscal=pCodfiscal;
        sediu=pSediu;
        capital=pCapital;
    }
}
 //**********************************
  class Chitare {
    public int codchitara;
    public String firma;
    public String nume;
    public int nrtaste;
    /** Creates a new instance of Chitare */
   public Chitare(int pCodchitara,String pFirma,String pNume,int pNrtaste){
        codchitara=pCodchitara;
        firma=pFirma;
        nume=pNume;
        nrtaste=pNrtaste;
    }
   public String getNume(){return this.nume;}
   
}
 
  //*********************************
  class SemiAcustica extends Chitare{
        public String material;
        public int nrdoze;
        public String modeldoza;
    /** Creates a new instance of SemiAcustica */
     public SemiAcustica(int pCodchitara,String pFirma,String pNume,int pNrtaste,String pMaterial,int pNrdoze,String pModeldoza){
     super(pCodchitara,pFirma,pNume, pNrtaste);
     material=pMaterial;
     nrdoze=pNrdoze;
     modeldoza=pModeldoza;
    }
      public String getNume(){return this.nume;}
}
  class Electrica extends Chitare{
    public int nrdoze;
    public String modeldoza;
    public String vibrato;
    /** Creates a new instance of Electrica */
  public Electrica(int pCodchitara,String pFirma,String pNume,int pNrtaste,int pNrdoze,String pModeldoza,String pVibrato){
     super(pCodchitara,pFirma,pNume, pNrtaste);
     nrdoze=pNrdoze;
     modeldoza=pModeldoza;
     vibrato=pVibrato;
    }
}
  class Acustica extends Chitare {
    public String material;
    /** Creates a new instance of Acustica */
   public Acustica(int pCodchitara,String pFirma,String pNume,int pNrtaste,String pMaterial){
        super(pCodchitara,pFirma,pNume, pNrtaste);
        material=pMaterial;
    }
    
}

  //*********************************
   interface Discount {
       float  calculDiscount(ArtRepar AR);
   }
      
   //********
   class DiscountLaValoare implements Discount{
       public double procent;
      public float calculDiscount(ArtRepar AR){
          float valoareDiscount=AR.valoare*(float)procent;
          return valoareDiscount;
         }
      public DiscountLaValoare(double pProcent){
          procent=pProcent;
      }
   }
   //*********************************
   class DiscountLaCantitate implements Discount{
       public double pragCantitate;
       public double nrUnitatiGratuite;
       
      public float calculDiscount(ArtRepar AR){
          if (AR.cantitate>pragCantitate){
              float valoareDiscount=AR.compo.pretcompo*(float)nrUnitatiGratuite;
              return valoareDiscount;
          }
          else
              return 0;
          }
      public DiscountLaCantitate(double pPragCantitate, double pNrUnitatiGratuite){
          pragCantitate=pPragCantitate;
          nrUnitatiGratuite=pNrUnitatiGratuite;
      }
   }
   //**********************************
   class ArtRepar{
      public double cantitate;
      public Chitare ch;
      public GradDefectiune gd;
      public Componente compo;
      public float valDiscount;
      public float valoare;
      public Discount disc;
      public ArtRepar(Chitare pCh,double pCantitate,GradDefectiune pGd,Componente pCompo,double pPragCantitate, double pNrUnitatiGratuite){
          disc=new DiscountLaCantitate(pPragCantitate,pNrUnitatiGratuite);
          
          ch=pCh;
          cantitate=pCantitate;
          gd=pGd;
          compo=pCompo;
           calculValoare();
         calculDiscount();
        
      }
        public ArtRepar(Chitare pCh,double pCantitate,GradDefectiune pGd,Componente pCompo,double pProcent){
          disc=new DiscountLaValoare( pProcent);
          ch=pCh;
          cantitate=pCantitate;
           gd=pGd;
          compo=pCompo;
           calculValoare();
         calculDiscount();
               
      }
      private void calculDiscount(){
          valDiscount=disc.calculDiscount(this);
      }
      public void calculValoare(){
          if (gd.procent<0.60) {
          valoare=compo.pretcompo*(float)gd.procent*(float)cantitate;
          System.out.println("Se Repara!!!");
          }
          else 
          {System.out.println("Se inlocuieste cu o componenta noua!!!!");
          valoare=compo.pretcompo*(float)cantitate;    
          }     
      }
      
        
   }
  //************************************************
   class Reparatii{
       public int nrrepar ;
       public Date data;
       public float valoareTotala;
       public List linieRepar=new ArrayList(0);
       public Client cl;
       public Reparatii(int pNr,Date pData,Client pCl){
        nrrepar=pNr;
        data=pData;
        cl=pCl;
                
       }
       public void addLinieRepar(ArtRepar ar){
           linieRepar.add(ar);
           
       }
       public void  calculValoareTotala(){
           for (int i=0;i<linieRepar.size();i++){
            valoareTotala=valoareTotala+((ArtRepar)linieRepar.get(i)).valoare-((ArtRepar)linieRepar.get(i)).valDiscount;           
           }
       }
   }
   //**************************************************
    class Componente {
    public int codcompo;
    public String numecompo;
    public float pretcompo;
    /** Creates a new instance of Componente */
    public Componente(int pCodcompo,String pNumecompo,float pPretcompo ) {
    codcompo=pCodcompo;
    numecompo=pNumecompo;
    pretcompo=pPretcompo;
    }
    }
   //***************************************************
    class GradDefectiune {
    public int graddef;
    
    public double procent;
    /** Creates a new instance of GradDefectiune */
  public GradDefectiune(int pGraddef,double pProcent) {
   graddef=pGraddef;
   procent=pProcent;
  }
}

   
      
           

     
   
  
   
   
    


