# HandyHub MongoDB Backend Server

This folder contains a complete Node.js and Express backend configured with **MongoDB** (via Mongoose) to support all API endpoints required by the HandyHub Android application.

## Prerequisites
- [Node.js](https://nodejs.org/) installed on your machine.
- Local [MongoDB](https://www.mongodb.com/try/download/community) running on `mongodb://localhost:27017` OR a connection string to [MongoDB Atlas](https://www.mongodb.com/cloud/atlas).

## Setup & Run Instructions

1. Open a terminal and navigate to the `server` directory:
   ```bash
   cd server
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. (Optional) Configure MongoDB URI:
   If using MongoDB Atlas, set the `MONGO_URI` environment variable:
   ```bash
   set MONGO_URI=mongodb+srv://<username>:<password>@cluster.mongodb.net/handyhub
   ```
   *(On macOS/Linux use `export MONGO_URI=...`)*

4. Start the server:
   ```bash
   npm start
   ```

The server will start on port `8000`, which automatically matches the base URL (`http://10.0.2.2:8000/`) configured in [`RetrofitClient.kt`](../app/src/main/java/com/example/handyhub/data/network/RetrofitClient.kt) for the Android emulator.
