import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DNPatchSpammer {

    /**
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner stringInput = new Scanner(System.in);
        System.out.println("Input the last 3 characters from the latest patch notes in caps");

        StringBuilder str = new StringBuilder(stringInput.next());
        int lastChar = str.length()-1;
        int midChar = str.length()-2;
        int firstChar = str.length()-3;

        ArrayList<String> lastThreeDigits = new ArrayList<String>();

        counterUpToFirst(str, lastChar, midChar, firstChar, lastThreeDigits);

        System.out.println("Initializing process...");

        for(int i = 0; i < lastThreeDigits.size(); i++){
            final URL url2 = new URL("http://dragonnest.nexon.net/news/all/00"+lastThreeDigits.get(i));
            checkIfValidURL(url2);
            Thread.sleep(1000);
        }

        System.out.println("Process finalized! \nTerminating program...");
    }

    private static int counterUpToFirst(StringBuilder str, int lastChar, int midChar, int firstChar, ArrayList<String> list){
        counterUpToMid(str, lastChar, midChar, list);
        if((str.charAt(firstChar)=='z') && (str.charAt(midChar)=='z') && (str.charAt(lastChar)=='z')){
            return 0;
        }
        incrementCharByOne(firstChar, str);
        resetCharAt(lastChar, str, list);
        resetCharAt(midChar, str, list);
        list.add(str.toString());
        //System.out.println(str);
        counterUpToFirst(str, lastChar, midChar, firstChar, list);
        return 0;
    }

    private static int counterUpToMid(StringBuilder str, int lastChar, int midChar, ArrayList<String> lastThreeDigits) {
        incrementAllCharsAt(lastChar, str, lastThreeDigits);
        if((str.charAt(midChar)=='z') && (str.charAt(lastChar)=='z')){
            return 0;
        }
        incrementCharByOne(midChar, str);
        resetCharAt(lastChar, str, lastThreeDigits);
        counterUpToMid(str, lastChar, midChar, lastThreeDigits);
        return 0;
    }

    private static void incrementCharByOne(int index, StringBuilder str){
        if(str.charAt(index)!='z'){
            str.setCharAt(index, toAlphaNum(index, str) );  //converts to alphanumeric
            str.setCharAt(index, (char) (str.charAt(index) + 1));
        }
    }
    private static void incrementAllCharsAt(int index, StringBuilder str, ArrayList<String> lastThreeDigits){
        while (str.charAt(index) < 'z')
        {
            str.setCharAt(index, toAlphaNum(index, str) );  //converts to alphanumeric
            str.setCharAt(index, (char) (str.charAt(index) + 1));
            lastThreeDigits.add(str.toString());
            //System.out.println(str);
        }
    }

    private static void resetCharAt(int index, StringBuilder str, ArrayList<String> lastThreeDigits) {
        str.setCharAt(index, '0');
        lastThreeDigits.add(str.toString());
        //System.out.println(str);
    }

    private static char toAlphaNum(int index, StringBuilder str) {
        if ( str.charAt(index)=='z'){
            return '0'-1;
        }
        else if ( str.charAt(index)=='Z'){
            return 'a'-1;
        }
        else if(str.charAt(index)=='9'){
            return 'A'-1;
        }
        return str.charAt(index);
    }


    public static void checkIfValidURL(final URL url) throws MalformedURLException, IOException {
        final URL DEFAULT_URL = new URL("http://dragonnest.nexon.net/news/All");

        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.getResponseCode();

        //System.out.println("Input URL: " + url);
        //System.out.println("Debugging purpose URL Gotten is:" + huc.getURL());

        if (huc.getURL().equals(DEFAULT_URL) || huc.getURL().toString().startsWith("http://dragonnest.nexon.net/Error/") || huc.getURL().toString().contains(">") || huc.getURL().toString().contains("<")) {
            System.out.println("BAD URL = " + url);
        } else {
            System.out.println("< --- GOOD URL = " + huc.getURL() + " --- >");
        }
    }

}
