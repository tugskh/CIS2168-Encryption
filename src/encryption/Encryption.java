/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author tugsbilegkhaliunbat
 */
public class Encryption {

    /**
     * @param args the command line arguments
     */
    public static int keyValue = 0;
    public static ArrayList<Integer> keyvalues = new ArrayList<>();
    public static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    
    
    
    public static void main(String[] args) {
        //CircularLinkedList<Integer> deck = deck(); // generates random deck
        
        
        int[] list = {1,4,7,10,13,16,19,22,25,28,3,6,9,12,15,18,21,24,27,2,5,8,11,14,17,20,23,26};
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();
        for(int i = 0; i < list.length; i++){
            deck.add(list[i]);
        }
        
        //System.out.println(encrypt("Hello, world!", deck));
        //System.out.println(decrypt("SNISYVZCSL"));
        //System.out.println(decrypt("SNISY"));
        
        
        Scanner sc = new Scanner(System.in);
        Scanner sc1 = new Scanner(System.in);
        System.out.println("How many message to decrypt?");
        int numOfMessage = sc.nextInt();
        String decrypted = "";
        for (int i = 0; i < numOfMessage; i++) {
            System.out.println("Enter a message to be decrypted: ");
            String message = sc1.nextLine();
            encrypt(message,deck);
            decrypted+=decrypt(message)+"\n";
        }
        System.out.println("The decrypted message:\n"+decrypted);
        System.out.println(sc.nextLine());
        
        
        
    }
    
    public static int generateKey(CircularLinkedList<Integer> deck){
        step1(deck);
        step2(deck);
        step3(deck);
        step4(deck);
        step5(deck);
        return keyValue;
    }
    
    
    public static String encrypt(String message,CircularLinkedList<Integer> deck){
        
        String encryptedMessage= "";message = message.replaceAll("[^a-zA-Z]", "");
        ArrayList<Integer> messageNum = new ArrayList<>();
        ArrayList<Integer> encrypted = new ArrayList<>();
        message = message.toUpperCase();
        
        if(message.length()%5 != 0){ // adds X's to the end of the message
            int length = message.length();
            for(int i = 0; i < (5-length%5); i++){
                message+="X";
            }
        }
        
        for(int i = 0; i< message.length(); i++){ // converts letters of the message to numbers
            messageNum.add(alphabet.indexOf(message.charAt(i)+1));
            keyvalues.add(generateKey(deck)); // generating the same number of keys as the letters in the message
        }
        
        for (int i = 0; i < messageNum.size(); i++) { // encrypts the numbers according to the keystream values
            int num = messageNum.get(i)+keyvalues.get(i);
            if(num > 26){
                encrypted.add(num-26);
            }
            else{
                encrypted.add(num);
            }
        }
        
        for (int i = 0; i < encrypted.size(); i++) { // converts numbers back to letters
            encryptedMessage+= alphabet.charAt(encrypted.get(i)-1);
        }
        
        //System.out.println("messageNum: "+messageNum);
        //System.out.println("keyvalues: "+keyvalues);
        //System.out.println("encrypted: "+encrypted);
        //
        
        
        return encryptedMessage;
    }
    
    public static String decrypt(String message){
        String decryptedMessage = "";
        ArrayList<Integer> messageNum = new ArrayList<>();
        ArrayList<Integer> decrypted = new ArrayList<>();
        
        for (int i = 0; i < message.length(); i++) { //converts encrypted message to numbers
            messageNum.add(alphabet.indexOf(message.charAt(i))+1);
        }
        
        for (int i = 0; i < messageNum.size(); i++) { // decrypts the numbers according to the keystream values
            if(messageNum.get(i)<=keyvalues.get(i)){
                decrypted.add(messageNum.get(i)+26-keyvalues.get(i));
            }
            else{
                decrypted.add(messageNum.get(i)-keyvalues.get(i));
            }
        }
        
        for (int i = 0; i < decrypted.size(); i++) { // converts the message back to letters
            decryptedMessage+= alphabet.charAt(decrypted.get(i)-1);
        }
            
        
        //System.out.println("messageNum: "+messageNum);
        //System.out.println("Keyvalues: "+keyvalues);
        //System.out.println("decrypted: "+decrypted);
        
        
        
        return decryptedMessage;
    }
    
    private static CircularLinkedList<Integer> deck() {
        ArrayList<Integer> values = new ArrayList<>();
        
        for(int i = 0; i< 28; i++){
            values.add(i, i+1);
       }
        Collections.shuffle(values);
        CircularLinkedList<Integer> deck = new CircularLinkedList<>();
        for(int i = 0; i < values.size(); i++){
            deck.add(values.get(i));
        }
        return deck;
    }
    
    
    private static void step1(CircularLinkedList<Integer> deck) {
        
        for(int i = 0; i< deck.size-1; i++){
            if(deck.get(i)==27){
                //System.out.println(i);
                deck.remove(i);
                deck.add(i+1, 27);
                break;
            }
        }
        
    }

    private static void step2(CircularLinkedList<Integer> deck) {
        for(int i = 0; i< deck.size-3; i++){
            if(deck.get(i)==28){
                //System.out.println(i);
                deck.remove(i);
                deck.add(i+2, 28);
                break;
            }
        }
        
        
    }
    
    private static void step3(CircularLinkedList<Integer> deck) {
        CircularLinkedList<Integer> last = new CircularLinkedList<>();
        CircularLinkedList<Integer> first = new CircularLinkedList<>();
        int indexA = 0;
        int indexB = 0;
        
        for(int i = 0; i < deck.size; i++){
            if(deck.get(i) == 27){indexA = i;}
            if(deck.get(i) == 28){indexB = i;}
        }
        
        // storing everything before the first joker and after the second joker
        if(indexA < indexB){
            for(int i = 0; i < indexA; i++){last.add(deck.get(i));}
            for(int i = indexB + 1; i < deck.size; i++){first.add(deck.get(i));}
        }
        
        if(indexB < indexA){ 
            for(int i = 0; i < indexB; i++){last.add(deck.get(i));}
            for(int i = indexA + 1; i < deck.size; i++){first.add(deck.get(i));}
        }
        
        for(int i = 0; i < last.size; i++){deck.remove(0);} // removing everything before the first joker
        
        for(int i = 0; i<first.size; i++){deck.remove(deck.size - 1);} // removing everything after the second joker
        
        for(int i = first.size - 1; i >= 0; i--){deck.add(0, first.get(i));} // adding everything after the second joker to th beginning
        
        for(int i = 0; i < last.size; i++){deck.add(last.get(i));} // adding everything before first joker to the end
    }
    
    private static void step4(CircularLinkedList<Integer> deck) {
        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        for(int i = 0; i < deck.get(deck.size - 1); i++){list.add(deck.get(i));} // storing the first number of elements according to the last number in the list
        for(int i = 0; i < list.size; i++){deck.add(deck.size - 1, list.get(i));} // adding the stored elemtents to the end of the list just before the last elements
        for(int i = 0; i < list.size; i++){deck.remove(0);} // removing the elemtents from the beginning of the list
        //System.out.println(list);
    }
    
    private static void step5(CircularLinkedList<Integer> deck) {
        while(deck.get(deck.get(0)) == 27 || deck.get(deck.get(0)) == 28){
            step1(deck);
            step2(deck);
            step3(deck);
            step4(deck);
        }
        keyValue = deck.get(deck.get(0));
        //System.out.println(keyValue);
    }

    
    
    
}
