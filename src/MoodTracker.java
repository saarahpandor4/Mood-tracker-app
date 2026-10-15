import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;


public class MoodTracker {
    

public static void main(String s[]){

// Create an ArrayList to store Mood objects
ArrayList<Mood> moodsList = new ArrayList<>();;
// Create a Scanner to read console input
Scanner scanner = new Scanner(System.in);

while(true) {
System.out.println(
"Press 'a' to add mood\n" + 
"'d' to delete mood(s)\n" +
"'e' to edit mood\n" +
"'s' to search for moods\n" +
"'M' to get all moods\n" +
"'w' to write the moods to a file\n" +
"Type 'Exit' to exit");
			
String menuOption = scanner.nextLine();

switch(menuOption) {
	//add code to add mood
    case "a": 
    System.out.println("Enter the mood name");
    String moodName = scanner.nextLine();

    System.out.println("Are you tracking the mood for a current day? y/n");
    String isForCurrentDate = scanner.nextLine();

    Mood moodToAdd = null;

    if (isForCurrentDate.equalsIgnoreCase("n")) {
        try {
            System.out.println("Input the date in MM/dd/yyyy format:");
            String moodDateStr = scanner.nextLine();

            DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate moodDate =
                LocalDate.parse(moodDateStr, dateFormatter);

            System.out.println("Input the time in HH:mm:ss format:");
            String moodTimeStr = scanner.nextLine();

            DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime moodTime =
                LocalTime.parse(moodTimeStr, timeFormatter);

            System.out.println("Add notes about this mood");
            String moodNotes = scanner.nextLine();

            if (moodNotes.isBlank()) {
                moodToAdd = new Mood(moodName, moodDate, moodTime);
            } else {
                moodToAdd = new Mood(
                    moodName, moodDate, moodTime, moodNotes
                );
            }

        } catch (DateTimeParseException dfe) {
            System.out.println(
                "Incorrect format of date or time. Cannot create mood."
            );
            continue;
        }

    } else {
        System.out.println("Add notes about this mood");
        String moodNotes = scanner.nextLine();

        if (moodNotes.isBlank()) {
            moodToAdd = new Mood(moodName);
        } else {
            moodToAdd = new Mood(moodName, moodNotes);
        }
    }

    try {
        if (isMoodValid(moodToAdd, moodsList)) {
            moodsList.add(moodToAdd);
            System.out.println("The mood has been added to the tracker");
        }
    } catch (InvalidMoodException ime) {
        System.out.println("The mood is not valid");
    }
    continue;  
	
    //add code to delete mood
	case "d": 
    System.out.println("Enter '1' to delete all moods by date\n" +
    "Enter '2' to delete a specific mood");

String deleteVariant = scanner.nextLine();

if (deleteVariant.equals("1")) {
try {
System.out.println("Input the date in MM/dd/yyyy format:");
String moodDateStr = scanner.nextLine();

DateTimeFormatter dateFormatter =
DateTimeFormatter.ofPattern("MM/dd/yyyy");
LocalDate moodDate =
LocalDate.parse(moodDateStr, dateFormatter);

boolean areMoodsDeleted = deleteMoods(moodDate, moodsList);

if (areMoodsDeleted) {
System.out.println("The moods have been deleted");
} else {
System.out.println("No matching moods found");
}

} catch (DateTimeParseException dfe) {
System.out.println("Incorrect format of date. Cannot delete mood.");
}

} else if (deleteVariant.equals("2")) {
try {
System.out.println("Enter the mood name");
String deleteMoodName = scanner.nextLine();

System.out.println("Input the date in MM/dd/yyyy format:");
String moodDateStr = scanner.nextLine();

DateTimeFormatter dateFormatter =
DateTimeFormatter.ofPattern("MM/dd/yyyy");
LocalDate moodDate =
LocalDate.parse(moodDateStr, dateFormatter);

System.out.println("Input the time in HH:mm:ss format:");
String moodTimeStr = scanner.nextLine();

DateTimeFormatter timeFormatter =
DateTimeFormatter.ofPattern("HH:mm:ss");
LocalTime moodTime =
LocalTime.parse(moodTimeStr, timeFormatter);

Mood delMood = new Mood(deleteMoodName, moodDate, moodTime);
boolean isMoodDeleted = deleteMood(delMood, moodsList);

if (isMoodDeleted) {
System.out.println("The mood has been deleted");
} else {
System.out.println("No matching mood found");
}

} catch (DateTimeParseException dfe) {
System.out.println("Incorrect format of date or time. Cannot delete mood.");
}

} else {
System.out.println("Invalid option. Please enter 1 or 2.");
}
	continue;

	case "e": 	//add code to edit mood
    Mood moodToEdit = null;
    try {
        System.out.println("Enter the mood name");
        String editMoodName = scanner.nextLine();
        System.out.println("Input the date in MM/dd/yyyy format:");
        String moodDateStr = scanner.nextLine();
        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate moodDate =
                LocalDate.parse(moodDateStr, dateFormatter);

        System.out.println("Input the time in HH:mm:ss format:");
        String moodTimeStr = scanner.nextLine();
        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime moodTime =
                LocalTime.parse(moodTimeStr, timeFormatter);

        System.out.println("Add new notes about this mood");
        String moodNotes = scanner.nextLine();

        if (moodNotes.strip().equals("")) {
            System.out.println("No notes entered");
            continue;
        } else {
            moodToEdit = new Mood(
        editMoodName, moodDate, moodTime, moodNotes);

            boolean isMoodEdited = editMood(moodToEdit, moodsList);

            if (isMoodEdited) {
                System.out.println(
                        "The mood has been successfully edited");
            } else {
                System.out.println("No matching mood could be found");
            }
        }
    } catch (DateTimeParseException dfe) {
        System.out.println(
                "Incorrect format of date or time. Cannot edit mood.");
    }
	continue;

	case "s": 	//add code to search mood
    System.out.println(
     "Enter '1' to search for all moods by date\n" +
     "Enter '2' to search for a specific mood");

    String searchVariant = scanner.nextLine();

    if (searchVariant.equals("1")) {
    try {
        
    System.out.println(
      "Input the date in MM/dd/yyyy format:");
    String searchDateStr = scanner.nextLine();

  DateTimeFormatter dateFormatter =
  DateTimeFormatter.ofPattern("MM/dd/yyyy");
  LocalDate searchDate =
  LocalDate.parse(searchDateStr, dateFormatter);

    searchMoods(searchDate, moodsList);

        } 
catch (DateTimeParseException dfe) {
      System.out.println(
 "Incorrect format of date. Cannot search mood.");
        }

    } 
else if (searchVariant.equals("2")) {

try {
    System.out.println("Enter the mood name");
    String searchMoodName = scanner.nextLine();

    System.out.println(
    "Input the date in MM/dd/yyyy format:");
String searchDateStr = scanner.nextLine();

    DateTimeFormatter dateFormatter =
    DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate searchDate =
    LocalDate.parse(searchDateStr, dateFormatter);

    System.out.println("Input the time in HH:mm:ss format:");

String searchTimeStr = scanner.nextLine();

DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

LocalTime searchTime = LocalTime.parse(searchTimeStr, timeFormatter);

Mood moodToSearch = new Mood(searchMoodName, searchDate, searchTime);

searchMood(moodToSearch, moodsList);

        } 
catch (DateTimeParseException dfe) {
    System.out.println("Incorrect format of date or time. Cannot search mood.");
        }

    } 
    else {
        System.out.println("Invalid search option!");
    }

	continue;
    //add code to get all moods
	case "M":
    for (Mood moodobj : moodsList) {
        System.out.println(moodobj);
    }
	continue;

	case "w": 	//add code to write mood to a file
    try (PrintWriter writer = new PrintWriter(new FileWriter("Moods.txt"))) {
    for (Mood mood : moodsList) {
        writer.println(mood+"\n\n");
    }
	System.out.println("The entries are written to a file");
} catch (IOException e) {
    System.err.println("Error writing to file: " + e.getMessage());
}

	continue;
	case "Exit": 	
    
    System.out.println("Thank you for using the MoodTracker. Goodbye!");
	scanner.close();
    return;
    
    //break;
	default: 	
    System.out.println("Not a valid input!");
	continue;
			}
	}
} 


public static void searchMoods(
        
LocalDate moodDate, ArrayList<Mood> moodsList) 
  {
    boolean found = false;

for (Mood tempMood : moodsList) 
    {
       
 if (tempMood.getDate().equals(moodDate)) {
            found = true;
            System.out.println(tempMood);
        }
    }

 if (!found) {
    System.out.println("No matching records could be found!");
    }
}

public static void searchMood(
Mood mood, ArrayList<Mood> moodsList) {

    boolean found = false;

    for (Mood tempMood : moodsList)
{
    if (tempMood.equals(mood)) {
            found = true;
            System.out.println(tempMood);
     }
 }

    if (!found) {
        System.out.println("No matching records could be found!");
    }
}



public static boolean editMood(
Mood moodToEdit, ArrayList<Mood> moodsList) {

for (Mood tempMood : moodsList) 
{
    if (tempMood.equals(moodToEdit))
    { 
      tempMood.setNotes(moodToEdit.getNotes());
    return true;
        }
    }

    return false;
}



public static boolean deleteMoods(
LocalDate moodDate, ArrayList<Mood> moodsList) 
{
    boolean removed = false;

    java.util.Iterator<Mood> iterator = moodsList.iterator();

    while (iterator.hasNext())
    {
     Mood tempMood = iterator.next();

    if (tempMood.getDate().equals(moodDate)) 
    {
        iterator.remove();
        removed = true;
        }
    }

    return removed;
}


public static boolean deleteMood(
Mood mood, ArrayList<Mood> moodsList) 
{
    java.util.Iterator<Mood> iterator = moodsList.iterator();

while (iterator.hasNext()) {
Mood tempMood = iterator.next();

if (tempMood.equals(mood)) 
 {
     iterator.remove();
    return true;
        }
    }

    return false;
}


public static boolean isMoodValid(
    Mood mood, ArrayList<Mood> moodsList)

    throws InvalidMoodException 
    {
for (Mood tempMood : moodsList) 
{
    if (tempMood.equals(mood)) {

    throw new InvalidMoodException();
            }
        }

    return true;
    } 
}
