import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class wordsclassrunner {
    public static void main(String[] args) throws IOException{

        if (args.length == 0) {
            System.out.println("no ingreso el nombre del archivo de las palabras");
            System.exit(0);
        }

        FileReader leer = null;
        FileReader stopword = null;
        try {
            leer = new FileReader(args[0]);
            stopword = new FileReader((args[1]));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }

        BufferedReader inputFile = new BufferedReader(leer);
        BufferedReader inputword = new BufferedReader(stopword);

        String textLine = null;

        int lineCount = 0;
        int wordCount = 0;
        int numberCount = 0;

        String delimiters = "\\s+|,\\s*|\\.\\s*|\\;\\s*|\\:\\s*|\\!\\s*|\\¡\\s*|\\¿\\s*|\\?\\s*|\\-\\s*"
                + "|\\[\\s*|\\]\\s*|\\(\\s*|\\)\\s*|\\\"\\s*|\\_\\s*|\\%\\s*|\\+\\s*|\\/\\s*|\\#\\s*|\\$\\s*";
        Set<String> wordslist = new HashSet<>();
        String l;
        while ((l = inputword.readLine()) != null) {
            wordslist.add(l);
        }
        Set<String> list = new HashSet<>();

        long startTime = System.currentTimeMillis();
        try {
            while ((textLine = inputFile.readLine()) != null) {
                lineCount++;

                if (textLine.trim().length() == 0) {
                    continue;
                }

                // limitamos las palabras para separarlas
                String words[] = textLine.split( delimiters );

                wordCount += words.length;

                for (String theWord : words) {

                    theWord = theWord.toLowerCase().trim();

                    boolean numericco = true;

                    // verify token
                    try {
                        Double num = Double.parseDouble(theWord);
                    } catch (NumberFormatException e) {
                        numericco = false;
                    }

                    // ya verificcado el token como numero, pasar al siguiente y asi sucesivamente
                    if (numericco) {
                        numberCount++;
                        continue;
                    }

                    // agregar a la lista en caso que la palabra no sse encuentre en esta
                    if ( !list.contains(theWord) ) {
                        list.add(theWord);
                    }
                }
            }
            // tiempo para ejecutar
            long executetime = System.currentTimeMillis() - startTime;
            inputFile.close();
            leer.close();

            System.out.printf("%2.3f  segundos, %,d lineas y %,d palabras\n",
                    executetime / 1000.00, lineCount, wordCount - numberCount);

            System.out.printf("%,d palabras diferentes\n", list.size());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        for(String li: list){
            for(String w: wordslist){
                if(li != w){
                    System.out.println(li);
                }
            }

        }



    }
}
