package interface_bdd;

public class Main_bdd {

      

=======
      Thread t = new Thread(new Runnable(){
    	 @Override
         public void run(){
            while(isRunning == true){
               
               try {
                  Socket client = server.accept();
                  System.out.println("Connexion cliente reçue.");                  
                  Thread t = new Thread(new ClientProcessor(client));
                  t.start();
                  
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            
            try {
               server.close();
            } catch (IOException e) {
               e.printStackTrace();
               server = null;
            }
         }
      });
      
      t.start();
   }
   
   public void close(){
      isRunning = false;
   }   
}
