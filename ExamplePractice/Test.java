package ch4CheckTime;

public class Test { 
static Name n1 = new Name("John", "Smith");
static Name n2 = new Name("John", "Kitz");
public static void main(String[] args) {
swap1(n1. firstName, n1. lastName);
swap2(n1, n2);
System.out.println(n1);
System.out.println(n2);
}
static void swap1(String a, String b){
String temp;
temp = a;
a = b;
b = temp;
}
static void swap2(Name a, Name b){
Name temp;
temp = a;
a = b;
b = temp;
}
}
class Name {
String firstName;
String lastName;
public Name(String a, String b){
firstName = a;
lastName = b;
}
public String toString(){
return firstName + " " + lastName;
}
}





