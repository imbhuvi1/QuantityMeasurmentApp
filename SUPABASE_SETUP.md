# 🚀 QuantityMeasurementApp - Supabase Setup Guide

## 📋 Your Supabase Connection Details

```
Host: aws-1-ap-southeast-1.pooler.supabase.com
Port: 5432
Database: postgres
Username: postgres.jwmezlerhbqelxdfnxfb
Connection String: postgresql://postgres.jwmezlerhbqelxdfnxfb:[YOUR-PASSWORD]@aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres
```

## ⚡ Quick Start (3 Steps)

### Step 1: Add Your Password
Edit the `.env` file and replace `YOUR_ACTUAL_PASSWORD` with your Supabase password:
```env
DB_PASSWORD=your_real_supabase_password_here
```

### Step 2: Start Docker Desktop
Make sure Docker Desktop is running (green whale icon in system tray)

### Step 3: Run the Application
```bash
cd d:\QuantityMeasurmentApp
run-backend.bat
```

## 🔧 Manual Commands

### Test Database Connection
```bash
test-connection.bat
```

### Build and Run
```bash
docker-compose up --build -d
```

### View Logs
```bash
docker-compose logs -f app
```

### Stop Application
```bash
docker-compose down
```

## 📡 API Endpoints

Once running at http://localhost:8080:

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Quantity Operations (Anonymous & Authenticated)
- `POST /api/quantity/compare` - Compare two quantities
- `POST /api/quantity/convert` - Convert between units
- `POST /api/quantity/add` - Add quantities
- `POST /api/quantity/subtract` - Subtract quantities
- `POST /api/quantity/divide` - Divide quantities

### User Operations (Authenticated only)
- `GET /api/quantity/getHistory` - Get calculation history
- `DELETE /api/quantity/deleteById?id=1` - Delete specific calculation
- `DELETE /api/quantity/deleteAll` - Delete all history

### User Profile (Authenticated only)
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update user profile

### Health Check
- `GET /actuator/health` - Application health status

## 📝 Example API Calls

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "name": "Test User",
    "password": "password123"
  }'
```

### Compare Quantities (Anonymous)
```bash
curl -X POST http://localhost:8080/api/quantity/compare \
  -H "Content-Type: application/json" \
  -d '{
    "q1": {"value": 12, "unit": "INCHES", "measurementType": "LENGTH"},
    "q2": {"value": 1, "unit": "FEET", "measurementType": "LENGTH"}
  }'
```

### Convert Units (Anonymous)
```bash
curl -X POST http://localhost:8080/api/quantity/convert \
  -H "Content-Type: application/json" \
  -d '{
    "q1": {"value": 1, "unit": "FEET", "measurementType": "LENGTH"},
    "q2": {"value": 0, "unit": "INCHES", "measurementType": "LENGTH"}
  }'
```

## 🔍 Troubleshooting

### Connection Issues
1. Check `.env` file has correct password
2. Verify Docker Desktop is running
3. Test connection: `test-connection.bat`
4. Check Supabase dashboard for database status

### Application Issues
1. View logs: `docker-compose logs -f app`
2. Check health: http://localhost:8080/actuator/health
3. Restart: `docker-compose restart app`

### Port Conflicts
If port 8080 is busy, edit `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Use port 8081 instead
```

## 🛡️ Security Notes

- Change JWT_SECRET in production
- Use environment variables for sensitive data
- Enable SSL/HTTPS in production
- Regularly rotate database passwords
- Monitor Supabase usage and logs

## 📊 Supported Units

### Length
- FEET, INCHES, YARDS, CENTIMETERS

### Weight  
- GRAMS, KILOGRAMS, POUNDS, OUNCES

### Volume
- LITERS, MILLILITERS, GALLONS, CUPS

### Temperature
- CELSIUS, FAHRENHEIT, KELVIN

---

**Ready to run!** 🎉 Your backend is configured for Supabase PostgreSQL.