package practise.application;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Employee {

    private String name;
    private String designation;
    private double salary;
    private String cty;


    public Employee(String name, String designation, double salary, String cty) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.cty = cty;
    }

    public static void main(String[] args) {

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Sheldon", "Scientist", 100.00, "Califonia"));
        employees.add(new Employee("Leonard", "Scientist", 70.00, "Califonia"));
        employees.add(new Employee("Howard", "Astronaut", 50.00, "New wales"));
        employees.add(new Employee("Raj", "Scientist", 50.00, "Paris"));


        Predicate<Employee> p1 = employee -> employee.designation.equals("Scientist");
        display(p1, employees);

        System.out.println("************ employees - negate  **********");
        display(p1.negate(), employees);

        System.out.println("*********** employees salary greater than 60 **********");
        Predicate<Employee> p2 = employee -> employee.salary > 60;
        display(p1.and(p2), employees);

        System.out.println("************ Equals method ***********");

//        Prdicate has some default methods
//                and
//                or
//                negate
//                one static method ->  isEqual(t t)

        Predicate<String> p3 = Predicate.isEqual("Astronaut");
        String designation_ = employees.get(2).designation;
        System.out.println(p3.test(designation_));



        System.out.println("************ Equals method Object ***********");

        Employee e1 = new Employee("Sheldon", "Scientist", 100.00, "Califonia");
        Predicate<Employee> isScientist = Predicate.isEqual(e1);

        System.out.println(" -->>> "+isScientist.test(e1));



    }

    private static void display(Predicate<Employee> p1, ArrayList<Employee> employees) {

        employees.forEach(e -> {
            if (p1.test(e)) {
                System.out.println("name : " + e.name);
            }

        });
    }


    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", salary=" + salary +
                ", cty='" + cty + '\'' +
                '}';
    }
}
