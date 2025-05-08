import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        int choice = -1;


        ObjectMapper map = new ObjectMapper();

        File file = new File("src/data.json");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String total="";
        while((st = br.readLine())!=null){
            total = total+st;
        }

        List myList = map.readValue(total, new TypeReference<List<Task>>() {});
        System.out.println("Reading in existing tasks...");
        System.out.println(myList);
        myList.get(0).getClass();

        for(Object a : myList){
            tasks.add((Task) a);
        }




        do {
            displayMenu();
            try {
                choice = input.nextInt();
                input.nextLine(); //need cause it int does not start a new line like nextline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input enter a valid number");
                input.nextLine(); // for inpute and stops it from looping
                continue;
            }

            if (choice == 1) {
                addTask(input, tasks);
            } else if (choice == 2) {
                removeTask(input, tasks);
            } else if (choice == 3) {
                updateTask(input, tasks);
            } else if (choice == 4) {
                listTasks(tasks);

            } else if (choice == 5) {
                specifiedPriority(input,tasks);

            } else if (choice == 0) {
                System.out.println("Exiting program");
                map.writeValue(new File("src/data.json"),tasks);
            } else {
                System.out.println("Invalid choice try again");
            }

        } while (choice != 0);
        input.close();
    }

    public static void displayMenu() {
        System.out.println("choose an option:");
        System.out.println("(1) Add a task");
        System.out.println("(2) Remove a task");
        System.out.println("(3) Update a task");
        System.out.println("(4) List all tasks");
        System.out.println("(5) list all tasks of a specified Priority");
        System.out.println("(0) Exit");
        System.out.print("Enter your choice: ");
    }

    public static void addTask(Scanner input, ArrayList<Task> tasks) {
        System.out.println("\nEnter description of the task:");
        String description = input.nextLine();

        System.out.println("Enter title of the task:");
        String title = input.nextLine();

        int priority;
        while (true) {
            System.out.println("Enter priority of the task (levels 1-5):");
            try {
                priority = input.nextInt();
                input.nextLine();

                if (priority >= 1 && priority <= 5) {
                    break;
                } else {
                    System.out.println("Invalid priority number enter a number between 1 and 5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input enter a valid number");
                input.nextLine();
            }
        }

        Task task = new Task(description, title, priority);
        tasks.add(task);
        System.out.println("Task added: " + task);
    }


    public static void removeTask(Scanner input, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove");
            return;
        }

        listTasks(tasks);
        System.out.print("Enter the task number to remove: ");
        try {
            int taskNumber = input.nextInt();
            input.nextLine();
            if (taskNumber > tasks.size() || taskNumber <= 0) {
                System.out.println("Invalid task number");
            } else {
                tasks.remove(taskNumber - 1);
                System.out.println("Task removed");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input  enter a valid number");
            input.nextLine();
        }
    }

    public static void updateTask(Scanner input, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to update");
            return;
        }

        listTasks(tasks);
        System.out.print("Enter the task number to update: ");
        try {
            int taskNumber = input.nextInt();
            input.nextLine();
            if (taskNumber > tasks.size() || taskNumber <= 0) {
                System.out.println("Invalid task number");
            } else {
                System.out.println("Enter new description:");
                String newDescription = input.nextLine();

                System.out.println("Enter new title:");
                String newTitle = input.nextLine();

                System.out.println("Enter new priority:");
                int newPriority = input.nextInt();
                input.nextLine();

                Task newTask = new Task(newDescription, newTitle, newPriority);
                tasks.set(taskNumber - 1, newTask);
                System.out.println("Task updated: " + newTask);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.  enter a valid number");
            input.nextLine();
        }
    }

    public static void listTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list");
            return;
        }

        Collections.sort(tasks);

        System.out.println("\nTasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }


    public static void specifiedPriority(Scanner input, ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list.");
            return;
        }
        int desiredPriority;
        while (true) {
            System.out.println("Enter the priority level (1-5) to filter tasks:");
            try {
                desiredPriority = input.nextInt();
                input.nextLine();
                if (desiredPriority >= 1 && desiredPriority <= 5) {
                    break;
                } else {
                    System.out.println("Invalid priority Please enter a number between 1 and 5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input Please enter a valid number");
                input.nextLine();
            }
        }// input chaching for bad inputs

        boolean found = false;
        System.out.println("\nTasks with priority " + desiredPriority + ":");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i); //t the the number of the task that we going thoure while the arrey is being mov being move throw and the task is the class of the t because in java and stuff classes become a data type kind of why im i writing this much im very sleeply im going to wake up and be like what did i write i love google and w3schoosl simga robloxs roblox roblxo roblox roblox
            if (t.getPriority() == desiredPriority) {// dont forget .getPriority is coming from the class
                System.out.println((i + 1) + ". " + t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks with priority " + desiredPriority + " found.");
        }
    }
}