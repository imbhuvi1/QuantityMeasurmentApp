# QuantityMeasurementApp - Docker Deployment Guide

## 🐳 Docker Configuration

This project includes comprehensive Docker configuration for both development and production environments.

## 📁 Docker Files Overview

- `Dockerfile` - Multi-stage build configuration
- `docker-compose.yml` - Development environment
- `docker-compose.prod.yml` - Production environment
- `.dockerignore` - Build optimization
- `init.sql` - Database initialization

## 🚀 Quick Start (Development)

### Prerequisites
- Docker Desktop installed
- Docker Compose v3.8+

### 1. Clone and Navigate
```bash
cd QuantityMeasurmentApp
```

### 2. Build and Run
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down
```

### 3. Access Application
- **Application**: http://localhost:8080
- **MySQL**: localhost:3307 (to avoid local MySQL conflict)

## 🏗️ Build Options

### Build Only Application
```bash
# Build Docker image
docker build -t qma-app .

# Run with external MySQL
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/quantitymeasurementdb \
  qma-app
```

### Development with Hot Reload
```bash
# For development with volume mounting
docker-compose -f docker-compose.dev.yml up
```

## 🔧 Environment Configuration

### Development Environment Variables
```env
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/quantitymeasurementdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=Bhuvi@123
SPRING_JPA_HIBERNATE_DDL_AUTO=update
JWT_SECRET=mySecretKey123456789012345678901234567890
```

### Production Deployment

#### 1. Create Secrets Directory
```bash
mkdir -p secrets
echo "your_mysql_root_password" > secrets/mysql_root_password.txt
echo "your_mysql_password" > secrets/mysql_password.txt
echo "your_jwt_secret_key_here" > secrets/jwt_secret.txt
echo "your_google_client_id" > secrets/google_client_id.txt
echo "your_google_client_secret" > secrets/google_client_secret.txt
```

#### 2. Deploy Production
```bash
docker-compose -f docker-compose.prod.yml up -d
```

## 📊 Monitoring & Health Checks

### Health Check Endpoints
```bash
# Application health
curl http://localhost:8080/actuator/health

# Database connection
docker-compose exec mysql mysqladmin ping
```

### View Logs
```bash
# Application logs
docker-compose logs -f app

# Database logs
docker-compose logs -f mysql

# All services
docker-compose logs -f
```

## 🔍 Troubleshooting

### Common Issues

#### Port Conflicts
```bash
# Check port usage
netstat -tulpn | grep :8080
netstat -tulpn | grep :3306

# Use different ports
docker-compose up -d --scale app=1 -p 8081:8080
```

#### Database Connection Issues
```bash
# Check MySQL status
docker-compose exec mysql mysql -u root -p -e "SHOW DATABASES;"

# Reset database
docker-compose down -v
docker-compose up -d
```

#### Memory Issues
```bash
# Check container resources
docker stats

# Increase memory limits in docker-compose.yml
```

### Debug Mode
```bash
# Run with debug output
docker-compose up --verbose

# Access container shell
docker-compose exec app bash
docker-compose exec mysql bash
```

## 🔒 Security Considerations

### Production Security
1. **Change default passwords** in production
2. **Use Docker secrets** for sensitive data
3. **Enable SSL/TLS** with nginx reverse proxy
4. **Limit container resources** to prevent DoS
5. **Use non-root user** (already configured)

### Network Security
```bash
# Create custom network
docker network create qma-secure-network

# Run with custom network
docker-compose -f docker-compose.prod.yml up -d
```

## 📈 Performance Optimization

### JVM Tuning
```env
JAVA_OPTS=-Xmx1g -Xms512m -XX:+UseG1GC -XX:+UseContainerSupport
```

### Database Optimization
```sql
-- Add to init.sql for better performance
SET GLOBAL innodb_buffer_pool_size = 268435456;  -- 256MB
SET GLOBAL max_connections = 100;
```

### Container Resources
```yaml
deploy:
  resources:
    limits:
      memory: 1G
      cpus: '0.5'
```

## 🧪 Testing

### Integration Testing
```bash
# Run tests in container
docker-compose exec app mvn test

# Run specific test
docker-compose exec app mvn test -Dtest=QuantityMeasurementControllerTest
```

### Load Testing
```bash
# Using curl for basic testing
for i in {1..100}; do
  curl -X POST http://localhost:8080/api/quantity/compare \
    -H "Content-Type: application/json" \
    -d '{"q1":{"value":12,"unit":"INCHES","measurementType":"LENGTH"},"q2":{"value":1,"unit":"FEET","measurementType":"LENGTH"}}'
done
```

## 📝 Maintenance

### Backup Database
```bash
# Create backup
docker-compose exec mysql mysqldump -u root -p quantitymeasurementdb > backup.sql

# Restore backup
docker-compose exec -T mysql mysql -u root -p quantitymeasurementdb < backup.sql
```

### Update Application
```bash
# Rebuild and deploy
docker-compose build --no-cache app
docker-compose up -d app
```

### Clean Up
```bash
# Remove unused images
docker image prune -a

# Remove unused volumes
docker volume prune

# Complete cleanup
docker system prune -a --volumes
```

## 🌐 Scaling

### Horizontal Scaling
```bash
# Scale application instances
docker-compose up -d --scale app=3

# With load balancer
docker-compose -f docker-compose.prod.yml up -d --scale app=3
```

### Database Scaling
```yaml
# Add read replica in docker-compose.yml
mysql-replica:
  image: mysql:8.0
  environment:
    MYSQL_MASTER_SERVICE: mysql
```

## 📞 Support

For issues and questions:
1. Check logs: `docker-compose logs -f`
2. Verify configuration: `docker-compose config`
3. Test connectivity: `docker-compose exec app curl -f http://localhost:8080/actuator/health`

---

**Note**: Remember to update OAuth2 credentials and JWT secrets before production deployment!