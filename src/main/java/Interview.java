public class Interview {

    static String word1 = "civicmom";
    static int maxLength = 0;
    static String longestPalindrome;

    public static void main(String[] args) {
        for(int i = 0; i < word1.length(); i++){
            for(int j = i+1; j < word1.length(); j++){
                String substring = word1.substring(i,j);
                if(isPalindrome(substring) && substring.length() > maxLength){
                    longestPalindrome = substring;
                    maxLength = substring.length();
                }
            }

        }
        System.out.println(longestPalindrome);
    }

    public static boolean isPalindrome(String str){
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }


}
