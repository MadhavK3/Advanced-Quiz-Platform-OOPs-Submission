# MCQ System  
A complete MCQ (Multiple Choice Questions) platform consisting of a **Client Application** and a **Server Backend**.

---

## 📌 Overview  
This project is a two-module MCQ system:

### **1. MCQ Client (Frontend)**
A Java-based UI application that allows users to:
- Fetch MCQ questions from the server  
- Attempt quizzes  
- Submit answers  
- View scores  

### **2. MCQ Server (Backend)**
A Java (Maven/Spring-based) backend that:
- Provides MCQ questions through REST APIs  
- Accepts submitted answers  
- Calculates and returns scores  

Both modules work together to form a complete MCQ evaluation system.

---

## 📁 Project Structure  


---

## 🚀 Features

### **Client Features**
- Fetches quiz/questions from server  
- Displays MCQs in clean UI  
- Allows answer selection  
- Submits responses  
- Shows score returned from backend  

### **Server Features**
- REST APIs for MCQs  
- Validates submitted answers  
- Computes score  
- Sends JSON responses  
- Easy to extend with more question sets  

---

## 🛠️ Technologies Used  
- **Java (JDK 8+)**  
- **Maven**  
- **Spring Boot (server)**  
- **HTTP/JSON communication**  

---

## 🧩 How It Works

### **1. Client requests MCQs**

### **2. Server returns question list (JSON)**  
Client displays them to the user.

### **3. Client submits answers**

### **4. Server calculates score**  


git clone https://github.com/MadhavK3/mcq_client.git
git clone https://github.com/MadhavK3/mcq_server.git

cd mcq_server
mvn clean install
mvn spring-boot:run
cd mcq_client
mvn clean install


## 📌 Future Enhancements
Planned improvements and features to be implemented in future releases:

- Add login / authentication (JWT or OAuth)  
- Timer for each quiz (per-question or full-quiz timer)  
- Category-wise MCQs (filter by topic/difficulty)  
- Leaderboard & analytics (track top scorers & trends)  
- Persistent DB (MySQL / PostgreSQL) for storing quizzes and results  
- Admin panel for adding/editing/removing MCQs

---

## 👨‍💻 Authors  

| Name     | PRN |
|----------|-----:|
| Om       |   124B1F001 |
| Atharva  |  124B1F009 |
| Madhav   |  124B1F023 |

---

## 📚 References
- Java Documentation — https://docs.oracle.com/javase/  
- Spring Boot Docs — https://spring.io/projects/spring-boot  
- TutorialsPoint — https://www.tutorialspoint.com/java/  
- GeeksForGeeks — https://www.geeksforgeeks.org/java/  
- Javatpoint — https://www.javatpoint.com/java-tutorial
