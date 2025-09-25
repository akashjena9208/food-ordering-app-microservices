cd "D:/Micro Service Project/food-ordering-app"

# Initialize git (only once)
git init

# Add remote repo (only once)
git remote add origin https://github.com/akashjena9208/food-ordering-app-microservices.git

# Check remote
git remote -v


# Go to project root
cd "D:/Micro Service Project/food-ordering-app"

# Check changes
git status

# Stage the service you worked on
git add user-service/
👉 Replace user-service with:
restaurant-service/
order-service/
payment-service/
delivery-service/
api-gateway/
config-server/


# Commit with a clear message
git commit -m "Added user-service initial setup"

# Push to GitHub
git push origin main

# Add Another Service (order-service)
cd "D:/Micro Service Project/food-ordering-app"
git status
git add order-service/
git commit -m "Added order-service initial setup"
git push origin main
