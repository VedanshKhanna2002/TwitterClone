# Twitter Clone 🐦

A simple Twitter-like Android application built using Kotlin, Firebase, and XML layouts. The app allows users to sign in, post tweets, follow others, and view a feed of tweets from followed accounts.

---

## Features

- User authentication with Firebase (Email/Password)
- Post tweets (text-only)
- Follow/unfollow users
- View tweets from followed users
- Suggested accounts section
- Profile images loaded via Glide
- Real-time data updates

---

## Tech Stack

- Language: Kotlin
- UI: XML Layouts, RecyclerView, CircleImageView
- Backend: Firebase Realtime Database, Firebase Authentication
- Libraries:  
  - Glide (image loading)  
  - Firebase SDK

---



## Getting Started

### Prerequisites

- Android Studio (Hedgehog or newer)
- Firebase Project
- Emulator or Android device

### Firebase Setup

1. Go to Firebase Console: https://console.firebase.google.com/
2. Create a new Firebase project
3. Enable Authentication → Email/Password
4. Create a Realtime Database
5. Download `google-services.json` and place it in the `app/` directory

### Clone the Repository

```bash
git clone https://github.com/your-username/TwitterClone.git
cd TwitterClone
