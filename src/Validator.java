
import java.util.Scanner;

/** Created by Reid Nolan on 3/3/2017
 * Validator Class
 * Class for validating user input
 * @author Reid Nolan
 * @since 03/03/2017
 * @version 1.03
 */
class Validator
{
    /**
     * returns validated user input String
     * @param kPROMPT kPROMPT
     * @return inputString
     */
    static String getString(final String kPROMPT)
    {
        //declare local variable
        String inputString = "";
        //create new scanner
        Scanner kSC = new Scanner(System.in);
        //loop until string is valid
        boolean stringIsValid = false;
        while(!stringIsValid)
        {
            //prompt for input
            System.out.print(kPROMPT);
            //assign next scanner input string to variable
            inputString = kSC.nextLine();
            if (inputString.isEmpty())
            {/*doNothing()*/}
            else
            {
                //set boolean to true to exit loop
                stringIsValid = true;
            }
        }
        return inputString;
    }
}