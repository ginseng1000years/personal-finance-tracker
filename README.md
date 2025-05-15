# Money Management Application

A full-stack personal finance application with Vue.js frontend and Spring Boot backend, using MongoDB for data storage.

## Features

- Transaction management (income/expense tracking)
- Category management with CRUD operations
- Paginated data views
- Responsive UI with modals for all actions

## Project Structure

```
money-management/
├── frontend/          # Vue.js application
│   ├── src/           # Vue components and logic
│   │   ├── views/     # Main application views
│   │   │   ├── Categories.vue  # Category management
│   │   │   └── Transactions.vue # Transaction management
│   ├── package.json   # Frontend dependencies
├── backend/           # Spring Boot application
│   ├── src/           # Java source code
│   └── pom.xml        # Backend dependencies
└── README.md          # Project documentation
```

## Prerequisites

- Node.js (for frontend)
- Java 11 JDK (for backend)
- MongoDB (for database)

## Installation

1. **Frontend Setup**
```bash
cd frontend
npm install
```

2. **Backend Setup**
- Ensure Java 11 and Maven are installed
- Configure MongoDB connection in backend application.properties

## Running the Application

1. **Start Backend**
```bash
cd backend
mvn spring-boot:run
```

2. **Start Frontend**
```bash
cd frontend
npm run serve
```

Access the application at `http://localhost:3000`

## Configuration

- Frontend API base URL can be configured in `frontend/src/config.js`
- Backend MongoDB settings in `backend/src/main/resources/application.properties`

## Screenshots

(Add screenshots of the main interfaces here)

## License

MIT