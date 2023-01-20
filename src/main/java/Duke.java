import java.util.ArrayList;
import java.util.Scanner;
public class Duke {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

    private static String greetingsFromSkittles = "Hello I'm Skittles\nWhat can I do for you?\n";
    private static String adiosFromSkittles = "Bye. Hope to see you again soon!";

    private static String gotItMessage = "Got it. I've added this task:\n";

    //keeping track of number of things in list
    static int numOfThings = 0;

    static String howLongListNowMessage = "\nNow you have " + numOfThings + " tasks in the list";

        //assume no more than 100 tasks
        static ArrayList<Task> lstOfTasks = new ArrayList<>();

        //method to greet
        public static void hello() {
            System.out.print(greetingsFromSkittles);
        }

        public static void adios() {
            System.out.print(adiosFromSkittles);
        }

        public static void addStufftoLst(String xx) {
            lstOfTasks.add(new Task(xx));
            numOfThings += 1;
            System.out.println("added: " + xx);
        }

        //basically print out the list. must be numbered.
        public static void displayLst() {
            boolean isItMT = false;
            StringBuilder txtToDisplay = new StringBuilder("Here are the tasks in your list:");
            String nothingMessage = "Nothing in your list man!";
            for (Task thingInList : lstOfTasks) {
                if (thingInList != null) {
                    isItMT = true;
                    txtToDisplay.append("\n").append(thingInList.getRank()).append(".")
                            .append(thingInList.toString());
                }
            }
            if (!isItMT) {
                System.out.println(nothingMessage);
            } else {
                System.out.println(txtToDisplay);
            }
        }


        public static void completeTask(String xx) {
            int taskNum = Integer.parseInt(xx);
            String txt = "Try again! You don't have that task in your list!";
            for (Task thingInList : lstOfTasks) {
                if (thingInList != null && (thingInList.getRank() == taskNum)) {
                    thingInList.strike();
                    txt = ("Nice! I've marked this task as done:\n" + thingInList.isCompleted()
                            + thingInList.getName());
                }
            }
            System.out.println(txt);
        }

        public static void undoCompleteTask(String xx) {
            int taskNum = Integer.parseInt(xx);
            String txt = "Try again!";
            for (Task thingInList : lstOfTasks) {
                if (thingInList != null && (thingInList.getRank() == taskNum)) {
                    thingInList.unstrike();
                    txt = ("Ok, I've marked this task as not done yet:\n" + thingInList.isCompleted()
                            + thingInList.getName());
                }
            }
            System.out.println(txt);
        }

    public static void addAToDo(String todo) {
        ToDo mustDo = new ToDo(todo);
        lstOfTasks.add(mustDo);
        numOfThings += 1;
        System.out.println("Got it. I've added this task:\n" + mustDo.toString() +
                            "\nNow you have " + numOfThings + " tasks in the list.");
    }

    public static void addTimeSensitive(String name, String doByWhen) {
        Deadline dateline = new Deadline(name, doByWhen);
        lstOfTasks.add(dateline);
        numOfThings += 1;
        System.out.println("Got it. I've added this task:\n" + dateline.toString() +
                            "\nNow you have " + numOfThings + " tasks in the list");
    }

    public static void addAnEvent(String name, String startTime, String endTime) {
        Event suitAndTie = new Event(name, startTime, endTime);
        lstOfTasks.add(suitAndTie);
        numOfThings += 1;
        System.out.println("Got it. I've added this task:\n" + suitAndTie.toString() +
                "\nNow you have " + numOfThings + " tasks in the list");
    }

    public static void delete (String userTyped) {
        try {
            String rankOfTaskToDelete = userTyped.split(" ",2)[1];
            int taskToDeleteInt = Integer.parseInt(rankOfTaskToDelete);
            Task deleted = lstOfTasks.remove(taskToDeleteInt - 1);
            numOfThings --;
            System.out.println("Noted. I've removed this task.\n" + deleted.toString()
                    + "\nNow you have " + numOfThings + " tasks in the list.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You didn't enter a valid task to delete man!");
        }
    }


    public static void main(String[] args) {

        //start by greeting
        hello();
        while (true) {
            Scanner takingInput = new Scanner(System.in);
            String userTyped = takingInput.nextLine().toLowerCase();
                String frontWord = userTyped.split(" ")[0];
                //exit
                if (frontWord.equals("bye")) {
                    adios();
                    break;
                } else if (frontWord.equals("list")) {
                    //show user the list in this case
                    displayLst();
                } else if (frontWord.equals("mark")) {
                    completeTask(userTyped.substring(userTyped.length() - 1));
                } else if (frontWord.equals("unmark")) {
                    undoCompleteTask(userTyped.substring(userTyped.length() - 1));
                } else if (frontWord.equals("todo")) {
                    try {
                        String actualTask = userTyped.split(" ", 2)[1];
                        addAToDo(actualTask);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You didn't type a todo man!");
                    }
                } else if (frontWord.equals("deadline")) {
                    try {
                        String actualDeadlineTask = userTyped.split(" ", 2)[1].split(" /by ",2)[0];
                        String byWhen = userTyped.split(" ", 2)[1].split(" /by ",2)[1];
                        addTimeSensitive(actualDeadlineTask,byWhen);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You didn't type a deadline man!");
                    }
                } else if (frontWord.equals("event")) {
                    try {
                        String actualEvent = userTyped.split(" ",2)[1].split(" /from ", 2)[0];
                        String startTime =  userTyped.split(" ",2)[1].split(" /from ", 2)[1]
                                .split(" /to ",2)[0];
                        String endTime =  userTyped.split(" ",2)[1].split(" /from ", 2)[1]
                                .split(" /to ",2)[1];
                        addAnEvent(actualEvent,startTime,endTime);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You didn't type an event man!");
                    }
                } else if (frontWord.equals("delete")) {
                    delete(userTyped);
                } else {
                    System.out.println("Try again fat fingers!");
                }
            }
        }
    }

