package jsocket.utils;

class MiClase{
    private String nombre;
    public MiClase(String nameClase){
        this.nombre = nameClase;
    }
    public String getNombre(){
        return this.nombre;
    }
}
class MiClase2 extends MiClase{
  
    public MiClase2(String nombre) {
        super(nombre);
    }
}


class MiClase1 extends MiClase{
  
  public MiClase1(String nombre){
      super(nombre);
  }
}
class claseDaddy {
    public String daddy;
}
public class Main {
    
  public Main(){
      
  }
  public static void main(String[] argv) throws Exception {
      Main m = new Main();
      MiClase1 c1 = new MiClase1("clase 1");
      m.addClase(c1);
      MiClase2 c2 = new MiClase2("clase 2");
      m.addClase(c2);
  }
  
  public void addClase(Object clase){
      
     if(clase instanceof MiClase1){
         System.out.println("");
     }
     
     if(clase.getClass() == MiClase.class){
         System.out.println("Clase Padre");
     }else{
         if(clase.getClass() == MiClase1.class){
             System.out.println("Clase 1");
         }else{
             if(clase.getClass() == MiClase2.class){
                 System.out.println("Clase 2");
             }else{
                 System.out.println("no hay ninguna clase");
             }
         }
     }
  }
  
}