package utils;

public class DataGeneration {

    public synchronized  String generateRandomString (int lettersSize)
    {
        // choose a Character random from this String
        String alphabeticString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(lettersSize);

        for (int i = 0; i < lettersSize; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(alphabeticString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(alphabeticString
                    .charAt(index));
        }
        return sb.toString().toLowerCase() + generateRandomTimeStamp() ;
    }


    public synchronized  String generateRandomAlphanumericString (int lettersSize)
    {
        // choose a Character random from this String
        String alphabeticString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(lettersSize);

        for (int i = 0; i < lettersSize; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(alphabeticString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(alphabeticString
                    .charAt(index));
        }
        return  sb.toString() + generateRandomTimeStamp() ;
    }

    public synchronized  String generateRandomTimeStamp ()
    {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp.substring(timestamp.length()- 5) ;
    }
    public static void main(String[] args) {
        System.out.println(new DataGeneration().generateRandomTimeStamp() );
    }
}
