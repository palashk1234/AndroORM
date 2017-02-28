# AndroORM
ORM Library for android.


### Usage

  Start the DbEngine in your Application class like this.<br>
  ```java
  DbEngine.start(getApplicationContext(), "MyDatabase");
  ```
  
  Make Model/Pojo class as like given below.<br>
  
  ```java
@Table(name = "employee_dtls")
public class EmployeeDtlsPojo {

    @Column(name = "EMPLOYEE_NAME", fieldType = FieldType.TEXT)
    private String name;

    @Column(name = "EMPLOYEE_AGE", fieldType = FieldType.INTEGER, fieldSize = 2)
    public int age;

    @Column(name = "EMPLOYEE_PHONE", fieldType = FieldType.TEXT)
    private String phone;

    public EmployeeDtlsPojo() {
    }

    public EmployeeDtlsPojo(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }
  ```
  
  
  To insert Data into Table use method as follows.<br>
  ```java
  EmployeeDtlsPojo employeeDtlsPojo = new EmployeeDtlsPojo("Employee- 1", 25, "1234567890");
  new InsertQuery.Builder(employeeDtlsPojo).build().execute();
  ```
  
  
  To Select Data from Table use method as follows.<br>
  ```java
  List<EmployeeDtlsPojo> list = new SelectQuery.Builder(EmployeeDtlsPojo.class).build().execute();    
  ```
  
  
  Or for customized Selection from Table use method as follows.<br>
  ```java
  List<EmployeeDtlsPojo> list = new SelectQuery.Builder(EmployeeDtlsPojo.class)
                                            .setFields(DbUtils.EMPLOYEE_NAME)
                                            .where("EMPLOYEE_NAME  LIKE 'Employee%' ")
                                            .build()
                                            .execute();
                                            
                                            
List<EmployeeDtlsPojo> list = new SelectQuery.Builder(EmployeeDtlsPojo.class)
                                            .setFields("EMPLOYEE_NAME",EMPLOYEE_AGE)
                                            .build()
                                            .execute();                                            
  ```

### Contiribute
Plese give your precious contribution for this project.
