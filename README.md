# 💰 BudgetMate

BudgetMate is an Android budgeting application developed using Kotlin and Android Studio. The application helps users manage their personal finances by tracking expenses, setting spending goals, visualising spending patterns, and earning rewards for good budgeting habits.

---

## 📱 Purpose of the Application

Many people struggle to manage their spending and stay within budget. BudgetMate provides users with an easy-to-use platform to:

- Track daily expenses
- Categorise spending
- Set minimum and maximum spending goals
- Monitor financial progress
- Analyse spending through graphs and reports
- Stay motivated through gamification features

The app was developed as part of the IMAD5112 Programming Module Portfolio of Evidence (POE).

---

# ✨ Features

## Core Features

### 🔐 User Authentication
- User registration
- Secure login functionality
- Personalised budgeting experience

### 💵 Expense Tracking
- Add expenses
- Edit expenses
- Delete expenses
- View expense history

### 📂 Category Management
- Create spending categories
- Organise expenses into categories
- Filter spending by category

### 🎯 Budget Goals
- Set minimum spending goals
- Set maximum spending goals
- Monitor spending against goals

---

# 📊 Final POE Features

## Spending Reports and Graphs

Users can:

- View spending per category
- Select a custom date range
- Analyse spending trends
- View graphical spending summaries

### Graph Features
- Category spending visualisation
- Minimum goal indicators
- Maximum goal indicators
- Date-range filtering

---

## 📈 Monthly Spending Progress

The application visually displays how well the user is staying within their spending goals over a selected period.

Users can quickly identify whether they are:

✅ Within budget

⚠️ Approaching limits

❌ Exceeding goals

---

## 🏆 Gamification System

To encourage consistent budgeting habits, BudgetMate includes:

### Achievements
- First Expense Added
- Budget Goal Achieved
- Consistent Expense Logging
- Savings Milestones

### Rewards
- XP (Experience Points)
- Levels
- Achievement Badges
- Progress Tracking

The gamification system motivates users to maintain healthy financial habits.

---

# 🌟 Custom Features

## Feature 1: XP and Level System

Users earn XP for:

- Adding expenses
- Managing categories
- Staying within budget
- Completing achievements

As XP increases, users level up and unlock new badges.

---

## Feature 2: Achievement Badge System

The application awards badges when users reach specific milestones such as:

- Creating their first budget
- Logging expenses consistently
- Achieving spending goals
- Maintaining budgeting streaks

---

# 🛠 Technologies Used

| Technology | Purpose |
|------------|----------|
| Kotlin | Android Development |
| Android Studio | Development Environment |
| Room Database | Local Data Storage |
| RecyclerView | Data Presentation |
| Material Design | User Interface |
| MPAndroidChart | Data Visualisation |
| Git | Version Control |
| GitHub | Source Code Hosting |
| GitHub Actions | Continuous Integration |

---

# 🗄 Database Implementation

BudgetMate uses the Room Persistence Library to store:

- User data
- Expense records
- Budget goals
- Categories
- Achievement progress

Benefits include:

- Efficient local storage
- Offline functionality
- Reliable data persistence
- Structured database management

---

# 🧪 Testing

Automated and manual testing were performed to verify:

- User authentication
- Expense management
- Goal tracking
- Database operations
- Report generation
- Gamification features

Testing helps ensure application stability and reliability.

---

# ⚙️ GitHub Actions

GitHub Actions was implemented to automate the build process.

### Workflow Functions

- Automatically builds the project
- Runs tests
- Verifies Gradle configuration
- Ensures project integrity after code changes

Workflow file location:

```text
.github/workflows/build.yml
```

This ensures that the application can be successfully built on environments other than the developer's machine.

---

# 📸 Screenshots

## Dashboard
## Expense Tracking
## Budget Goals
## Reports and Graphs
## Gamification

---

# 📦 Installation

## Clone Repository

```bash
git clone https://github.com/DraxlerSosa/BudgetMate.git
```

## Open Project

1. Open Android Studio
2. Select **Open Existing Project**
3. Navigate to the cloned repository
4. Allow Gradle to sync

## Build Application

```bash
./gradlew assembleDebug
```

## Run Application

Connect an Android device or use an emulator and run the application.

---

# 🎥 Demonstration Video

YouTube Demonstration:

https://youtu.be/XpoxVf2HG5I

---

# 📁 APK

The APK file is included with the final submission and can also be downloaded from the repository releases section.

---

# 📚 References

Android Developers Documentation  
https://developer.android.com

Room Persistence Library Documentation  
https://developer.android.com/training/data-storage/room

GitHub Actions Documentation  
https://docs.github.com/actions

Material Design Guidelines  
https://m3.material.io

MPAndroidChart Documentation  
https://github.com/PhilJay/MPAndroidChart

---

# 👨‍💻 Developer

**Kgothatso Magatsela** - ST10448288
**Dennis Moima** - ST10451932


GitHub:
https://github.com/DraxlerSosa

---

# 📄 License

This project was developed for educational purposes as part of the IMAD5112 Portfolio of Evidence.
