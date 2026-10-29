package gov.bls.responseRates.properties;

/* Core Imports */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResponseRatesProperty extends Properties {

	private static final long serialVersionUID = 1L;

	public ResponseRatesProperty() {
      }

      private String getPropFileName() {
            System.setProperty("RRATES_ENV", "gov/bls/ipp/responseRates/responserates.properties");
            return System.getProperty("RRATES_ENV");
      }

      public String load() throws FileNotFoundException, IOException {
            return load(getPropFileName());
      }

      /**
       * load
       * @param fileName    File name
       * @return fileName
       * @throws FileNotFoundException
       * @throws IOException
       */
      public String load(String fileName)throws FileNotFoundException, IOException{          

          if(fileName == null || fileName.equals(""))throw new FileNotFoundException("file " + fileName + " not valid");

            InputStream file = null;

            try {
              file = this.getClass().getClassLoader().getResourceAsStream(fileName);                        
              load(file);
            }catch(Exception e){
          	  
          	  throw new FileNotFoundException("Properties file failed to load: "+e.toString());
            }
            finally{
              if(file != null)
                  file.close();
            }

            return fileName;
      }


/*-End-*/
}